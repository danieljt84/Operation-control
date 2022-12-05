package com.service.operation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.filter.FilterActivationDTO;
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
}
