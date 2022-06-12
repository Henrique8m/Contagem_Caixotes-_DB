package com.hrodriguesdev.serial.dao;

import java.io.IOException;
import java.io.OutputStream;

import javax.comm.SerialPort;

import com.hrodriguesdev.serial.properties.Parametros;

public class WriteGenericData {	
	private OutputStream saida;

	
	public void writeData(byte[] bufferWrite, SerialPort serialPort, int BufferSize) {

		//Padrao do bufer = 8
		//Indicadores alfa = 27
		try {
			Thread.sleep(Parametros.deley);
			serialPort.setOutputBufferSize(BufferSize);
			saida = serialPort.getOutputStream();
			saida.write(bufferWrite);
			//Thread.sleep(0);
			saida.flush();
			Thread.sleep(50);
			//saida.close();
		} catch (IOException e) {
			System.out.println("Erro ao enviar os dados! STATUS: ");
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
