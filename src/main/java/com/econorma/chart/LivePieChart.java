package com.econorma.chart;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.LineChartModel;
 
 

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;
 
@ManagedBean
public class LivePieChart implements Serializable {
 
    private PieChartModel livePieModel;
    
    @PostConstruct
    public void init() {
        createModel();
    }
 
    private void createModel() {
    	livePieModel = new PieChartModel();
	}

	public PieChartModel getLivePieModel() {
        int random1 = (int)(Math.random() * 1000);
        int random2 = (int)(Math.random() * 1000);
 
        livePieModel.getData().put("Candidate 1", random1);
        livePieModel.getData().put("Candidate 2", random2);
         
        livePieModel.setTitle("Votes");
        livePieModel.setLegendPosition("ne");
         
        return livePieModel;
    }
     
}