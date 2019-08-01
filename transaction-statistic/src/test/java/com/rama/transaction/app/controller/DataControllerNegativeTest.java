package com.rama.transaction.app.controller;

import static java.time.Instant.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rama.transaction.app.controller.DataController;
import com.rama.transaction.app.dto.Transaction;
import com.rama.transaction.app.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(DataController.class)
public class DataControllerNegativeTest {

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void oldTransactionTest() throws URISyntaxException, Exception {
		Transaction transaction = new Transaction(new BigDecimal("10.50"), now().minusSeconds(70));
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST, asJsonString(transaction));
		assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	@Test
	public void futureTransactionTest() throws URISyntaxException, Exception {
		Transaction transaction = new Transaction(new BigDecimal("10.50"), now().plusSeconds(20));
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST,  asJsonString(transaction));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	@Test
	public void invalidDateTransactionTest() throws Exception {
		String content = "{\"amount\":\"10.35\",\"timestamp\":\"22 DEC 2018\"}";
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST, content);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	@Test
	public void invalidJsonTransactionTest() throws URISyntaxException, Exception {
		String content = "{\"timestamp\":\"2018-10-20T09:59:51.312Z\"}";
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST, content);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.andReturn().getResponse().getStatus());
	}

	@Test
	public void invalidWithEmptyBodyDataTransactionTest() throws Exception {
		String content = "";
		ResultActions responseEntity = processApiRequest("/transactions", HttpMethod.POST, content);
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.andReturn().getResponse().getStatus());
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
