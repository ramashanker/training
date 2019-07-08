/*package com.rama.mongo.operation.controller;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class OperationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void bulkEntryFileUpload() throws Exception {
		MockMultipartFile multipartfile = new MockMultipartFile("file", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
				"{file :upload}".getBytes());
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(new URI("/mongo/entryfile"));
		builder.with(new RequestPostProcessor() {

			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				request.setParameter("name", "test1");
				return request;
			}
		});
		ResultActions response = mockMvc.perform(builder.file(multipartfile));
		response.andExpect(MockMvcResultMatchers.status().isOk());
	}
}*/
