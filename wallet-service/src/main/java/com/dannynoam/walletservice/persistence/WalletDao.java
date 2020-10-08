package com.dannynoam.walletservice.persistence;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;

import java.util.List;
import java.util.Optional;

public interface WalletDao {
    void storeWallet(Wallet wallet);
    Optional<Wallet> getWallet(String address);
    List<WalletAddress> getWalletAddresses(String word, int limit);
    Integer getWalletsCount();
    Integer getMaxWordLength();
}
