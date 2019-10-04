package com.mongo.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.app.document.Employee;
import com.mongo.app.document.Student;
import com.mongo.app.service.EmployeeDataOperationService;
import com.mongo.app.service.StudentDataOperationService;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentOperationController.class)
@ActiveProfiles("test")
public class StudentOperationControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StudentDataOperationService studentDataOperationService;

	@Value("${student.create.url}")
	String createurl;

	@Value("${student.read.url}")
	String readurl;

	@Value("${student.update.url}")
	String updateurl;

	@Value("${student.delete.url}")
	String deleteurl;

	@Test
	public void testCreateData() throws Exception {

		Mockito.when(studentDataOperationService.createData(Mockito.any(Student.class)))
				.thenReturn(createStudent("58d1c36efb0cac4e15afd278", "stud1", "25", "7"));
		ResultActions responseEntity = processApiRequest(createurl, HttpMethod.POST, null,
				createStudent("58d1c36efb0cac4e15afd278", "stud1", "25", "7"));
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Student result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Student.class);
		assertEquals("58d1c36efb0cac4e15afd278", result.get_id());
		assertEquals("stud1", result.getFullName());
		assertEquals("25", result.getRollno());
		assertEquals("7", result.getStd());
		

	}

	@Test
	public void testReadData() throws Exception {
		List<Student> employees = new ArrayList<Student>();
		employees.add(createStudent("58d1c36efb0cac4e15afd278", "stud1", "25", "7"));
		Mockito.when(studentDataOperationService.readAllData()).thenReturn(employees);
		ResultActions responseEntity = processApiRequest(readurl, HttpMethod.GET, null, null);
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		List<Student> result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				new TypeReference<List<Student>>(){});
		Student student = result.get(0);
		assertEquals("58d1c36efb0cac4e15afd278", student.get_id());
		assertEquals("stud1", student.getFullName());
		assertEquals("25", student.getRollno());
		assertEquals("7", student.getStd());
	}

	@Test
	public void testUpdateData() throws Exception {

		Mockito.when(studentDataOperationService.updateData(Mockito.any(Student.class)))
				.thenReturn(createStudent("58d1c36efb0cac4e15afd278", "stud1", "25", "7"));
		ResultActions responseEntity = processApiRequest(updateurl, HttpMethod.PUT,
				new ObjectId("58d1c36efb0cac4e15afd123"), createStudent("58d1c36efb0cac4e15afd278", "stud1", "25", "7"));
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Student student = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Student.class);
		assertEquals("58d1c36efb0cac4e15afd278", student.get_id());
		assertEquals("stud1", student.getFullName());
		assertEquals("25", student.getRollno());
		assertEquals("7", student.getStd());

	}

	@Test
	public void testDeleteData() throws Exception {
		
		ResultActions responseEntity = processApiRequest(deleteurl, HttpMethod.DELETE,
				new ObjectId("58d1c36efb0cac4e15afd278"), null);
		responseEntity.andExpect(status().isOk());
		Mockito.verify(studentDataOperationService, times(1)).deleteData(any(ObjectId.class));

	}

	private ResultActions processApiRequest(String api, HttpMethod methodType, ObjectId id, Student student) {
		ResultActions response = null;
		try {
			switch (methodType) {
			case GET:
				response = mockMvc.perform(get(api));
				break;
			case POST:
				response = mockMvc.perform(post(api).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(student)).accept(MediaType.APPLICATION_JSON));
				break;
			case PUT:
				response = mockMvc.perform(put(api, id).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(student)).accept(MediaType.APPLICATION_JSON));
				break;
			case DELETE:
				response = mockMvc.perform(delete(api, id));
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

	private String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Student createStudent(String id, String name, String rollNo,String std ) {
		Student student  = new Student();
		student.set_id(new ObjectId(id));
		student.setFullName(name);
		student.setRollno(rollNo);
		student.setStd(std);
		return student;
	}

}
