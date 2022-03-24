package com.mongo.app.file.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mongo.app.file.response.FileUploadResponse;
import com.mongo.app.file.service.FileUploadService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class OperationControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@TestConfiguration
	static class FileUploadServiceTestConfiguration {
		@Bean
		public FileUploadService fileUploadServiceImpl() {
			FileUploadResponse value = new FileUploadResponse();
			List<String> exception = new ArrayList<String>();
			value.setException(exception);
			value.setFilecount(1);
			FileUploadService fileUploadService = Mockito.mock(FileUploadService.class);
			Mockito.when(fileUploadService.uploadFileProcess(Mockito.any(), Mockito.anyString())).thenReturn(value);
			return fileUploadService;
		}
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void bulkEntryFileUpload() throws Exception {
		MockMultipartFile multipartfile = new MockMultipartFile("file", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
				"{file :upload}".getBytes());
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(new URI("/mongo/uploadfile"));
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
}
