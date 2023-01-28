package com.service.finance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.finance.DataActivity;
import com.model.view.BillingByBrandView;
import com.model.view.SummaryBillingView;
import com.repository.finance.BillingRepository;

@Service
public class BillingService {

	@Autowired
	BillingRepository billingRepository;

	public List<SummaryBillingView> getSummaryBillingWithOutInactive() {
		return billingRepository.getSummaryBillingWithOutInactive().stream()
				.map(element -> new SummaryBillingView(new DataActivity(((BigInteger) element[0]).longValue())
						, new BigDecimal((Double) element[1]),null))
				.collect(Collectors.toList());

	}
	
	public List<SummaryBillingView> getSummaryBillingWithInactive(LocalDate start) {
		return billingRepository.getSummaryBillingWithInactive(start).stream()
				.map(element -> new SummaryBillingView(new DataActivity(((BigInteger) element[0]).longValue())
						, new BigDecimal((Double) element[1]),((Date) element[2]).toLocalDate()))
				.collect(Collectors.toList());

	}

}
