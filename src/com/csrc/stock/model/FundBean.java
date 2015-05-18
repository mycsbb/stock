package com.csrc.stock.model;

import java.util.ArrayList;
import java.util.List;

public class FundBean {
	private Integer id; 
	private String fundNo;
	private String fundName;
	private List<PositionBean> positionBeans;
	public FundBean() {
		super();
		this.fundNo = "";
		this.fundName = "";
		this.positionBeans = new ArrayList<PositionBean>();
	}
	
	public FundBean(String fundNo, String fundName) {
		super();
		this.fundNo = fundNo;
		this.fundName = fundName;
		this.positionBeans = new ArrayList<PositionBean>();
	}

	public FundBean(Integer id, String fundNo, String fundName,
			List<PositionBean> positionBeans) {
		super();
		this.id = id;
		this.fundNo = fundNo;
		this.fundName = fundName;
		this.positionBeans = positionBeans;
	}

	public List<PositionBean> getPositionBeans() {
		return positionBeans;
	}

	public void setPositionBeans(List<PositionBean> positionBeans) {
		this.positionBeans = positionBeans;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFundNo() {
		return fundNo;
	}
	public void setFundNo(String fundNo) {
		this.fundNo = fundNo;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	@Override
	public String toString() {
		return "FundBean [id=" + id + ", fundNo=" + fundNo + ", fundName="
				+ fundName + "]";
	}

	public boolean isEmpty() {
		if (this.fundNo == null || this.fundNo.trim().equals("")
				|| this.positionBeans == null 
				|| this.positionBeans.size() == 0) return true;
		return false;
	}
	
}
