package com.econorma.util;

 

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import jssc.SerialPortList;

public class SerialUtil {

	/**
	 * Returns a vector where each element of the vector is a string containing
	 * the name of a serial port in the system.
	 */
 

	/**
	 * 
	 * @return the name of the first available port or null if none can be used.
	 */
 
	
	public static String getFirstPortAvailable() {
		String [] ports = getSerialPorts();
		
		for(String s : ports) {
			return s;
		}
		 
		return null;
	}
	
	public static String[] getSerialPorts() {
		
		String[] portNames = SerialPortList.getPortNames();
	
	return portNames;
	}
}
