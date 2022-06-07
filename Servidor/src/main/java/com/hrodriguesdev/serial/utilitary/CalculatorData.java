package com.hrodriguesdev.serial.utilitary;

public interface CalculatorData {
	
	
	static byte[] addressRead(int endereco, int registrador) {
		byte[] bufferWrite = new byte[8];
		if ((endereco > 0) && (endereco < 256)) {
			bufferWrite[0] = (byte) endereco;
			bufferWrite[1] = (byte) 3; // Metodo de escrita
			bufferWrite[2] = (byte) 0;
			bufferWrite[3] = (byte) registrador;
			bufferWrite[4] = (byte) 0;
			bufferWrite[5] = (byte) 1; // registrador
			
			int[] crc = CalculatorCRC.calculateCRC(bufferWrite, 0, 6); //calcular o crc
			
			bufferWrite[6] = (byte) crc[0];
			bufferWrite[7] = (byte) crc[1];

		} else {
			System.out.println("Endereço fora da faixa");
		}
		return bufferWrite;
	}
	
	static byte[] addressReadAlfa(int endereco, int registrador, int totalRegistradores) {
		byte[] bufferWrite = new byte[8];
		if ((endereco > 0) && (endereco < 256)) {
			bufferWrite[0] = (byte) endereco;
			bufferWrite[1] = (byte) 3; // Metodo de escrita
			bufferWrite[2] = (byte) 0;
			bufferWrite[3] = (byte) registrador;
			bufferWrite[4] = (byte) 0;
			bufferWrite[5] = (byte) totalRegistradores; // registrador
			
			int[] crc = CalculatorCRC.calculateCRC(bufferWrite, 0, 6); //calcular o crc
			
			bufferWrite[6] = (byte) crc[0];
			bufferWrite[7] = (byte) crc[1];

		} else {
			System.out.println("Endereço fora da faixa");
		}
		return bufferWrite;
	}

}
