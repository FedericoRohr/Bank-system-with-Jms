package com.presi.jms.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.presi.jms.models.Wallet;
import com.presi.jms.repository.interfaces.WalletRepository;

@Component // use component becouse i´m mocking BD
public class WalletRepositoryImp implements WalletRepository {

	private Map<String, Wallet> wallets; // mocking a repository TODO: Make a DB

	public WalletRepositoryImp() {
		super();
		wallets = new HashMap<>();
		this.seederWallets();
	}

	private void seederWallets() {
		wallets.put("98765432", new Wallet("98765432", 2));
		wallets.put("12345678", new Wallet("12345678", 15));
		wallets.put("41916580", new Wallet("41916580", 40));
	}

	@Override
	public void createWallet(String dni) {
		System.out.println("Chequeo existencia de billetera");
		Wallet wallet = wallets.get(dni);
		if(wallet == null) {
			wallets.put(dni, new Wallet(dni, 0));
			System.out.println("Termina creacion de la billetera: " + dni);
		}else {
			System.out.println("Billetera encontrada:" + wallet);
		}
	}

	@Override
	public Wallet get(String dni) {
		return wallets.get(dni);
	}
}
