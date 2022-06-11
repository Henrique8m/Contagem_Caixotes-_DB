package com.hrodriguesdev.controller;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.serial.controller.SerialController;
import com.hrodriguesdev.serial.properties.Parametros;


public class PesagemController extends Thread {
	private SerialController controller;
	private Thread thread = new Thread(this);
	private int filtro = 0;
	private int filtroDown = 0;
	private int filtroDownSave = 0;
	
	private Double valueStabilized;
	private Double oldValue = 0d;
	private Boolean auxSave = false;
	private Boolean auxSaveMetodo2 = false;
	private Boolean estabilized = false;
	private Double rascunho;
	
	public void start() {
		thread.start();
	}
	
	
	@Override
	public void run() {
		controller = MainApp.serialController;
		while(true) {
			synchronized (controller) {
				try {
					controller.wait();
					toCompare( controller.getDisplay() );
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					System.out.println("Problemas no wait");
				}
			}
		}
	}
	
	private void toCompare( Double newValue ) {
		try {
			Double bordaDeSubida = Parametros.bordaDeSubida;
			Double balancaVazia = Parametros.pesoCorte;
			

			
			if(newValue > balancaVazia) {
				
				estabilized = valuestabilized(newValue);
				
				if(newValue > (oldValue + bordaDeSubida) && !estabilized) {	
					oldValue = newValue;
					rascunho = newValue;
					if(Parametros.metodo == 1) {
						auxSave = true;	
					}else {
						auxSaveMetodo2 = true;
					}
//					System.out.println("newValue > (oldValue + bordaDeSubida) && newValue > 500d  == " + newValue + 
//							"\nestabilizado? " + estabilized + "\nValor estabilizado = " + valueStabilized);
										
				}
				
			}
			
			if(auxSaveMetodo2 && Parametros.metodo == 2 && newValue > Parametros.pesoSemCarrinho) {
				auxSave = true;	
			}
			
			if( newValue < balancaVazia && newValue >= 0d && auxSave ) {
				filtroDownSave++;
				if(filtroDownSave >= Parametros.confirmacao ) {
					if(estabilized) {
						MainApp.viewController.addPesagem(valueStabilized);	
//						System.out.println("Balanca esvaziou e salvou o peso estabilizado de " + valueStabilized + " Kilos");
						
					}else {
						MainApp.viewController.addPesagem(rascunho);						
//						System.out.println("Balanca esvaziou e salvou o rascunho de " + rascunho + " Kilos");
						
					}
					filtroDownSave = 0;
					estabilized = false;
					filtro = 0;
					filtroDown = 0;
					valueStabilized=0d;
					oldValue = 0d;
					auxSave = false;
				}
				
			}
		}catch(NullPointerException e ) {
			System.out.println("NullPoint in HistoryService no if");
			e.printStackTrace();
		}
		
	}
	
	private boolean valuestabilized(Double newValue) {
		if( valueStabilized == null ) valueStabilized=0d;
		
		//
		if( valueStabilized >( newValue - 3.0 ) && valueStabilized <( newValue + 3.0 ) ) {
			valueStabilized = newValue;
			if(filtro >= Parametros.bordaDeSubida) {
				filtroDown = 0;
				return true;
				
			}else {
				filtro++;
				return false;
				
			}
				
		//filtro de descida, para que na hora do carrinho sair, não estrague o processo;
		}else if(estabilized && valueStabilized > newValue) {
			filtroDown++;
			
			//Se contar mais de tres vezes, pode se dizer que realmente o processo caiu de valor.
			if(filtroDown >= MainApp.filtroBalancaEsPesoDescendo ) {
				filtro = 0;
				valueStabilized = newValue;
				return false;
				
			}else return true;
			
		}		
		valueStabilized = newValue;		
		return false;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
