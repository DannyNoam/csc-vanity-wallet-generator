package com.dannynoam.walletservice.persistence;

import com.dannynoam.walletservice.domain.Wallet;

public interface WalletDao {
    void storeWallet(Wallet wallet);
}
