package com.hrodriguesdev.serial.utilitary;

public enum Gadgets {	
	
// divisao, registrador ,bufferWrite, bufferRead, bufferReadMs ,bufferReadLs;
	N2000(100, 2, 8, 7, 4, 3),
	N1500(1, 1, 8, 7, 4, 3),
	N1540(10, 1, 8, 7, 4, 3),
	N1500_4_a_20(1, 1, 8, 7, 4, 3),
	N1540_4_a_20(1000, 1, 8, 7, 4, 3),
	
	//	divisao, registrador, totalRegistradores ,bufferWrite, bufferRead, bufferRead1Ms, bufferReadMs ,bufferReadLs;
	ALFA(10, 80, 6,  27, 17, 0, 10, 9),
	ALFA_LEI_SETPOINTS(10, 40, 9,  27, 23, 0, 10, 9),
	ALFA_ESC_SETPOINTS(10, 30, 9,  27, 23, 0, 10, 9),
	
	//Primeiro o de multiplicação, depois o de soma
	ALFA_FORMAT_SETPOINTS_VAZIA(19, 20),
	ALFA_FORMAT_SETPOINTS_1_4(7, 8),
	ALFA_FORMAT_SETPOINTS_2_5(11, 12),
	ALFA_FORMAT_SETPOINTS_3_6(15, 16);
	
	
	int divisao;
	int registrador;
	int totalRegistradores;
	int bufferWrite;
	int bufferRead;
	int bufferRead1Ms;
	int bufferReadMs;
	int bufferReadLs;
	
	int formatDataLs;
	int formatDataMs;
	
	Gadgets(int divisao, 
			int registrador, 
			int totalRegistradores,
			int bufferWrite,
			int bufferRead, 
			int bufferRead1Ms, 
			int bufferReadLs, 
			int bufferReadMs) {
		
		this.divisao = divisao;
		this.registrador = registrador;
		this.totalRegistradores = totalRegistradores;
		this.bufferWrite = bufferWrite;
		this.bufferRead = bufferRead;
		this.bufferRead1Ms = bufferRead1Ms;
		this.bufferReadMs = bufferReadMs;
		this.bufferReadLs = bufferReadLs;
	}


	Gadgets(int divisao, 
			int registrador, 
			int bufferWrite,
			int bufferRead, 
			int bufferReadLs, 
			int bufferReadMs) {
		
		this.divisao = divisao;
		this.registrador = registrador;
		this.bufferWrite = bufferWrite;
		this.bufferRead = bufferRead;
		this.bufferReadMs = bufferReadMs;
		this.bufferReadLs = bufferReadLs;
	}


	Gadgets (int formatDataMs ,int formatDataLs) {
		this.formatDataMs = formatDataMs;
		this.formatDataLs = formatDataLs;
	}


	public int getBufferRead1Ms() {
		return bufferRead1Ms;
	}

	public int getBufferReadMs() {
		return bufferReadMs;
	}

	public int getBufferReadLs() {
		return bufferReadLs;
	}

	public int getDivisao() {
		return divisao;
	}
	
	public int getBufferWrite() {
		return bufferWrite;
	}
	
	public int getBufferRead() {
		return bufferRead;
	}

	public int getRegistrador() {
		return registrador;
	}

	public int getTotalRegistradores() {
		return totalRegistradores;
	}

	public int getFormatDataLs() {
		return formatDataLs;
	}

	public int getFormatDataMs() {
		return formatDataMs;
	}
	
}
