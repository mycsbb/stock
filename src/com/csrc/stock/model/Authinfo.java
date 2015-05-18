package com.csrc.stock.model;

public class Authinfo {
	private String pgv_pvi;
	private String emstat_bc_emcount;
	private String emstat_ss_emcount;
	private String pu;
	private String pgv_info;
	private String kforders;
	private String pi;
	private String sessionid;
	public Authinfo() {
		super();
		this.pgv_info = "";
		this.pgv_pvi = "";
		this.emstat_bc_emcount = "";
		this.emstat_ss_emcount = "";
		this.pu = "";
		this.kforders = "";
		this.pi = "";
		this.sessionid = "";
	}
	public Authinfo(String pgv_info, String ssi, String pgv_pvi,
			String emstat_bc_emcount, String emstat_ss_emcount, String pu,
			String kforders, String pi, String sessionid) {
		super();
		this.pgv_info = pgv_info;
		this.pgv_pvi = pgv_pvi;
		this.emstat_bc_emcount = emstat_bc_emcount;
		this.emstat_ss_emcount = emstat_ss_emcount;
		this.pu = pu;
		this.kforders = kforders;
		this.pi = pi;
		this.sessionid = sessionid;
	}
	
	public String getKforders() {
		return kforders;
	}
	public void setKforders(String kforders) {
		this.kforders = kforders;
	}
	public String getPgv_info() {
		return pgv_info;
	}
	public void setPgv_info(String pgv_info) {
		this.pgv_info = pgv_info;
	}
	
	public String getPgv_pvi() {
		return pgv_pvi;
	}
	public void setPgv_pvi(String pgv_pvi) {
		this.pgv_pvi = pgv_pvi;
	}
	public String getEmstat_bc_emcount() {
		return emstat_bc_emcount;
	}
	public void setEmstat_bc_emcount(String emstat_bc_emcount) {
		this.emstat_bc_emcount = emstat_bc_emcount;
	}
	public String getEmstat_ss_emcount() {
		return emstat_ss_emcount;
	}
	public void setEmstat_ss_emcount(String emstat_ss_emcount) {
		this.emstat_ss_emcount = emstat_ss_emcount;
	}
	public String getPu() {
		return pu;
	}
	public void setPu(String pu) {
		this.pu = pu;
	}
	public String getPi() {
		return pi;
	}
	public void setPi(String pi) {
		this.pi = pi;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
}
