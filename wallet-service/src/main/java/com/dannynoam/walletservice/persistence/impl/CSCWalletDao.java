package com.dannynoam.walletservice.persistence.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.persistence.WalletDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CSCWalletDao implements WalletDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void storeWallet(Wallet wallet) {
        jdbcTemplate.update(
                "INSERT INTO WALLET VALUES (?, ?, ?)", wallet.getAddress(), wallet.getPublicKey(), wallet.getSecret());
    }
}
