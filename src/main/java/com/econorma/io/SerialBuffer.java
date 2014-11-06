package com.econorma.io;

import java.util.ArrayList;
import java.util.List;

public class SerialBuffer {
	
	private static final String CR_LF =  "\r\n";
	private static final String LF = "\n";
	private static final String CR = "\r";

	StringBuilder sb = new StringBuilder();
	public SerialBuffer(){}
	
	public  void add(String s){
		if(s!=null)
			sb.append(s);
	}
	
	public List<String> getLines(){
		List<String> lines = null;
		
	
		while(true){
			//cerco caratteri di fine riga
			int cr_lf_idx = sb.indexOf(CR_LF);
			if(cr_lf_idx==-1)
				cr_lf_idx = Integer.MAX_VALUE;
			int lf_idx = sb.indexOf(LF);
			if(lf_idx==-1)
				lf_idx = Integer.MAX_VALUE;
			int cr_idx = sb.indexOf(CR);
			if(cr_idx==-1)
				cr_idx = Integer.MAX_VALUE;
			
			// se non c'è metto al max
			
			//prendo la prima occorrenza tra i caratteri di break
			int match = Math.min(cr_lf_idx, Math.min(lf_idx,cr_idx));
			
			if(match<Integer.MAX_VALUE){
				int matchLength = 0;
				if(match==cr_lf_idx){
					matchLength = 2;
				}else{
					matchLength = 1;
				}
				if(lines==null){
					lines = new ArrayList<String>();
				}
				//aggiungo il contenuto
				lines.add(sb.substring(0, match));
				
				//rimuovo il match dal buffer
				sb.replace(0, match+matchLength, "");
				
			}else
				break;
		}
		
		return lines;
	}
}
