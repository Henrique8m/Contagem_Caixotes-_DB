package com.hrodriguesdev.securit;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.hrodriguesdev.MainApp;

public class DataSecurit {
	
	public static Boolean validateData() {
		File file = null;
		Date data = new Date(System.currentTimeMillis()); 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyyMMdd");
		int dataInt = Integer.parseInt(formatarDate.format(data));
		//System.out.println(dataInt);
		if(dataInt > MainApp.dataSpired) {
			file = new File("C:\\Program Files\\Java\\resources\\db.properties" );
			file.delete();
			return false;
		}
		return true;
	}
	
}
