package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDF {
	
	private String caminho = "C:\\Users\\Henrique\\Desktop\\PDF Teste Carvao.pdf";
	//C:\\Users\\henri\\Desktop\\PDF teste.pdf
	private Document document;
	private Paragraph paragraph;
	private String nome, placa, cidade, estado, telefone;
	private double pesoTotal;
	
	public Boolean newDocument(Motorista motoristaPDF, List<Pesagem> listPDF) {
		document = new Document();
		pesoTotal = 0d;
		File file = new File(caminho);	
		if(file.exists()) {
			file.delete();
		}

		try { 
		   	PdfWriter.getInstance(document, new FileOutputStream(caminho));
	    	document.open();
	    	document.addTitle("Relatório de Pesagem");
	    	
        	paragraph = new Paragraph("Relatório de Pesagem");
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
	     }
	     catch(IOException ioe) {
	         System.err.println(ioe.getMessage());
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
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
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
