package com.mongo.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongo.app.config.SpringSecurityBasicConfig;
import com.mongo.app.document.Student;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.app.document.Employee;
import com.mongo.app.service.EmployeeDataOperationService;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest({EmployeeOperationController.class})
@ActiveProfiles(value = "test")
public class EmployeeOperationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	EmployeeDataOperationService employeeDataOperationService;

	@Value("${employee.create.url}")
	String createurl;

	@Value("${employee.read.url}")
	String readurl;

	@Value("${employee.update.url}")
	String updateurl;

	@Value("${employee.delete.url}")
	String deleteurl;

	@TestConfiguration
	public static class TestConfig {
		@Bean
		@Primary
		public StudentOperationController mockStudentOperationController() {
			StudentOperationController studentOperationController = mock(StudentOperationController.class);
			Student student=mock(Student.class);
			List students= new ArrayList();
			students.add(student);
			Mockito.when(studentOperationController.getAllData()).thenReturn(students);
			return studentOperationController;
		}

	}



	@Test
	public void testCreateData() throws Exception {
		Mockito.when(employeeDataOperationService.createData(Mockito.any(Employee.class)))
				.thenReturn(createEmployee("58d1c36efb0cac4e15afd278", 20, "123", "test"));
		ResultActions responseEntity = processApiRequest(createurl, HttpMethod.POST, null,
				createEmployee("58d1c36efb0cac4e15afd278", 20, "123", "test"));
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Employee result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Employee.class);
		assertEquals(20, result.getAge());
		assertEquals("123", result.getEmpNo());
		assertEquals("test", result.getFullName());
		assertEquals("58d1c36efb0cac4e15afd278", result.get_id());

	}

	@Test
	public void testReadData() throws Exception {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(createEmployee("58d1c36efb0cac4e15afd278", 20, "123", "test"));
		Mockito.when(employeeDataOperationService.readAllData()).thenReturn(employees);
		ResultActions responseEntity = processApiRequest(readurl, HttpMethod.GET, null, null);
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				new TypeReference<List<Employee>>(){});
		Employee employee = result.get(0);
		assertEquals(20, employee.getAge());
		assertEquals("123", employee.getEmpNo());
		assertEquals("test", employee.getFullName());
		assertEquals("58d1c36efb0cac4e15afd278", result.get(0).get_id());
	}

	@Test
	public void testUpdateData() throws Exception {

		Mockito.when(employeeDataOperationService.updateData(Mockito.any(Employee.class)))
				.thenReturn(createEmployee("58d1c36efb0cac4e15afd123", 20, "123", "test"));
		ResultActions responseEntity = processApiRequest(updateurl, HttpMethod.PUT,
				new ObjectId("58d1c36efb0cac4e15afd123"), createEmployee("58d1c36efb0cac4e15afd278", 20, "123", "test"));
		responseEntity.andExpect(status().isOk());
		ObjectMapper mapper = new ObjectMapper();
		Employee result = mapper.readValue(responseEntity.andReturn().getResponse().getContentAsString(),
				Employee.class);
		assertEquals(20, result.getAge());
		assertEquals("123", result.getEmpNo());
		assertEquals("test", result.getFullName());
		assertEquals("58d1c36efb0cac4e15afd123", result.get_id());

	}

	@Test
	public void testDeleteData() throws Exception {
		
		ResultActions responseEntity = processApiRequest(deleteurl, HttpMethod.DELETE,
				new ObjectId("58d1c36efb0cac4e15afd278"), null);
		responseEntity.andExpect(status().isOk());
		Mockito.verify(employeeDataOperationService, times(1)).deleteData(any(ObjectId.class));

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
				response = mockMvc.perform(put(api, id).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(employee)).accept(MediaType.APPLICATION_JSON));
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

	private static Employee createEmployee(String id, int age, String empNo, String name) {
		Employee empEmployee = new Employee();
		empEmployee.set_id(new ObjectId(id));
		empEmployee.setAge(age);
		empEmployee.setEmpNo(empNo);
		empEmployee.setFullName(name);
		empEmployee.setJoiningDate(new Date());
		return empEmployee;
	}
}
