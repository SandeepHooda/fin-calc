package com.vo;

import java.util.Comparator;

public class CurrentPriceVOSort implements Comparator<CurrentPriceVO>{

	@Override
	public int compare(CurrentPriceVO o1, CurrentPriceVO o2) {
		if (o1.getTimeCreated() < o2.getTimeCreated()){
			return -1;
		}else {
			return 1;
		}
		
	}

}
