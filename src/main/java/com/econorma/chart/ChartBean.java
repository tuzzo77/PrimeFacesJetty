package com.econorma.chart;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.econorma.data.Lettura;
import com.econorma.io.ReceiverLettorejSSC;
import com.econorma.util.Logger;
import com.econorma.util.SerialUtil;



@ManagedBean
public class ChartBean implements Serializable {

	private static final Logger logger = Logger.getLogger(ChartBean.class);
	private static final String TAG = ChartBean.class.getSimpleName();
	
	private LineChartModel dateModel;
	private LineChartModel cartesianChartModel;
	private ReceiverLettorejSSC receiver;
	private HashMap<String,Lettura> letture = new HashMap<String,Lettura>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@PostConstruct
	public void init() {
		createDateModel();
		createChartModel();
		String[] serialPorts = SerialUtil.getSerialPorts();
		receiver=new ReceiverLettorejSSC(serialPorts[0]);
	}

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public LineChartModel getCartesianChartModel() {
		return cartesianChartModel;
	}

	private void createDateModel() {
		dateModel = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("2014-01-01 08:11", 10.6);
		series1.set("2014-01-06 12:11", -5.6);
		series1.set("2014-01-12 14:11", 15);
		series1.set("2014-01-18 20:11", 12);
		series1.set("2014-01-24 08:11", 30);
		series1.set("2014-01-30 09:11", 0);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");

		series2.set("2014-01-01 05:11", 1);
		series2.set("2014-01-06 24:11", 11);
		series2.set("2014-01-12 23:11", -10.5);
		series2.set("2014-01-18 05:11", 25);
		series2.set("2014-01-24 10:11", 30);
		series2.set("2014-01-30 11:11", 62);

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);

		dateModel.setTitle("Temperature Monitor");
		dateModel.setZoom(true);
		dateModel.getAxis(AxisType.Y).setLabel("°C");
		DateAxis axis = new DateAxis("Date");
 		axis.setTickFormat("%d-%m-%y");

		dateModel.getAxes().put(AxisType.X, axis);
		dateModel.setExtender("ext");
	}


	private void createChartModel() {
		
		if (receiver!=null){
			letture = receiver.getLetture();
			logger.info(TAG, "HashMap Letture " + letture.values());
		}
		
		 

		cartesianChartModel = new LineChartModel();

		LineChartSeries seriesA = new LineChartSeries();
		LineChartSeries seriesB = new LineChartSeries();

		try {
			seriesA.set(sdf.parse("01/01/2014 08:11").getTime(), 10);
			seriesA.set(sdf.parse("31/01/2014 12:11").getTime(), -5.6);
			seriesA.set(sdf.parse("03/02/2014 14:11").getTime(), 15);
			seriesA.set(sdf.parse("06/04/2014 20:11").getTime(), 12);
			seriesA.set(sdf.parse("08/05/2014 08:11").getTime(), 30);
			seriesA.set(sdf.parse("11/10/2014 09:11").getTime(), 0);
		 
			seriesB.set(sdf.parse("05/01/2014 08:11").getTime(), -2);
			seriesB.set(sdf.parse("02/02/2014 12:11").getTime(), -30.6);
			seriesB.set(sdf.parse("15/01/2014 14:11").getTime(), 0);
			seriesB.set(sdf.parse("17/08/2014 20:11").getTime(), 16);
			seriesB.set(sdf.parse("10/10/2014 08:11").getTime(), 30);
			seriesB.set(sdf.parse("11/11/2014 09:11").getTime(), 80);
		} catch (Exception e) {

		}

		seriesA.setLabel("Logger 1");
		seriesB.setLabel("Logger 2");
		
		cartesianChartModel.addSeries(seriesA);
		cartesianChartModel.addSeries(seriesB);

		cartesianChartModel.setLegendPosition("ne");
		cartesianChartModel.setZoom(true);
		cartesianChartModel.setExtender("ext");

	}
	
	public void start(){
		
		Runnable r = new Runnable() {
			public void run() {
				receiver.run();
			}
		};

		new Thread(r).start();
		
	  
//		FacesContext.getCurrentInstance().renderResponse();  
 		 
		
		
	}
	
	
	public void stop(){
		receiver.die();
	}
	
}