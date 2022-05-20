package com.hrodriguesdev.serial.resurce;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class PortComResurce {

	
	private CommPortIdentifier cp;
	private SerialPort serialPort;
	
	public Boolean getPortIdentifier(String portName) {
		
		try {
			if(cp==null) cp = CommPortIdentifier.getPortIdentifier(portName);
			return true;
		} catch (NoSuchPortException e) {
			System.out.println("Porta não existe! STATUS: " + e.getMessage());	
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}

	@SuppressWarnings("static-access")
	public SerialPort openPort(int baudRate, int timeout, int stopBits) {

		try {
			serialPort = (SerialPort) cp.open("SerialService", timeout);
			serialPort.setSerialPortParams(baudRate, serialPort.DATABITS_8, stopBits, serialPort.PARITY_NONE);
		}
		catch (PortInUseException e) {
			System.out.println("Erro ao abrir a porta! STATUS: " + e.getMessage());
			return null;
		}
		catch (UnsupportedCommOperationException e) {
			System.out.println("Erro com os parametros da porta! STATUS: " + e.getMessage());
			return null;
		}
	return serialPort;
	}
	
}
