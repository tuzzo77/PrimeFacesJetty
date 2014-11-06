package com.econorma.chart;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
@RequestScoped
public class DynamicChart implements Serializable {
	
	private final static int MAX_VALUE = 20;
	private final static int NUMBER_OF_POINTS = 20;
	private final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

     private LineChartModel model;
	 
	 

    public DynamicChart() {
        createLinearModel();
    }

//    private void createLinearModel() {
//        model = new LineChartModel();
//        model.addSeries(getStockChartData("Temperature"));
//        model.setTitle("Temperature");
//    }
//
//    private ChartSeries getStockChartData(String label) {
//        ChartSeries data = new ChartSeries();
//        data.setLabel(label);
//        for (int i = 1; i <= 20; i++) {
//            data.getData().put(i, (int) (Math.random() * 1000));
//        }
//        return data;
//    }
    
    
    
    private void createLinearModel(){
    	model = new LineChartModel();
        ChartSeries series1 = new ChartSeries();
    	series1.set(1, 10.5);
    	series1.set(2, 12.6);
    	series1.set(3, 50.6);
    	series1.set(1, -40.5);
    	series1.set(6, 30.6);
    	series1.set(10, 1);
    	ChartSeries series2 = new ChartSeries();
    	series2.set(0, 6.4);
    	series2.set(10, 9.1);
    	series2.set(8, 10);
    	series2.set(25, 90);
    	series2.set(50, -15.5);
    	series2.set(90, 30);
    	series1.setLabel("Shit");
    	series2.setLabel("Fuck");
    	model.addSeries(series1);
    	model.addSeries(series2);
    	model.setZoom(true);
     	
    }
    
     
    
   
//    private void createLinearModel() {
//    	model = new CartesianChartModel();
//        Calendar day = Calendar.getInstance();
//        day.set(Calendar.HOUR_OF_DAY, 0);
//        day.set(Calendar.MINUTE, 0);
//        day.set(Calendar.SECOND, 0);
//        day.set(Calendar.MILLISECOND, 0);
//        LineChartSeries series = new LineChartSeries();
//        series.setLabel("My series");
//        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
//            series.set(dateFormat.format(day.getTime()), getRandomValue());
//            day.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        model.addSeries(series);
//    }
    
    private int getRandomValue() {
    	Random rand = new Random();
       return rand.nextInt(MAX_VALUE);
    }
    

    public CartesianChartModel getLinearModel() {
        return model;
    }

}