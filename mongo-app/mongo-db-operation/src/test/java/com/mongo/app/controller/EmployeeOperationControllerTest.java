package com.mongo.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.app.document.Employee;
import com.mongo.app.service.EmployeeDataOperationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeOperationController.class)
@ActiveProfiles("test")
public class EmployeeOperationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	EmployeeDataOperationService dataUploadService;

	@Value("${create.url}")
	String createurl;

	@Value("${read.url}")
	String readurl;

	@Value("${update.url}")
	String updateurl;

	@Value("${delete.url}")
	String deleteurl;

	@Test
	public void testCreateData() throws Exception {

		Mockito.when(dataUploadService.createData(Mockito.any(Employee.class)))
				.thenReturn(createEmployee("58d1c36efb0cac4e15afd278"));
		ResultActions responseEntity = processApiRequest(createurl, HttpMethod.POST,null,
				createEmployee("58d1c36efb0cac4e15afd278"));
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Employee result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Employee.class);
		assertEquals(20, result.getAge());
		assertEquals("123", result.getEmpNo());
		assertEquals("e1", result.getFullName());
		assertEquals("58d1c36efb0cac4e15afd278", result.get_id());

	}

	@Test
	public void testReadData() throws Exception {

		List<Employee> employees = new ArrayList<Employee>();
		employees.add(createEmployee("58d1c36efb0cac4e15afd278"));
		Mockito.when(dataUploadService.readAllData()).thenReturn(employees);
		ResultActions responseEntity = processApiRequest(readurl, HttpMethod.GET, null, null);
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				List.class);
		Employee employee=result.get(0);
		assertEquals(20, employee.getAge());
		assertEquals("123", employee.getEmpNo());
		assertEquals("e1", employee.getFullName());
		assertEquals("58d1c36efb0cac4e15afd278", result.get(0).get_id());

	}

	@Test
	public void testUpdateData() throws Exception {

		Mockito.when(dataUploadService.createData(Mockito.any(Employee.class)))
				.thenReturn(createEmployee("58d1c36efb0cac4e15afd278"));
		ResultActions responseEntity = processApiRequest(updateurl, HttpMethod.PUT,
				new ObjectId("58d1c36efb0cac4e15afd278"), null);
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Employee result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Employee.class);
		assertEquals(20, result.getAge());
		assertEquals("123", result.getEmpNo());
		assertEquals("e1", result.getFullName());
		assertEquals("58d1c36efb0cac4e15afd278", result.get_id());

	}

	@Test
	public void testDeleteData() throws Exception {

		ResultActions responseEntity = processApiRequest(deleteurl, HttpMethod.DELETE,
				new ObjectId("58d1c36efb0cac4e15afd278"), null);
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Employee result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Employee.class);
		Mockito.verify(dataUploadService, times(1)).deleteData(any(ObjectId.class));

	}

	private ResultActions processApiRequest(String api, HttpMethod methodType, ObjectId id, Employee employee) {
		ResultActions response = null;
		try {
			switch (methodType) {
			case GET:
				response = mockMvc.perform(get(api));
				break;
			case POST:
				response = mockMvc.perform(post(api).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(employee)).accept(MediaType.APPLICATION_JSON));
				break;
			case PUT:
				response = mockMvc.perform(put(api, id));
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

	private static Employee createEmployee(String id) {
		Employee empEmployee = new Employee();
		empEmployee.set_id(new ObjectId("58d1c36efb0cac4e15afd278"));
		empEmployee.setAge(20);
		empEmployee.setEmpNo("123");
		empEmployee.setFullName("e1");
		empEmployee.setJoiningDate(new Date());
		return empEmployee;
	}
}
