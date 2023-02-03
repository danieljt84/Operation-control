package com.service.finance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

	
	public List<SummaryBillingView> getSummaryBilling(LocalDate start,LocalDate end){
		List<SummaryBillingView> summaryBillingViews = new ArrayList<>();
		summaryBillingViews.addAll(getSummaryBillingWithOutInactive(start));
		summaryBillingViews.addAll(getSummaryBillingWithInactive(start,end));
		return summaryBillingViews;
	}
	
	
	private List<SummaryBillingView> getSummaryBillingWithOutInactive(LocalDate start) {
		/*return billingRepository.getSummaryBillingWithOutInactive().stream()
				.map(element -> new SummaryBillingView(new DataActivity(((BigInteger) element[0]).longValue())
						, new BigDecimal((Double) element[1]),null))
				.collect(Collectors.toList());
				*/
		return billingRepository.getSummaryBillingWithOutInactive(start).stream().map(element -> new SummaryBillingView(new DataActivity(((BigInteger) element[0]).longValue())
						, new BigDecimal((Double) element[1]),null,null)).collect(Collectors.toList());
		
	}
	
	private List<SummaryBillingView> getSummaryBillingWithInactive(LocalDate start, LocalDate end) {
		return billingRepository.getSummaryBillingWithInactive(start,end).stream().map(element -> new SummaryBillingView(new DataActivity(((BigInteger) element[0]).longValue())
				, new BigDecimal((Double) element[1]),(String) element[2],null)).collect(Collectors.toList());

	}

}
