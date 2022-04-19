package com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Promoter;
import com.model.Team;

@Repository
public class TeamRepositoryImp {

	@Autowired
	TeamRepository teamRepository;
	
	public Team checkTeam(String nameTeam) {
		Team team = teamRepository.findByName(nameTeam);
		if(team==null) {
			Team newTeam = new Team(nameTeam);
			team = teamRepository.save(newTeam);
		}
		return team;
	}
}
