package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.serial.properties.Parametros;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ConfigurationViewController implements Initializable {
	@FXML
	private TextField deley, confirmacao, pesoCorte, pesoSemCarrinho, bordaDeSubida, metodo;
	@FXML
	private Button gravar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	   deley.setText( String.valueOf( Parametros.deley ) );
	   pesoCorte.setText( String.valueOf( Parametros.pesoCorte ) );
	   pesoSemCarrinho.setText( String.valueOf( Parametros.pesoSemCarrinho ) );
	   bordaDeSubida.setText( String.valueOf( Parametros.bordaDeSubida ) );
	   confirmacao.setText( String.valueOf( Parametros.confirmacao ) );
	   metodo.setText( String.valueOf( Parametros.metodo ) );
		
	}
	
	@FXML
	public void gravar(ActionEvent event) {
		   Parametros.deley = Integer.parseInt( deley.getText() );
		   Parametros.pesoCorte = Double.parseDouble( pesoCorte.getText() );
		   Parametros.pesoSemCarrinho = Double.parseDouble( pesoSemCarrinho.getText() );
		   Parametros.bordaDeSubida = Double.parseDouble( bordaDeSubida.getText() );
		   Parametros.confirmacao = Integer.parseInt( confirmacao.getText() );
		   Parametros.metodo = Integer.parseInt( metodo.getText() );
		   Stage stage = (Stage) gravar.getScene().getWindow();
		   stage.close();
	}
	
	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" && event.getCode().toString() != "LEFT" && event.getCode().toString() != "RIGHT" ) {		

			if(event.getTarget().equals(deley)) {
				String replace = deley.getText().replaceAll("[^0-9]+", "");
				deley.setText( replace );
				deley.end();
				
			}else if(event.getTarget().equals(confirmacao)) {
				String replace = confirmacao.getText().replaceAll("[^0-9]+", "");
				confirmacao.setText( replace );
				confirmacao.end();
				
			}else if(event.getTarget().equals(pesoCorte)) {
				pesoCorte.setText( replace(pesoCorte.getText()) );
				pesoCorte.end();
								
			}else if(event.getTarget().equals(pesoSemCarrinho)) {				
				pesoSemCarrinho.setText( replace( pesoSemCarrinho.getText() ) );
				pesoSemCarrinho.end();
				
			}else if(event.getTarget().equals(bordaDeSubida)) {
				bordaDeSubida.setText( replace( bordaDeSubida.getText() ) );
				bordaDeSubida.end();
				
			}else if(event.getTarget().equals(metodo)) {
				String replace = metodo.getText().replaceAll("[^0-9]+", "");
				metodo.setText( replace );
				metodo.end();
				
			}			
		}

	}
	
	private String replace(String input) {
		String replace;
		input = input.replaceAll("[^0-9]+", "");
		StringBuilder stringBuilder = new StringBuilder(input);;
		
		if(input.length()>1) {
			replace = stringBuilder.insert(input.length()-1, '.').toString();
			if(input.length()>2)
				if(replace.charAt(0)=='0') {
					replace = replace.replaceFirst("0", "");				
				}
			
		}else if(input.length() == 1 ) {
			replace = stringBuilder.insert(input.length()-1, "0.").toString();
		}else {
			replace = stringBuilder.insert(input.length(), "0.0").toString();
		}
		return replace;
	}

}
