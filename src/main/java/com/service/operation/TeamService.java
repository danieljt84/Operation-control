package com.service.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Team;
import com.repository.operation.TeamRepository;

@Service
public class TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	
	
	public List<Team> getAll() {
		return teamRepository.findAll();
	}

}
