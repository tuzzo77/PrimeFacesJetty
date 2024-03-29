package com.econorma.io;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import com.econorma.data.Lettura;
import com.econorma.data.Lettura.TipoMisura;
 

public class Parsers {

	private static final Pattern UMIDITA_REGEX = Pattern.compile(".*\"(.*)\"([E|T|V])_([0-9A-F]{3})_([0-9A-F]{3})_([b|B])([0-9A-F]{2})");
	private static final int UMIDITA_NOME_SONDA_POS = 1;
	private static final int UMIDITA_TIPO_LETTURA = 2;
	private static final int UMIDITA_TEMPERATURA_POS = 3;
	private static final int UMIDITA_UMIDITA_POS = 4;
	private static final int UMIDITA_BATTERIA_STATO_POS = 5;
	private static final int UMIDITA_CHECKSUM_POS = 6;
	
	
	private static class Umidita {

		private static final double C1 = -4;
		private static final double C2 = 0.0405;
		private static final double T1 = 0.01;
		private static final double T2 = 0.00008;
		private static final double C3 = (-0.0000028);

		public static double parse(double val_sonda_ur, double tempe) {

			double val_sonda_ur_2 = Math.pow(val_sonda_ur, 2.0);
			double step1 = C1 + (C2 * val_sonda_ur);
			double step2 = C3 * val_sonda_ur_2;
			double step3 = step1 + step2;
			double RHLineare = C1 + (C2 * val_sonda_ur) + (C3 * val_sonda_ur_2);
			double RHReal = ((tempe - 25) * (T1 + (T2 * val_sonda_ur)))
					+ RHLineare;
			Double RH = RHReal;
			return RH;
		}
	}
	 
	public static Lettura createLettura(String raw) {
		
		//  -(e>"B28056"T_0CE_000_BC4
		// ->"LG-TEM"T_0CC_000_B21
		
		if (!checkCheckSum(raw))
			return null;
		
		Matcher matcher = UMIDITA_REGEX.matcher(raw);
		if(matcher.find()){
		
			double temperatura = Double.MIN_VALUE;
			double umidita = 0d;
			double latitudine = 0d;
			double longitudine = 0d;
			Lettura.TipoMisura tipoMisura; 
			
			String id_sonda = matcher.group(UMIDITA_NOME_SONDA_POS);
			String tipo_lettura = matcher.group(UMIDITA_TIPO_LETTURA);
			String temperatura_hex = matcher.group(UMIDITA_TEMPERATURA_POS);
			String umidita_hex = matcher.group(UMIDITA_UMIDITA_POS);
			String batteria = matcher.group(UMIDITA_BATTERIA_STATO_POS);
 			String checksum = matcher.group(UMIDITA_CHECKSUM_POS);
			 
			
			if("E".equals(tipo_lettura)){
				temperatura = Integer.parseInt(temperatura_hex,16)*0.04-39.63;
 				temperatura = Math.round(temperatura*10.0)/10.0;
				int umiditaUr = Integer.parseInt(umidita_hex,16);
				umidita = Umidita.parse(umiditaUr, temperatura);
 				umidita = Math.round(umidita*10.0)/10.0;
				tipoMisura = TipoMisura.TEMPERATURA_UMIDITA;
			}else if("T".equals(tipo_lettura)){
				tipoMisura = TipoMisura.TEMPERATURA;
				
				int temperatura10 = Integer.parseInt(temperatura_hex,16);
//				if (temperatura10 > 2048) {
					if (temperatura10 > 3500) {
					temperatura10 = (-1 * (4096 - temperatura10));
					}
				temperatura = temperatura10 / 10d;
			}else {
				//V
				
				temperatura = Integer.parseInt(temperatura_hex,16);
				tipoMisura = TipoMisura.TENSIONE_CORRENTE;
				 
			}
			
			Lettura lettura = new Lettura();
			lettura.setTipoMisura(tipoMisura);
			lettura.setStatoBatteria(batteria);
			lettura.setTemperaturaGrezza(temperatura);
			lettura.setUmiditaGrezza(umidita);
			lettura.setIdSonda(id_sonda);
			lettura.setData(new Date());
			lettura.setLatitude(latitudine);
			lettura.setLongitude(longitudine);
			return lettura;
			
			
		}else {
			// ho ricevuto una stringa corrotta
		}
		
		return null;
	}

	private static boolean checkCheckSum(String s) {
		if (s == null)
			return false;

		// ottengo e stampo il valore ASCII di un char (B=66)
		
		int FirstGreater = s.indexOf(">");
	 

		String Stringa = null;
		if (FirstGreater >= 0 && s.length() >= FirstGreater + 22) {
			Stringa = s.substring(FirstGreater, FirstGreater + 20); // STRiNGA  a meno del checksum
			
			// System.out.println(Stringa);

			// String mioChar= ">\"LG-TEM\"T_0FD_000_B25";

			int sum = 0;
			int size = Stringa.length();
			for (int i = 0; i < size; i++) {
				sum = sum + (int) Stringa.charAt(i);
			}

			String check_sum1 = Integer.toHexString(sum);
			String check_sum = check_sum1.substring(1, 3);

			// System.out.println(check_sum);
			// System.out.println(sum);

			String stringa_check_sum = s.substring(FirstGreater + 20,
					FirstGreater + 20 + 2);

			boolean result = stringa_check_sum.toUpperCase().equals(
					check_sum.toUpperCase());
			return result;
		} else
			return false;
	}
}
