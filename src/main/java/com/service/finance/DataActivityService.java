package com.service.finance;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.form.finance.FilterDataTableDataActivityForm;
import com.model.Shop;
import com.model.finance.DataActivity;
import com.repository.finance.DataActivityCustomRepository;
import com.repository.finance.DataActivityRepository;

@Service
public class DataActivityService {

	@Autowired
	DataActivityRepository dataActivityRepository;
	@Autowired
	DataActivityCustomRepository dataActivityCustomRepository;

	public DataActivity save(DataActivity dataActivity) {
		return this.dataActivityRepository.save(dataActivity);
	}

	public DataActivity findById(Long id) {
		Optional<DataActivity> optional = dataActivityRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException("DADOS DE ATIVIDADE -" + id + "- NÃO ENCONTRADO");
		}
	}

	public List<DataActivity> findAll() {
		return dataActivityRepository.findAll();
	}

	public DataActivity update(DataActivity dataActivity) {
		DataActivity reference = dataActivityRepository.getById(dataActivity.getId());
		reference.setActivity(dataActivity.getActivity());
		reference.setDaysInWeekContracted(dataActivity.getDaysInWeekContracted());
		reference.setHoursContracted(dataActivity.getHoursContracted());
		reference.setPrice(dataActivity.getPrice());
		reference.setShop(dataActivity.getShop());
		reference.setType(dataActivity.getType());
		return dataActivityRepository.save(reference);
	}

	public List<BigInteger> findByFilter(FilterDataTableDataActivityForm filter) {
		return dataActivityCustomRepository.findByFilter(filter);
	}

	public List<Shop> getShopsByActivity(List<Long> activityIds) {
		return dataActivityRepository.getShopsByActivity(activityIds);
	}
}
