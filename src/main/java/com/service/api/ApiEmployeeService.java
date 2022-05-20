package com.service.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controller.dto.EmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.PropertiesReader;

@Service
public class ApiEmployeeService {

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	
	public ApiEmployeeService() {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}
	
	public EmployeeDTO getEmployeeByCpfAndApi(String cpf) {
		String urlApi = PropertiesReader.getProp().getProperty("api.employee.url")
				+"/employee/cpf/{cpf}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
				Map<String, String> uriVariables = new HashMap();
	        uriVariables.put("cpf","15001565740");
		try {
			ResponseEntity<EmployeeDTO> response = restTemplate
					.exchange(urlApi,HttpMethod.GET,requestEntity,EmployeeDTO.class,uriVariables);
			return response.getBody();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
