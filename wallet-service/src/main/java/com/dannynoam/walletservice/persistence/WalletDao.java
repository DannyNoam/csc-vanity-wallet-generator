package com.dannynoam.walletservice.persistence;

import com.dannynoam.walletservice.domain.Wallet;

import java.util.Optional;

public interface WalletDao {
    void storeWallet(Wallet wallet);
    Optional<Wallet> getWallet(String address);
}
