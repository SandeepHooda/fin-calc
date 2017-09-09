package com.vo;

import java.util.Comparator;

public class StockSort implements Comparator<StockVO> {
	@Override
	public int compare(StockVO o1, StockVO o2) {
		if (o1.getCompanyXirr() > o2.getCompanyXirr()){
			return -1;
		}else {
			if (o1.getCompanyXirr() == o2.getCompanyXirr()){
				if (o1.getCompanyAbsoluteGainPercent() > o2.getCompanyAbsoluteGainPercent()){
					return -1;
				}else {
					return 1;
				}
				
			}else {
				return 1;
			}
			
		}
	}

}
