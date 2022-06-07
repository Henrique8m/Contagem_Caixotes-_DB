package com.hrodriguesdev.serial.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.comm.SerialPort;

public class ReadGenericData {	
	private Timer timer = new Timer(true);
	private TimerTask timerTask;
	
	public byte[] readData(SerialPort serial, int bufferSize){
		InputStream entrada; 
		byte[] bufferRead = new byte[bufferSize];
		
		//padrão bufferRead 7
		//alfa 17
		
		try {
			serial.setInputBufferSize(bufferSize);
			entrada = serial.getInputStream();
				
			timerTask = new TimerTask() {
				@Override
				public void run() {
					serial.close(); 
					}}; 
			timer.schedule(timerTask,60);
			entrada.read(bufferRead);
			
			timerTask.cancel();	
			serial.close();

			} catch (IOException e) {				
				return null;
			} catch(NullPointerException e) {
				return null;
			}
		
		return bufferRead;
	}

}
