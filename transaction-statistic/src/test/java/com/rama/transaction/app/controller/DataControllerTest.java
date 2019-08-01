package com.rama.transaction.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rama.transaction.app.controller.DataController;
import com.rama.transaction.app.dto.Statistics;
import com.rama.transaction.app.dto.Transaction;
import com.rama.transaction.app.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(DataController.class)
public class DataControllerTest {
	@MockBean
	private TransactionService transactionService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createTransactionTest() throws URISyntaxException, Exception {
		Transaction transaction = new Transaction(new BigDecimal("10.35"),Instant.now());
		Mockito.doNothing().when(transactionService).createTransaction(Mockito.any());;
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST,
				asJsonString(transaction));
		assertEquals(HttpStatus.CREATED.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	@Test
	public void getStatisticsTest() throws IOException {
		Statistics expected = new Statistics(new BigDecimal("30"), new BigDecimal("10"), new BigDecimal("20"),
				new BigDecimal("5"), 3);
		Mockito.when(transactionService.getStatistics()).thenReturn(expected);
		ResultActions responseEntity = processApiRequest("/statistics", HttpMethod.GET, null);
		MvcResult mvcresult = responseEntity.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcresult.getResponse().getStatus());
		Statistics actual = mapper.readValue(mvcresult.getResponse().getContentAsString(), Statistics.class);
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}

	@Test
	public void deleteTransactionTest() {
		Mockito.doNothing().when(transactionService).deleteTransaction();
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.DELETE, null);
		assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	private ResultActions processApiRequest(String api, HttpMethod methodType, String content) {
		ResultActions response = null;
		try {
			switch (methodType) {
			case GET:
				response = mockMvc
						.perform(MockMvcRequestBuilders.get(new URI(api)).contentType(MediaType.APPLICATION_JSON));
				break;
			case DELETE:
				response = mockMvc
						.perform(MockMvcRequestBuilders.delete(new URI(api)).contentType(MediaType.APPLICATION_JSON));
				break;
			case POST:
				response = mockMvc.perform(MockMvcRequestBuilders.post(new URI(api)).content(content)
						.contentType(MediaType.APPLICATION_JSON));
				break;
			default:
				fail("Method Not supported");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return response;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			JavaTimeModule module = new JavaTimeModule();
			mapper.registerModule(module);
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
