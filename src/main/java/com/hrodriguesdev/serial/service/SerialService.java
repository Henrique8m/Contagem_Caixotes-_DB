package com.hrodriguesdev.serial.service;

import javax.comm.SerialPort;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.serial.dao.ReadGenericData;
import com.hrodriguesdev.serial.dao.WriteGenericData;
import com.hrodriguesdev.serial.properties.SerialProperties;
import com.hrodriguesdev.serial.resurce.PortComResurce;


public class SerialService{
	private PortComResurce resurce = new PortComResurce();
	private WriteGenericData write = new WriteGenericData();
	private ReadGenericData read = new ReadGenericData();	
	private SerialProperties serialProperties;
	

	public SerialPort enablePortCom() {
		serialProperties = MainApp.serialProperties;
		if(resurce.getPortIdentifier(serialProperties.getPorta())) {
			return resurce.openPort(serialProperties.getBaud(), serialProperties.getTimeout(), serialProperties.getStopBits());
			
		}else {
			//System.out.println("SerialService-enablePortCom fail");
			return null;
		}
		
	}

	public void writeData(byte[] bufferWrite, SerialPort serial, int BufferSize) {
		write.writeData(bufferWrite, serial, BufferSize);
		
	}

	public byte[] readData(SerialPort serial, int bufferSize) {
		return read.readData(serial, bufferSize);

	}
}