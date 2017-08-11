package com.chart;

import java.util.Comparator;

public class ChartVOUIComparator  implements Comparator<ChartVOUI>{

	@Override
	public int compare(ChartVOUI o1, ChartVOUI o2) {
		if (o1.getNavs().get(o1.getNavs().size() -1).getBpi() > o2.getNavs().get(o2.getNavs().size() -1).getBpi()){
			return -1;
		}else {
			return 1;
		}
		
		
	}

}
