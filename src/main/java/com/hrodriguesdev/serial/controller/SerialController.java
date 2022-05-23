package com.hrodriguesdev.serial.controller;

import javax.comm.SerialPort;

import com.hrodriguesdev.MainApp;
import com.hrodriguesdev.gui.controller.MainViewController;
import com.hrodriguesdev.serial.service.FormatData;
import com.hrodriguesdev.serial.service.SerialService;
import com.hrodriguesdev.serial.utilitary.CalculatorByteInt;
import com.hrodriguesdev.serial.utilitary.CalculatorData;
import com.hrodriguesdev.serial.utilitary.Gadgets;


public class SerialController implements Runnable{
	//@Autowired
	private MainViewController view = MainApp.viewController;
    private SerialService serialService = new SerialService();
    private FormatData formatData = new FormatData();
    
    private byte[] bufferWrite= new byte[8];
    private byte[] bufferRead = new byte[7];
    private byte[] bufferReadAlfa = new byte[17];
    
    private int bufferSizeRead;
    private int bufferSizeWrite;
    private int endereco = 1;
	private int negativo;
	private int stavel;
	private int saturada;
     

	private static Double display = 0d;

    private Thread thread = new Thread(this);
 
    public SerialController() {	}
    
	@SuppressWarnings({"deprecation" })
	public void read() throws InterruptedException {		
		if(!thread.isAlive()){
			thread.start();
			
		}else {
			thread.resume();
			
		}			
	}

    @Override
    public void run() {
    	view = MainApp.viewController;
    	while(true) {
			//Thread.sleep();
			if(thread.isInterrupted()) {
				view.comunicacao.setText("Lost");
				return;
				
			}		    	
			sweep(serialService.enablePortCom());
			
		}
    }
    
    private void sweep(SerialPort serial) {
    	try{
        	if(serial != null) {            
                bufferWrite = CalculatorData.addressReadAlfa(endereco,Gadgets.ALFA.getRegistrador(),Gadgets.ALFA.getTotalRegistradores()); 
				bufferSizeRead = Gadgets.ALFA.getBufferRead();
				bufferSizeWrite = Gadgets.ALFA.getBufferWrite();
				serialService.writeData(bufferWrite, serial, bufferSizeWrite);
            	bufferReadAlfa = serialService.readData(serial, bufferSizeRead);	            	
                
                if(bufferRead != null) {  
        			byte[] valueByte = new byte[4];
        			valueByte = CalculatorByteInt.intToByteWord16( Byte.toUnsignedInt(bufferReadAlfa[4] ) ) ;
        			String binario = CalculatorByteInt.binario(valueByte[0]);
        			negativo = Integer.parseInt( String.valueOf(binario.charAt(3) ) );
        			stavel = Integer.parseInt( String.valueOf(binario.charAt(2) ) );
        			saturada = Integer.parseInt( String.valueOf(binario.charAt(1) ) );
        			
        			//if(stavel==1 && saturada==1) {
            		if(saturada==1) {
        				if(negativo==1)
        					display = formatData.formatDataAlfa(bufferReadAlfa); 
        				else if(formatData.formatDataAlfa(bufferReadAlfa)<= MainApp.balancaVazia)
        					display = formatData.formatDataAlfa(bufferReadAlfa);
    			    	synchronized (this) {
    			    		notify();
    			    		
    					}
        				
        			}
                	view.comunicacao.setText("OK");
                	
                }else view.comunicacao.setText("ReadOff");

                bufferReadAlfa = null;
                

            }else view.comunicacao.setText("Lost");

        }catch(Exception e) {
        	System.out.println("SerialController-Sweep");
        	e.printStackTrace();
        }
    }
    
    public Double getDisplay() {
    	return display;
    }
}
