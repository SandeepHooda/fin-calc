package com.vo.chart;

import java.util.ArrayList;
import java.util.List;

public class chartData {
	private List<String> labels = new ArrayList<String>();
    private List<ChartDataSets> datasets = new ArrayList<ChartDataSets>();
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<ChartDataSets> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<ChartDataSets> datasets) {
		this.datasets = datasets;
	}
}
