package com.bank.repository.interfaces;

import com.bank.models.Wallet;

public interface WalletRepository {

	boolean exist(String dni);
	Wallet get(String dni);
	void save(Wallet wallet);
	Wallet createWallet(String sellerDni);
	

}
