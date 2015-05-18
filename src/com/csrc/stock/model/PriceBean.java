package com.csrc.stock.model;

import java.io.Serializable;

public class PriceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; 
	private float price;
	private float diff;

	public PriceBean() {
		super();
		this.price = -1f;
	}

	public PriceBean(float price, float diff) {
		super();
		this.price = price;
		this.diff = diff;
	}

	public PriceBean(Integer id,float price, float diff) {
		super();
		this.id = id;
		this.price = price;
		this.diff = diff;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiff() {
		return diff;
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}

	@Override
	public String toString() {
		return "PriceBean [id=" + id + ", price=" + price + ", diff=" + diff
				+ "]";
	}
}
