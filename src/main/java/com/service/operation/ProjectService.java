package com.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.model.Project;
import com.repository.ProjectRepository;

@Service
public class ProjectService {
	
    @Autowired
    ProjectRepository projectRepository;
    
    
    public Project check(Project project) {
    	var optional = projectRepository.findById(project.getId());
    	if(optional.isPresent()) {
    		return optional.get();
    	}else {
    		return projectRepository.save(project);
    	}
    }
	
	

}
