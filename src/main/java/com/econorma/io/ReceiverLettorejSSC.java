package com.econorma.io;
 
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.econorma.data.Lettura;
import com.econorma.util.Logger;

import jssc.SerialPort;
import jssc.SerialPortException;


public class ReceiverLettorejSSC  {
	
	private static final Logger logger = Logger.getLogger(ReceiverLettorejSSC.class);
	private static final String TAG = ReceiverLettorejSSC.class.getSimpleName();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final NumberFormat nf = new DecimalFormat("#####.##");

	private String portName;
	private SerialPort serialPort;
	private boolean running;
	private String idSonda;
	private String data;
	private String valore;
	
//	private List<Lettura> letture = new ArrayList<Lettura>();
	private HashMap<String,Lettura> letture = new HashMap<String,Lettura>();
	 

	public ReceiverLettorejSSC(String com) {
	  	portName = com;
		running = true;
	}

	 
	public void run() {
		
		logger.info(TAG, "Receiver su " + portName + ": [CONNECTING...]");
		
		try{
			if (connect()) {
				
				SerialBuffer serialBuffer = new SerialBuffer();
				logger.info(TAG, "Receiver su " + portName + ": [CONNECTED]");
				while (running) {
					try {
						
						 int available = serialPort.getInputBufferBytesCount();
						// leggi
						 
						 if(available>0){
							 String readString = serialPort.readString(available);
							 
							 logger.debug(TAG,  portName + ": Data " + sdf.format(new Date()) + ": lettura parziale [" + readString + "]" );
							 
							 serialBuffer.add(readString);
							 
							 List<String> lines = serialBuffer.getLines();
								
							 logger.debug(TAG,"\t"+ (lines!=null ? lines.size() : 0)+" righe complete dal buffer");
							 
							 if(lines!=null){
								 	
									for(String line : lines){
										logger.info(TAG, "\t"+ portName + ": parsing [" + line + "]" + " Data " + sdf.format(new Date()));
										
									 
										
										final Lettura lettura ;
										
										{
											Lettura tmpLettura = null;
											try{
												tmpLettura = Parsers.createLettura(line);
											}catch (Exception e) {
							 
												logger.warn(TAG, "Receiver su " + portName + ": cannot parse data [" + line + "]");
											}
											lettura = tmpLettura;
										}
										if (lettura != null) {
											logger.debug(TAG, "Receiver su " + portName + ": lettura ok");
											//TODO
  
//											letture.add(lettura);
											
											letture.put(lettura.getIdSonda(),lettura);
											
										}else{
											logger.debug(TAG, "Receiver su " + portName + ": lettura fail");
										}
										
									}
								}
						 }else{
							 
							 
							 Thread.sleep(100);
						 }
						 
							
//						if (isInterrupted()) {
//							logger.debug(TAG, "Receiver su " + portName + ": interrupt received. Quit");
//							break;
//						}
					// exits
					} catch (InterruptedException e1) {
						// ignore
						logger.debug(TAG, "Receiver su " + portName + ": interrupt received. Quitting? "+!running);
					} catch (Exception e) {
						logger.error(TAG, "Receiver su " + portName + ", errore", e);
 
					}
				} // for loop
				logger.info(TAG, "Receiver su " + portName + " exiting... running = ["+running+"]");
			} // connect
		} finally {
			
			try{ if(serialPort!=null)serialPort.closePort();}catch (Exception e) {}
			logger.info(TAG, "Receiver su " + portName + " uscito [stream chiuso]");
		}
		
		logger.info(TAG, "Receiver su " + portName + ": [TERMINATED]");

	}

	public void die() {
		running = false;
		logger.info(TAG, "Receiver su " + portName + " : Quit PLEASE!");
	}

	private boolean connect() {
		try {
			if(serialPort!=null){
				logger.info(TAG, "Receiver su " + portName
						+ ": Closing current port...");
				try{serialPort.closePort();}catch (Exception e) {}
				serialPort = null;
			}
			
			serialPort = new SerialPort(portName);
			
			boolean opened = false;
			try{
				opened = serialPort.openPort();
				if(opened){
					 serialPort.setParams(SerialPort.BAUDRATE_9600, 
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
					 return true;
				}
			}catch(SerialPortException spe){
				if(SerialPortException.TYPE_PORT_BUSY.equals(spe.getExceptionType())){
					logger.info(TAG, "Receiver su " + portName
							+ ": Port in use");
 
				}
				
			}
			
			if(opened){
 
			}
			return false;
		} catch (Exception e) {
			logger.fatal(TAG, "Receiver su " + portName
					+ ": Generic error while using port", e);
 
			return false;
		}
	}
	
	
//	public List<Lettura> getLetture(){
//		return letture;
//	}
	
	public HashMap<String, Lettura> getLetture(){
		return letture;
	}

}