package com.PriceChart;

import java.util.List;

public class PriceAndVolume {

	private List<PriceVO> priceVOList;
	private List<PriceVO> volumeVoList;
	public List<PriceVO> getPriceVOList() {
		return priceVOList;
	}
	public void setPriceVOList(List<PriceVO> priceVOList) {
		this.priceVOList = priceVOList;
	}
	public List<PriceVO> getVolumeVoList() {
		return volumeVoList;
	}
	public void setVolumeVoList(List<PriceVO> volumeVoList) {
		this.volumeVoList = volumeVoList;
	}
}
