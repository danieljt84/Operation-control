package com.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.dto.filter.FilterActivationDTO;
import com.controller.dto.filter.FilterDataTableDataActivityDTO;
import com.controller.dto.operation.ProjectDTO;
import com.controller.dto.operation.ShopDTO;
import com.model.Brand;
import com.repository.operation.FilterRepositorImp;
import com.service.operation.ShopService;

@Service
public class FilterService {

	@Autowired
	FilterRepositorImp filterRepositorImp;

	public FilterActivationDTO getAllValuesPossibleToFilterToActivation(LocalDate initialDate, LocalDate finalDate,
			List<Brand> brands) {
		FilterActivationDTO filterActivationDTO = new FilterActivationDTO();
		filterActivationDTO.setShop(generateShopDTO(filterRepositorImp.getAllValuesToShopPossibleToFilter(initialDate, finalDate,
				brands.stream().map(Brand::getId).collect(Collectors.toList()))));
		filterActivationDTO.setProject(generateProjectDTO(filterRepositorImp.getAllValuesToProjectPossibleToFilter(initialDate, finalDate,
				brands.stream().map(Brand::getId).collect(Collectors.toList()))));
		return filterActivationDTO;
	}

	public FilterDataTableDataActivityDTO getAllValuesPossibleToFilterToDataTableDataActivity() {
		FilterDataTableDataActivityDTO filterDataTableDataActivityDTO = new FilterDataTableDataActivityDTO();
		filterDataTableDataActivityDTO
				.setShop(filterRepositorImp.getAllValuesToShopPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO
				.setBrand(filterRepositorImp.getAllValuesToBrandDescriptionPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setDaysInWeekContracted(
				filterRepositorImp.getAllValuesToDaysInWeekContractedPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO
				.setDescription(filterRepositorImp.getAllValuesToDescriptionPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setHoursContracted(
				filterRepositorImp.getAllValuesToHoursContractedPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO
				.setProject(filterRepositorImp.getAllValuesToProjectPossibleToFilterInDataTableDataActivity());
		return filterDataTableDataActivityDTO;
	}

	private List<ShopDTO> generateShopDTO(List<Object[]> datas) {
		List<ShopDTO> dtos = new ArrayList<>();
		for (Object[] data : datas) {
			ShopDTO dto = new ShopDTO();
			dto.setId(((BigInteger) data[0]).longValue());
			dto.setName((String) data[1]);
			dtos.add(dto);
		}
		return dtos;
	}
	
	private List<ProjectDTO> generateProjectDTO(List<Object[]> datas) {
		List<ProjectDTO> dtos = new ArrayList<>();
		for (Object[] data : datas) {
			ProjectDTO dto = new ProjectDTO();
			dto.setId(((BigInteger) data[0]).longValue());
			dto.setName((String) data[1]);
			dtos.add(dto);
		}
		return dtos;
	}

}
