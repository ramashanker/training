package com.eureka.app.school;

import static org.junit.Assert.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import com.eureka.app.school.controller.SchoolServiceController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SchoolServiceController.class)
@ActiveProfiles("test")
@SpringBootTest(classes = SpringTestConfig.class)
public class SchoolServiceControllerTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

	private MockRestServiceServer mockServer;

	private ObjectMapper mapper = new ObjectMapper();

	@Value("${student.url}")
	String url;
	@Value("${school.name}")
	String schoolname;

	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void testGetStudents() throws Exception {
		mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://student-service/getStudentDetailsForSchool/")))
				.andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(String.class)));
		ResultActions responseEntity = processApiRequest(url, HttpMethod.GET, schoolname);
		responseEntity.andExpect(status().isOk());
	}

	private ResultActions processApiRequest(String api, HttpMethod methodType, String content) {
		ResultActions response = null;
		try {
			switch (methodType) {
			case GET:
				response = mockMvc.perform(get(api, content));
				break;
			case POST:
				response = mockMvc.perform(post(api, content));
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

}
