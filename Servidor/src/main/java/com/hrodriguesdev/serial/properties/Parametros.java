package com.hrodriguesdev.serial.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.MainApp;

public class Parametros {	
    public static Double pesoCorte, pesoSemCarrinho, bordaDeSubida;
    public static int deley, confirmacao, metodo;

    private String pathToProperties = null;

    private File arquivoParams;

    
	public void writeParams() {
    	createdDiretorioAndFile();    	
    	pathToProperties = System.getProperty("user.home").toString() + MainApp.diretorioStr2 +"\\" + MainApp.Params;  
    	if(pathToProperties!=null)
    		try(BufferedReader br = new BufferedReader(new FileReader(pathToProperties) ) ){
    			String itemsParams = br.readLine();
    			String[] line = new String[7];
				
    			int i = 0;
    			while(itemsParams != null) {				
					line[i] = itemsParams;								
					itemsParams = br.readLine();
					i++;
					
				}	
				if(!line[0].isEmpty())
					deley = Integer.parseInt(line[0]);
				if(!line[1].isEmpty())
					confirmacao = Integer.parseInt(line[1]);
				if(!line[2].isEmpty())
					pesoSemCarrinho = Double.parseDouble( line[2] );
				if(!line[3].isEmpty())
					pesoCorte = Double.parseDouble( line[3] );
				if(!line[4].isEmpty())
					bordaDeSubida = Double.parseDouble( line[4] );
				if(!line[5].isEmpty())
					metodo = Integer.parseInt( line[5] );			
				
	    	}catch (IOException e) {
				System.out.println(e.getMessage());
			}catch(NullPointerException e) {
				System.out.println("Falha no caminho do arquivo de propriedades!");
			}
    	else {
    		
    	}
	}

	private void createdDiretorioAndFile() {

		try {
			File diretorio1 = new File(System.getProperty("user.home").toString() + MainApp.diretorioStr1);
			diretorio1.mkdir();
			File diretorio2 = new File(System.getProperty("user.home").toString() + MainApp.diretorioStr2);
			diretorio2.mkdir();
			arquivoParams = new File(diretorio2, MainApp.Params );
			if(!arquivoParams.exists()) {
				arquivoParams.createNewFile(); 
				writeFile();
			}
				
			
		}catch (IOException e1) {
			e1.printStackTrace(); 
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	
	}
	
	private void writeFile() {
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(arquivoParams))){
			for(String x : configDefalt()) {
				bw1.write(x);
				bw1.newLine();
			}
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		
	}

	private List<String> configDefalt(){
		List<String> list = new ArrayList<>();
		list.add("500");
		list.add("2");
		list.add("700");
		list.add("50");
		list.add("5");
		list.add("1");
		return list;
	}
	
}
