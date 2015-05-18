package com.csrc.stock.model;

import java.io.Serializable;

public class StockBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; 
	private String stockNo;
	private String stockName;
	private PriceBean priceBean;
	private String holder;
    private long totalHoldNum;
    private long totalCost;
    
	public StockBean() {
		super();
		this.stockNo = "";
		this.stockName = "";
		this.priceBean = new PriceBean();
		this.holder = "";
		this.totalHoldNum = -1l;
		this.totalCost = -1l;
	}

	public StockBean(String stockNo, String stockName, PriceBean priceBean,
			String holder, long totalHoldNum, long totalCost) {
		super();
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.priceBean = priceBean;
		this.holder = holder;
		this.totalHoldNum = totalHoldNum;
		this.totalCost = totalCost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public PriceBean getPriceBean() {
		return priceBean;
	}

	public void setPriceBean(PriceBean priceBean) {
		this.priceBean = priceBean;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public long getTotalHoldNum() {
		return totalHoldNum;
	}

	public void setTotalHoldNum(long totalHoldNum) {
		this.totalHoldNum = totalHoldNum;
	}

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "StockBean [id=" + id + ", stockNo=" + stockNo + ", stockName="
				+ stockName + ", priceBean=" + priceBean + ", holder=" + holder
				+ ", totalHoldNum=" + totalHoldNum + ", totalCost=" + totalCost
				+ "]";
	}

	public boolean isEmpty() {
		if (stockNo == null || stockNo.trim().equals("") 
				|| stockName == null || stockName.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public String format(String splitstr) {
		return stockNo + splitstr 
				+ stockName + splitstr 
				+ holder + splitstr 
				+ priceBean.getPrice() + splitstr 
				+ priceBean.getDiff() + splitstr 
				+ totalHoldNum + splitstr
				+ totalCost;
	}
}
