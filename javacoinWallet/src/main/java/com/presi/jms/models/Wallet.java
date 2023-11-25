package com.presi.jms.models;

public class Wallet {
	
	private String dni;
	private double javacoin;
	
	
	public Wallet(String dni, double javacoin) {
		super();
		this.dni = dni;
		this.javacoin = javacoin;
	}
	
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public double getJavacoin() {
		return javacoin;
	}
	public void setJavacoin(double javacoin) {
		this.javacoin = javacoin;
	}
	
	private void extract(double javacoin) {
		setJavacoin(getJavacoin() - javacoin);
	}

	public void deposit(double javacoin) {
		setJavacoin(getJavacoin() + javacoin);
	}
	
	

	@Override
	public String toString() {
		return "Wallet [dni=" + dni + ", javacoin=" + javacoin + "]";
	}


	public void transferJavacoin(double javacoin, Wallet wallet) {
		System.out.println("Previo a la trasnferencia");
		System.out.println(this);
		System.out.println(wallet);
		this.extract(javacoin);
		wallet.deposit(javacoin);
		System.out.println("Cuenta :" +this.getDni() + " transfirio " + javacoin + " javacoins a cuenta " + wallet.getDni());
		System.out.println("posterior a la trasnferencia");
		System.out.println(this);
		System.out.println(wallet);
	}
	
	
	

}
