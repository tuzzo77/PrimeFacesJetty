package com.econorma.data;

import java.util.Date;
 

public class Lettura  {

	public static final double INVALID_MEASURE = Double.MIN_VALUE;
	private String idSonda;
	
	private String statoBatteria;

	private Date data;

	private TipoMisura tipoMisura;

	private double temperaturaGrezza = INVALID_MEASURE;
	
	private double umiditaGrezza = INVALID_MEASURE;
	
	private double latitude = INVALID_MEASURE;
	
	private double longitude= INVALID_MEASURE;
 
	
	private transient boolean _discovery;

	private double effetto_letale;
	private double f0;
	
	public double getTemperaturaGrezza() {
		return temperaturaGrezza;
	}

	public void setTemperaturaGrezza(double valoreOriginale) {
		this.temperaturaGrezza = valoreOriginale;
	}
	
	public double getUmiditaGrezza() {
		return umiditaGrezza;
	}

	public void setUmiditaGrezza(double valoreOriginale) {
		this.umiditaGrezza = valoreOriginale;
	}

	public String getIdSonda() {
		return idSonda;
	}

	public void setIdSonda(String idSonda) {
		this.idSonda = idSonda;
	}

	public double getValore() {
	 	return temperaturaGrezza;
	}
	
	public double getUmidita(){
	 	return umiditaGrezza;
	 
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoMisura getTipoMisura() {
		return tipoMisura;
	}
	
	public String getStatoBatteria(){
		return statoBatteria;
	}

	public void setTipoMisura(TipoMisura tipoMisura) {
		this.tipoMisura = tipoMisura;
	}
	
	public void setStatoBatteria(String statoBatteria){
		this.statoBatteria=statoBatteria;
	}

	public static enum TipoMisura {
		TEMPERATURA, TEMPERATURA_UMIDITA, TENSIONE_CORRENTE, UMIDITA, F0, EL
	}

	@Override
	public String toString() {
		return "id_sonda: " + idSonda + " temperatura:" + getValore() + " data: "+ data;
	}
 
 
	public double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(double value){
		latitude = value;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(double value){
		longitude = value;
	}
	
	
	public double getValoreMisura(TipoMisura tipo){
		switch (tipo) {
		case TEMPERATURA:
			return getValore();
		case UMIDITA:
			return getUmidita();
		case TENSIONE_CORRENTE:
			return getValore();
		case F0:
			return getF0();
		case EL:
			return getEffettoLetale();
		default:
			throw new RuntimeException("Richiesta non supportata");
		}
	}
	
	public boolean isValoreMisuraValid(TipoMisura tipo){
		switch (tipo) {
		case TEMPERATURA:
			if(TipoMisura.TEMPERATURA.equals(getTipoMisura()) || TipoMisura.TEMPERATURA_UMIDITA.equals(getTipoMisura()))  {
				return INVALID_MEASURE != getValoreMisura(tipo);
			}else{
				return false;
			}
		case UMIDITA:
			return getValoreMisura(tipo)>0;
		case TENSIONE_CORRENTE: 
			return TipoMisura.TENSIONE_CORRENTE.equals(getTipoMisura());
		case F0:
			return getValoreMisura(tipo)>0;
		case EL:
			return getValoreMisura(tipo)>0;
		default:
			throw new RuntimeException("Richiesta non supportata");
		}
	}
 
 
	
	public void setDiscovery(boolean discovery){
		_discovery = discovery;
	}
	
	public boolean isDiscovery(){
		return _discovery;
	}
 
	
	public Double getEffettoLetale() {
		return effetto_letale;
	}
	
	public void setEffettoLetale(double effetto_letale) {
		this.effetto_letale = effetto_letale;
	}
	
	public Double getF0() {
		return f0;
	}
	
	public void setF0(double f0) {
		this.f0 = f0;
	}
 
	
}
