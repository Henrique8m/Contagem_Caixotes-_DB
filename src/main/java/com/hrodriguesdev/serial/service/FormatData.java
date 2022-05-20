package com.hrodriguesdev.serial.service;

import com.hrodriguesdev.serial.utilitary.Gadgets;

public class FormatData {
	private String display;
	private Integer division;
	private double valueDouble;
	
	public String formatData(byte[] bufferRead, String gatgets, String format) {
		try {
		division = Gadgets.valueOf(gatgets).getDivisao();
		if(bufferRead != null ) {			
			if (Byte.toUnsignedInt(bufferRead[3]) > 0) {
				if(format == "int")
					display = Integer.toString(((Byte.toUnsignedInt(bufferRead[4])) + ((Byte.toUnsignedInt(bufferRead[3]) * 256))) / division);
				
				else if(format == "double") {
					valueDouble = (((Byte.toUnsignedInt(bufferRead[4])) + ((Byte.toUnsignedInt(bufferRead[3]) * 256))));
					display = Double.toString(valueDouble / division);
				}
				
			} else {
				if(format == "int")
					display = Integer.toString((Byte.toUnsignedInt(bufferRead[4])) / division);
				else if(format == "double") {
					valueDouble = (Byte.toUnsignedInt(bufferRead[4])) ;
					display = Double.toString(valueDouble / division);
				}
			}
			
		}else System.out.println("buffer Read null na classe FormatData");
		
		}catch(IllegalArgumentException e ) {
			e.printStackTrace();
			return ("E " + gatgets);
		}catch (Exception e) {
			e.printStackTrace();
			return ("E " + gatgets);
		}
		return display;
	}
	
	
	@SuppressWarnings("unused")
	public Double formatDataAlfa(byte[] bufferRead) {
		//Primeiro endereço
		//Segundo Registrador
		
		//do quarto ao nono dados
		//Quarto byte, bit 4 = 1 balança em movimento
		
		//sexto e setimo peso = sexto = msword, setimo = lsword
		
		if(bufferRead != null ) {
	
			if (Byte.toUnsignedInt(bufferRead[9]) > 0) {
			
				valueDouble = (Byte.toUnsignedInt(bufferRead[10])) + ((Byte.toUnsignedInt(bufferRead[9]) * 256)) ;
				
			} else {

				valueDouble = (Byte.toUnsignedInt(bufferRead[10]));
			}
			
		}else {
			System.out.println("buffer Read null na classe FormatDataAlda");
			return null;
		}

		return valueDouble/10;
	}
	
	@SuppressWarnings("unused")
	public String formatDataAlfaGeneric(String gadgets , byte[] bufferRead, int ls1 , int ms1) {
		//Primeiro endereço
		//Segundo Registrador
		
		//do quarto ao nono dados
		//Quarto byte, bit 4 = 1 balança em movimento
		
		//sexto e setimo peso = sexto = msword, setimo = lsword
		
		if(bufferRead != null ) {			
			if (Byte.toUnsignedInt(bufferRead[ms1]) > 0) {
			
				valueDouble = (Byte.toUnsignedInt(bufferRead[ls1])) + ((Byte.toUnsignedInt(bufferRead[ms1]) * 256)) ;
				
			} else {

				valueDouble = (Byte.toUnsignedInt(bufferRead[ls1]));
			}
			
		}else {
			System.out.println("buffer Read null na classe FormatDataAlda");
			return "1.02";
		}

		return Double.toString(valueDouble/Gadgets.valueOf(gadgets).getDivisao());
	}
}
