package com.bank.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bank.models.Wallet;
import com.bank.repository.interfaces.WalletRepository;

@Component
public class WalletRepositoryImp implements WalletRepository {

	private Map<String, Wallet> wallets; // mocking a repository TODO: Make a DB

	public WalletRepositoryImp() {
		super();
		wallets = new HashMap<>();
		this.seederWallets();
	}

	@Override
	public boolean exist(String dni) {
		return wallets.get(dni) != null;
	}

	@Override
	public Wallet get(String dni) {
		return wallets.get(dni);
	}

	@Override
	public void save(Wallet wallet) {
		wallets.put(wallet.getDni(), wallet);
	}

	private void seederWallets() { // mocking wallets
		wallets.put("41916580", new Wallet("41916580", 10, 0, 5));
		wallets.put("12345678", new Wallet("12345678", 30, 0, 2));
		wallets.put("87654321", new Wallet("87654321", 69, 0, 5));
	}

	

	@Override
	public Wallet createWallet(String sellerDni) {
		Wallet wallet = new Wallet(sellerDni, 0, 0, 0);
		wallets.put(sellerDni, wallet);
		System.out.println("Wallet creada: " + sellerDni);
		return wallet;
	}
}
