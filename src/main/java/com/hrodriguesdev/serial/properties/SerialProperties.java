package com.hrodriguesdev.serial.properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SerialProperties {
    private String porta;
    private int baud = 19200;
    private int timeout = 500;
    private int stopBits = 1;
    private String paridade = "None";
    private String pathToProperties = null;
    private String properties = "SerialProperties";
    
    
    public SerialProperties(String porta){
        this.porta = porta;
    }

    public SerialProperties(){
    	try {
    		pathToProperties = getClass().getResource(properties + ".csv").getPath();
    	}catch(NullPointerException e) {
    		//System.out.println("Falta o arquivo de propriedades!");
    	}
    	if(pathToProperties!=null)
    	try(BufferedReader br = new BufferedReader(new FileReader(pathToProperties) ) ){
			
		String itemsCsv = br.readLine();
			while(itemsCsv != null) {				
				String[] line = itemsCsv.split(",");
				System.out.println(line[1]);								
				itemsCsv = br.readLine();
			}	
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}catch(NullPointerException e) {
			System.out.println("Falha no caminho do arquivo de propriedades!");
		}
    	
    	/*
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(path1))){
			for(ProductFile x : controler.reader) {
				bw1.write(x.toString());
				bw1.newLine();
			}
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		*/
	}

	public String getPorta() {
        return porta;
    }

    public int getBaud() {
        return baud;
    }

    public int getTimeout() {
        return timeout;
    }
    
    public int getStopBits() {
        return stopBits;
    }

	public String getParidade() {
		return paridade;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}
	
}
