package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.DataTask;
import com.repository.DataTaskRepository;

@Service
public class DataTaskService {
	
	@Autowired
	DataTaskRepository dataTaskRepository;
	
	public List<DataTask> findByShopAndGradeAndSituationEqualsIncompleto(String shop, String grade) throws Exception{
		try {
			return dataTaskRepository.findByShopAndGradeAndSituationEqualsIncompleto(grade,shop);
		}catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA LISTA");
		}
	}
}
