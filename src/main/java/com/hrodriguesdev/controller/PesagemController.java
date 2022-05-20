package com.hrodriguesdev.controller;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.serial.controller.SerialController;


public class PesagemController extends Thread {
	private SerialController controller;
	private Thread thread = new Thread(this);
	private int filtro = 0;
	private int filtroDown = 0;
	private int filtroDownSave = 0;
	
	private Double valueStabilized;
	private Double oldValue = 0d;
	private Boolean auxSave = false;
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
					//System.out.println( controller.getDisplay() ); 
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					System.out.println("Problemas no wait");
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void toCompare( Double newValue ) {
		try {
			Double bordaDeSubida = 1.0d;
			Double balancaVazia = 5.0d;
			if(newValue > 10.0d) {
				estabilized = valuestabilized(newValue);
				if(newValue > (oldValue + bordaDeSubida) && !estabilized) {	
					oldValue = newValue;
					rascunho = newValue;
					auxSave = true;				
					System.out.println("newValue > (oldValue + bordaDeSubida) && newValue > 500d  == " + newValue + 
							"\nestabilizado? " + estabilized + "\nValor estabilizado = " + valueStabilized);
										
				}
				
			}else if( newValue < balancaVazia && newValue >= 0d && auxSave ) {
				filtroDownSave++;
				if(filtroDownSave >= 2 ) {
					if(estabilized) {
						MainApp.viewController.addPesagem(valueStabilized);	
						System.out.println("Balança esvaziou e salvou o peso estabilizado de " + valueStabilized + " Kilos");
						
					}else {
						MainApp.viewController.addPesagem(rascunho);						
						System.out.println("Balança esvaziou e salvou o rascunho de " + rascunho + " Kilos");
						
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
			if(filtro >= 6) {
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
			if(filtroDown >= 2 ) {
				filtro = 0;
				valueStabilized = newValue;
				return false;
				
			}else return true;
			
		}		
		valueStabilized = newValue;		
		return false;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
