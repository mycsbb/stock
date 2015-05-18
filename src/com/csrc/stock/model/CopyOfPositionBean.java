package com.csrc.stock.model;

public class CopyOfPositionBean {
	private Integer id; 
	private String stockNo;
	private String stockName;
	private PriceBean priceBean;
	private long stockNum;
	private long cost;
	
	
	private int rank;
	private float percent;
	
	
	public CopyOfPositionBean() {
		super();
		this.stockNo = "";
		this.stockName = "";
		this.priceBean = new PriceBean();
		this.stockNum = -1l;
		this.cost = -1l;
		this.rank = -1;
		this.percent = -1f;
	}

	public CopyOfPositionBean(Integer id) {
		super();
		this.id = id;
	}

	public CopyOfPositionBean(String stockNo, String stockName, int rank,
			PriceBean priceBean, float percent, long stockNum, long cost) {
		super();
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.rank = rank;
		this.priceBean = priceBean;
		this.percent = percent;
		this.stockNum = stockNum;
		this.cost = cost;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public long getStockNum() {
		return stockNum;
	}

	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "PositionBean [id=" + id + ", stockNo=" + stockNo
				+ ", stockName=" + stockName + ", rank=" + rank
				+ ", priceBean=" + priceBean + ", percent=" + percent
				+ ", stockNum=" + stockNum + ", cost=" + cost + "]";
	}

}
