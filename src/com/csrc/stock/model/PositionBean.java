package com.csrc.stock.model;

import java.io.Serializable;

public class PositionBean implements Comparable<PositionBean>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private StockBean stockBean;
	private int rank;
	private float percent;
	public PositionBean() {
		super();
		this.stockBean = new StockBean();
		this.rank = -1;
		this.percent = -1f;
	}
	public PositionBean(StockBean stockBean, int rank, float percent) {
		super();
		this.stockBean = stockBean;
		this.rank = rank;
		this.percent = percent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public StockBean getStockBean() {
		return stockBean;
	}
	public void setStockBean(StockBean stockBean) {
		this.stockBean = stockBean;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	@Override
	public String toString() {
		return "PositionBean [id=" + id + ", stockBean=" + stockBean
				+ ", rank=" + rank + ", percent=" + percent + "]";
	}
	
	public String format(String splitstr) {
		return rank + splitstr + stockBean.getStockNo() + splitstr 
				+ stockBean.getStockName() + splitstr 
				+ stockBean.getPriceBean().getPrice() + splitstr 
				+ stockBean.getPriceBean().getDiff() + splitstr 
				+ percent + splitstr 
				+ stockBean.getTotalHoldNum() + splitstr
				+ stockBean.getTotalCost();
	}
	@Override
	public int compareTo(PositionBean obj) {
		return rank - obj.getRank();
	}
}
