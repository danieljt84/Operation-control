package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class PropertiesReader {
	public static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
<<<<<<< HEAD
			file = new FileInputStream("C:\\Users\\4P\\eclipse-workspace\\Operation-control\\src\\main\\resources\\data.properties");
=======
			file = new FileInputStream("C:\\Users\\SERVIDOR APP\\Documents\\Projetos\\Controle Operacional\\Operation-control\\src\\main\\resources\\data.properties");
>>>>>>> d5193faebdfc9221effa33a52c1bd86b63412fc0
			props.load(file);
			return props;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
