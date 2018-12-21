package com.dannynoam.walletservice.service.impl;

import com.dannynoam.walletservice.controller.WalletController;
import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.persistence.WalletDao;
import com.dannynoam.walletservice.service.WalletService;
import com.dannynoam.walletservice.service.exception.WalletNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CSCWalletService implements WalletService {

    private static final Logger logger = LogManager.getLogger(CSCWalletService.class);

    @Autowired
    private WalletDao cscWalletDao;

    @Override
    public void saveWallet(Wallet wallet) {
        cscWalletDao.storeWallet(wallet);
    }

    @Override
    public Wallet getWallet(String address) {
        Optional<Wallet> wallet = cscWalletDao.getWallet(address);

        if(!wallet.isPresent()) {
            logger.error(String.format("Wallet not found! Address=%s", address));
            throw new WalletNotFoundException(String.format("Wallet for address %s doesn't exist", address));
        }

        return wallet.get();
    }
}
