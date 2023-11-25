package com.bank.models;

public class Wallet {
	
	private String dni;
	private double dolars;
	private double reteindeBalance;
	private int operations;
	
	
	public Wallet(String dni, double dolars, double reteindeBalance, int operations) {
		super();
		this.dni = dni;
		this.dolars = dolars;
		this.reteindeBalance = reteindeBalance;
		this.operations = operations;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public double getDolars() {
		return dolars;
	}
	public void setDolars(double dolars) {
		this.dolars = dolars;
	}
	public double getReteindeBalance() {
		return reteindeBalance;
	}
	public void setReteindeBalance(double reteindeBalance) {
		this.reteindeBalance = reteindeBalance;
	}
	public int getOperations() {
		return operations;
	}
	public void setOperations(int operations) {
		this.operations = operations;
	}
	
	public boolean hasDolarsEnough(int dolars) {
		return dolars<= this.getDolars();
	}

	public void payCommision(double commision) {
		setReteindeBalance(getReteindeBalance()  - commision);
		System.out.println("Comprador paga al banco: " + commision + " como comisión");
	}

	
	private void extractReteinedDolars(double dolars) {
		setReteindeBalance(getReteindeBalance() - dolars);
	}

	public void deposit(double dolars) {
		setDolars(getDolars() + dolars);
	}
	
	public String toString() {
	    return "Wallet [dni=" + dni + ", dolars=" + String.format("%.2f", dolars) +
	           ", reteindeBalance=" + String.format("%.2f", reteindeBalance) +
	           ", operations=" + operations + "]";
	}
	
	public void transferReteinedDolars(double dolars, Wallet wallet) {
		System.out.println("Previo a la trasnferencia");
		System.out.println(this);
		System.out.println(wallet);
		this.extractReteinedDolars(dolars);
		wallet.deposit(dolars);
		System.out.println("Cuenta :" +this.getDni() + " transfirio " + dolars + " dolares a cuenta " + wallet.getDni());
		System.out.println("posterior a la trasnferencia");
		System.out.println(this);
		System.out.println(wallet);
		
	}

	public boolean reteinBalance(double balance) {
		System.out.println("Previo a la retención de dolares");
		System.out.println(this);
		boolean reteined = balance <= this.getDolars();
		if(reteined) {
			setDolars(getDolars()+balance);
			setReteindeBalance(getReteindeBalance() + balance);
			System.out.println("Posterior a la retención");
			System.out.println(this);
		}
		return  reteined;
	}

	public void releaseReteinedBalance(double dolars) {
		System.out.println("Previo  al rollback de saldo retenido");
		System.out.println(this);
		extractReteinedDolars(dolars);
		deposit(dolars);
		System.out.println("Posteriror  al rollback de saldo retenido");
		System.out.println(this);
	}
	
	
	
	
	

}
