package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDF {
	private Document document;
	private Paragraph paragraph;
	private String nome, placa, cidade, estado, telefone;
	private double pesoTotal;
	
	public Boolean newDocument(Motorista motoristaPDF, List<Pesagem> listPDF, int diretorio) {
		document = new Document();
		pesoTotal = 0d;				
		String data = motoristaPDF.getData();
		data = data.replaceAll("/", "-");	
		
		String local = System.getProperty("user.home")
						.toString() + 
						MainApp.caminhoPDF;
		try {
			File diretorio1 = new File(local);
			diretorio1.mkdir();
			if(diretorio == 2) {
				File diretorio2 = new File(local + 
						"\\" +
						data);
				diretorio2.mkdir();
				local = local + 
						"\\" +
						data; 
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	

		String caminho = local + 
				"\\" + 
				motoristaPDF.getPlaca() +
				"    " + 
				data +
				".pdf";
		
		File file = new File(caminho);		
		if(file.exists()) {
			file.delete();
		}

		try { 
		   	PdfWriter.getInstance(document, new FileOutputStream(caminho) );
	    	document.open();
	    	document.addTitle("Relatorio de Pesagem");
	    	
        	paragraph = new Paragraph("Relatorio de Pesagem");
    		paragraph.setAlignment(Element.ALIGN_CENTER);    		
    		document.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		    		
        	paragraph = new Paragraph( coletaDeDados(motoristaPDF) );        	
    		paragraph.setAlignment(Element.ALIGN_CENTER);
    		document.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		    		
	        for(Pesagem p: listPDF) {
	        	paragraph = new Paragraph(p.getNumeroCaixote() + "      " + p.getPeso() + "Kg        " + p.getData() + " " + p.getHora() );
	        	pesoTotal += p.getPeso();
	    		paragraph.setAlignment(Element.ALIGN_CENTER); 
	    		document.add(paragraph);
	    		 
	    	}
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
	        
	      	paragraph = new Paragraph( fechamento( pesoTotal, listPDF.size() ) );
	    	paragraph.setAlignment(Element.ALIGN_CENTER);    		
	    	document.add(paragraph);
	        
	        document.addCreationDate();  
	         
	    }catch(DocumentException de) {
	    	System.err.println(de.getMessage());
	    	return false;
	     }
	     catch(IOException ioe) {
	         System.err.println(ioe.getMessage());
	         return false;
	     }
	     document.close();
	     return true;
	     
	 }
	
	private String coletaDeDados(Motorista motoristaPDF) {
		if(motoristaPDF.getName() == null) nome = "";
		else nome = motoristaPDF.getName();
		
		if(motoristaPDF.getPlaca() == null) placa = "";
		else placa = motoristaPDF.getPlaca();
		
		if(motoristaPDF.getCidade() == null) cidade = "";
		else cidade = motoristaPDF.getCidade();
		
		if(motoristaPDF.getEstado() == null) estado = "";
		else estado = motoristaPDF.getEstado();
		
		if(motoristaPDF.getPhone() == null) telefone = "";
		else telefone = motoristaPDF.getPhone();
		
		return 	nome + "  " + 
				placa + "  " + 
    			motoristaPDF.getData() + "\n" + 
				cidade + "  " + 
    			estado + "  " + 
				telefone ;
		
	}
	
	private String fechamento(double total, int size) {
		return "Peso Total: " + total + "Kg " + "\n" + "Numero de Caixotes = " + size; 
	}
	
	String[] letters = new String[]{ "A", "B", "D", "E", "F", "G", "H", "I"};
	File[] drives = new File[letters.length];
	boolean[] isDrive = new boolean[letters.length];
//  private static final String command = "wmic logicaldisk get name";   
	
	public void checkUsbVolume() {
//        try {
//        	Process SerialNumberProcess = Runtime.getRuntime().exec(command);
//        	InputStreamReader ISR = new InputStreamReader(SerialNumberProcess.getInputStream());
//        	BufferedReader br = new BufferedReader(ISR);
//        	String items = br.readLine();    		
//			while(items != null) {	
//				System.out.println(items);
//				items = br.readLine();
//			}	
//		  	
//        	SerialNumberProcess.waitFor();
//        	br.close();
//        }catch (Exception e) {
//        	e.printStackTrace();
//
//        }
	
		// init the file objects and the initial drive state
		for ( int i = 0; i < letters.length; ++i )
			{
			drives[i] = new File(letters[i]+":");
		
			 isDrive[i] = drives[i].canRead();
			// isDrive[i] = drives[i].canWrite();
		}
	
		 System.out.println("FindDrive: waiting for devices...");
	
	    for ( int i = 0; i < letters.length; ++i ){
	        boolean pluggedIn = drives[i].canRead();
	
	        // if the state has changed output a message

	            if ( pluggedIn )
	                System.out.println("Drive "+letters[i]+" has been plugged in");
	            else
	                System.out.println("Drive "+letters[i]+" has been unplugged");
	    }
	}
      

	

}




/*
document.open();
document.setPageSize(PageSize.A3);

document.newPage();
document.add(new Paragraph("Novo parágrafo na nova página"));

pageSize.setBackgroundColor(new java.awt.Color(0xFF, 0xFF, 0xDE));

Image figura = Image.getInstance("C:\\imagem.jpg");
document.add(figura);

*/
