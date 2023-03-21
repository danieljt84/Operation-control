package com.util;

import java.util.ArrayList;
import java.util.List;

import com.model.Project;

public final class ProjectArray {
	
	public static List<Project> projects = new ArrayList<>();
	
	static {
		Project cfixo = new Project();
		cfixo.setId(0l);
		cfixo.setName("FIXO_RJ");
		cfixo.setNameApi("4pmktcfixo");
		
		Project compartilhadoRj = new Project();
		compartilhadoRj.setId(1l);
		compartilhadoRj.setName("COMPARTILHADO_RJ");
		compartilhadoRj.setNameApi("4pmktcompartilhado");
		
		Project compartilhadoSP = new Project();
		compartilhadoSP.setId(2l);
		compartilhadoSP.setName("COMPARTILHADO_SP");
		compartilhadoSP.setNameApi("4pmktcompartilhadosp");
		
		Project compartilhadoES = new Project();
		compartilhadoES.setId(3l);
		compartilhadoES.setName("COMPARTILHADO_ES");
		compartilhadoES.setNameApi("4pmktcompartilhadoes");
		
		Project compartilhadoBA = new Project();
		compartilhadoBA.setId(4l);
		compartilhadoBA.setName("COMPARTILHADO_BA");
		compartilhadoBA.setNameApi("4pmktcompartilhadoba");
		
		Project royal4p = new Project();
		royal4p.setId(5l);
		royal4p.setName("ROYAL4P");
		royal4p.setNameApi("royal4pmkt");
		
		projects.add(cfixo);
		projects.add(compartilhadoES);
		projects.add(royal4p);
		projects.add(compartilhadoBA);
		projects.add(compartilhadoRj);
		projects.add(compartilhadoSP);
	}
	public ProjectArray() {
		
	}
	
}
