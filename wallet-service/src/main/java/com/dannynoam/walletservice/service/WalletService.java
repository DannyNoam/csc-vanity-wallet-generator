package com.dannynoam.walletservice.service;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.domain.WalletsInfo;

import java.util.List;

public interface WalletService {
    void storeWallet(Wallet wallet);
    List<WalletAddress> getWalletAddressesContainingWord(String word, int limit);
    Wallet getWallet(String address);
    WalletsInfo getWalletsInfo();
}
