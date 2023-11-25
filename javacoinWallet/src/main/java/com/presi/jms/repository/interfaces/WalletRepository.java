package com.presi.jms.repository.interfaces;

import com.presi.jms.models.Wallet;

public interface WalletRepository {
	
	void createWallet(String dni);

	Wallet get(String dniSeller);

}
