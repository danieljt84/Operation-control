package com.service.operation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Holyday;
import com.repository.operation.HolydayRepository;

@Service
public class HolydayService {
	@Autowired
	HolydayRepository holydayRepository;

	public List<Holyday> getHolydaysBetween(LocalDate start, LocalDate end){
		return holydayRepository.findByDateBetween(start, end);
	}
}
