package com.hrodriguesdev.serial.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerialProperties {
    private String porta = "COM4";
    private int baud = 19200;
    private int timeout = 500;
    private int stopBits = 1;
    private String paridade = "None";
    private String pathToProperties = null;
    private String properties = "SerialProperties.properties";
    private String diretorioStr1 = "\\AppData\\Local\\YggDrasil";
    private String diretorioStr2 = "\\AppData\\Local\\YggDrasil\\serial";
    
    private File arquivoProperties;
    
    
    public SerialProperties(String porta){
        this.porta = porta;
    }

    public SerialProperties(){
    	File file;
    	try {
    		pathToProperties = getClass().getResource(properties).getPath();
    		
    	}catch(NullPointerException e) {	
    		createdDiretorioAndFile();
    			writeFile();
    		
  
    	}
    	if(pathToProperties!=null)
    		try(BufferedReader br = new BufferedReader(new FileReader(pathToProperties) ) ){
    			String itemsProperties = br.readLine();
    			String[] line = new String[5];
				int i = 0;
    			while(itemsProperties != null) {				
					line[i] = itemsProperties;
					System.out.println(line[1]);								
					itemsProperties = br.readLine();
					i++;
					
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

	private void writeFile() {
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(arquivoProperties))){
			for(String x : configDefalt()) {
				bw1.write(x);
				bw1.newLine();
			}
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		
	}

	private void createdDiretorioAndFile() {
		
		try {
			File diretorio1 = new File(System.getProperty("user.home").toString() + diretorioStr1);
			diretorio1.mkdir();
			File diretorio2 = new File(System.getProperty("user.home").toString() + diretorioStr2);
			diretorio2.mkdir();
			arquivoProperties = new File(diretorio2, properties );
			arquivoProperties.createNewFile(); 
			
		}catch (IOException e1) {
			e1.printStackTrace(); 
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	
	}
	
	private List<String> configDefalt(){
		List<String> list = new ArrayList<>();
		list.add("19200");
		list.add("1");
		list.add("COM4");
		return list;
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
