package com.dannynoam.walletservice.service.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.domain.WalletsInfo;
import com.dannynoam.walletservice.persistence.WalletDao;
import com.dannynoam.walletservice.service.WalletService;
import com.dannynoam.walletservice.service.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CSCWalletService implements WalletService {

    private static final Logger logger = LogManager.getLogger(CSCWalletService.class);

    private WalletDao cscWalletDao;

    @Override
    public void storeWallet(Wallet wallet) {
        cscWalletDao.storeWallet(wallet);
    }

    @Override
    public List<WalletAddress> getWalletAddressesContainingWord(String word, int limit) {
        return cscWalletDao.getWalletAddresses(word, limit);
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

    @Override
    public WalletsInfo getWalletsInfo() {
        return WalletsInfo.builder()
                .count(cscWalletDao.getWalletsCount())
                .maxWordLength(cscWalletDao.getMaxWordLength())
                .build();
    }
}
