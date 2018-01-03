package com.nav;

import java.util.Comparator;

public class CurrentMarketPriceComparator implements Comparator<CurrentMarketPrice>{

	@Override
	public int compare(CurrentMarketPrice o1, CurrentMarketPrice o2) {
		if ( o1.getCloseChange() - o2.getCloseChange()> 0){
		//if ( o1.getHigh52Chg() - o2.getHigh52Chg()> 0){
			return 1;
		}else {
			return -1;
		}
	}

}
