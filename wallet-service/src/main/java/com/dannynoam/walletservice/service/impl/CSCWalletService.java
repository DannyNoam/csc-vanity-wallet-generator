package com.dannynoam.walletservice.service.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.persistence.WalletDao;
import com.dannynoam.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSCWalletService implements WalletService {

    @Autowired
    private WalletDao cscWalletDao;

    @Override
    public void saveWallet(Wallet wallet) {
        cscWalletDao.storeWallet(wallet);
    }
}
