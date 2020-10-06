package com.dannynoam.walletservice.service;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;

import java.util.List;

public interface WalletService {
    void storeWallet(Wallet wallet);
    Wallet getWallet(String address);
    List<WalletAddress> getWalletAddresses(String word, int limit);
}
