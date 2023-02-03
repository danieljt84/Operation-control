package com.service.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.model.finance.InactiveControl;
import com.repository.finance.InactiveControlRepository;

@Service
public class InactiveControleService {

	
	@Autowired
	InactiveControlRepository inactiveControlRepository;
	
	
	public List<InactiveControl> findByDataActivity(Long idDataActivity){
		return inactiveControlRepository.findByDataActivityId(idDataActivity);
	}
}
