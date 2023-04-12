package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PropertiesReader {
	public static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream(new File("C:\\Users\\Daniel\\Documents\\Projetos\\Controle Operacional\\Operation-control\\src\\main\\resources\\data.properties"));
			props.load(file);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
	    }
		return props;
	}
}