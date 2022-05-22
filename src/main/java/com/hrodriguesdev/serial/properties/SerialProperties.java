package com.hrodriguesdev.serial.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SerialProperties {
    private String porta = "COM4";
    private int baud = 19200;
    private int timeout = 500;
    private int stopBits = 1;
    private String paridade = "None";
    private String pathToProperties = null;
    private String properties = "SerialProperties.csv";
    private String diretorioStr1 = "\\AppData\\Local\\YggDrasil";
    private String diretorioStr2 = "\\AppData\\Local\\YggDrasil\\serial";
    
    
    public SerialProperties(String porta){
        this.porta = porta;
    }

    public SerialProperties(){
    	File file;
    	try {
    		pathToProperties = getClass().getResource(properties).getPath();
    		
    	}catch(NullPointerException e) {	
    		
    		File diretorio1 = new File(System.getProperty("user.home").toString() + diretorioStr1);
    		boolean statusDir = diretorio1.mkdir();
    		File diretorio2 = new File(System.getProperty("user.home").toString() + diretorioStr2);
    		boolean statusDir2 = diretorio2.mkdir();
    		System.out.println(statusDir); 
    		File arquivo = new File(diretorio2, properties ); 
    		try {
    			boolean statusArq = arquivo.createNewFile();
    			System.out.print(statusArq);
    		} catch (IOException e1) {
    		    e.printStackTrace(); 
    		} 
    	}
    	if(pathToProperties!=null)
    		try(BufferedReader br = new BufferedReader(new FileReader(pathToProperties) ) ){
    			String itemsCsv = br.readLine();
				while(itemsCsv != null) {				
					String[] line = itemsCsv.split(",");
					System.out.println(line[1]);								
					itemsCsv = br.readLine();
					
				}	
				
	    	}catch (IOException e) {
				System.out.println(e.getMessage());
			}catch(NullPointerException e) {
				System.out.println("Falha no caminho do arquivo de propriedades!");
			}
    	else {
    		
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
