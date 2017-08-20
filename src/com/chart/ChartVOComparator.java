package com.chart;

import java.util.Comparator;

public class ChartVOComparator implements Comparator<ChartVO>{
	@Override
	public int compare(ChartVO o1, ChartVO o2) {
		try{
			if (o1.getNavs().get(o1.getNavs().size() -1).getScaled() > o2.getNavs().get(o2.getNavs().size() -1).getScaled()){
				return -1;
			}else {
				return 1;
			}
		}catch(Exception e){
			if (o1.getNavs() == null){
				return 1;
			}else {
				return -1;
			}
			
		}
		
		
		
	}
}
