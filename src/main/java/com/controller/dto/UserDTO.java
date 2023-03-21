package com.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.controller.dto.operation.TeamDTO;
import com.model.User;

public class UserDTO {
	
	private Long id;
	private String username;
	private String password;
	private List<TeamDTO> teams;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTeamsDTO(List<TeamDTO> teamsDTO) {
		this.teams = teamsDTO;
	}

	public List<TeamDTO> getTeamsDTO() {
		return teams;
	}

	public UserDTO convertToDTO(User user,ModelMapper modelMapper) {
		UserDTO dto = modelMapper.map(user, UserDTO.class);
	    return dto;
	}
}
