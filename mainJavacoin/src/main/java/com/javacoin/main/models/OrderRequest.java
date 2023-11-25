package com.javacoin.main.models;

import java.io.Serializable;


public class OrderRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
    
	private String buyerDni;
	
	private String sellerDni;

	// javacoin to purchase
	private double javacoin;

	// quote proposal
	private double dolars;

	private double commision;

	public OrderRequest() {
		super();

	}

	public OrderRequest(int id, String buyerDni, double javacoin, double dolars) {
		super();
		this.id = id;
		this.buyerDni = buyerDni;
		this.javacoin = javacoin;
		this.dolars = dolars;
	}

	public OrderRequest(String buyerDni, double javacoin, double dolars) {
		super();
		this.buyerDni = buyerDni;
		this.javacoin = javacoin;
		this.dolars = dolars;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuyerDni() {
		return buyerDni;
	}

	public void setBuyerDni(String buyerDni) {
		this.buyerDni = buyerDni;
	}

	public String getSellerDni() {
		return sellerDni;
	}

	public void setSellerDni(String sellerDni) {
		this.sellerDni = sellerDni;
	}

	public double getJavacoin() {
		return javacoin;
	}

	public void setJavacoin(double javacoin) {
		this.javacoin = javacoin;
	}

	public double getDolars() {
		return dolars;
	}

	public void setDolars(double dolars) {
		this.dolars = dolars;
	}

	public double getCommision() {
		return commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		 return "OrderRequest [id=" + id + ", buyerDni=" + buyerDni + ", sellerDni=" + sellerDni + ", javacoin="
				+ javacoin + ", dolars=" + dolars + ", commision=" + commision + "]";
	}


	
	

}
