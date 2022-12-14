package com.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.dto.filter.FilterActivationDTO;
import com.controller.dto.filter.FilterDataTableDataActivityDTO;
import com.repository.operation.FilterRepositorImp;

@Service
public class FilterService {
	
	@Autowired
	FilterRepositorImp filterRepositorImp;
	
	public FilterActivationDTO getAllValuesPossibleToFilterToActivation( LocalDate initialDate,   LocalDate finalDate,  Long brandId){
		FilterActivationDTO filterActivationDTO = new FilterActivationDTO();
		filterActivationDTO.setShop(filterRepositorImp.getAllValuesToShopPossibleToFilter(initialDate, finalDate, brandId));
		filterActivationDTO.setProject(filterRepositorImp.getAllValuesToProjectPossibleToFilter(initialDate, finalDate, brandId));
		return filterActivationDTO;
	}
	
	public FilterDataTableDataActivityDTO getAllValuesPossibleToFilterToDataTableDataActivity() {
		FilterDataTableDataActivityDTO filterDataTableDataActivityDTO =  new FilterDataTableDataActivityDTO();
		filterDataTableDataActivityDTO.setShop(filterRepositorImp.getAllValuesToShopPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setBrand(filterRepositorImp.getAllValuesToBrandDescriptionPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setDaysInWeekContracted(filterRepositorImp.getAllValuesToDaysInWeekContractedPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setDescription(filterRepositorImp.getAllValuesToDescriptionPossibleToFilterInDataTableDataActivity());
		filterDataTableDataActivityDTO.setHoursContracted(filterRepositorImp.getAllValuesToHoursContractedPossibleToFilterInDataTableDataActivity());
		return filterDataTableDataActivityDTO;
	}
	
}
