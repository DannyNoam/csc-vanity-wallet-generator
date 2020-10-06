package com.dannynoam.walletservice.persistence.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.persistence.WalletDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CSCWalletDao implements WalletDao {

    private final JdbcTemplate jdbcTemplate;
    private final ResultSetExtractor<Wallet> walletResultSetExtractor;
    private final ResultSetExtractor<List<WalletAddress>> walletAddressesResultSetExtractor;

    @Autowired
    public CSCWalletDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<Wallet> walletResultSetExtractor,
                        ResultSetExtractor<List<WalletAddress>> walletAddressesResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.walletResultSetExtractor = walletResultSetExtractor;
        this.walletAddressesResultSetExtractor = walletAddressesResultSetExtractor;
    }

    @Override
    public void storeWallet(Wallet wallet) {
        jdbcTemplate.update(
                "INSERT INTO WALLET (ADDRESS, PUBLIC_KEY, SECRET, WORD) VALUES (?, ?, ?, ?)",
                wallet.getAddress().getAddress(), wallet.getPublicKey(), wallet.getSecret(),
                wallet.getWord().toLowerCase()
        );
    }

    @Override
    public Optional<Wallet> getWallet(String address) {
        return Optional.ofNullable(jdbcTemplate.query("SELECT ADDRESS, PUBLIC_KEY, SECRET FROM WALLET WHERE ADDRESS = ?",
                new Object[] { address },
                walletResultSetExtractor));
    }

    @Override
    public List<WalletAddress> getWalletAddresses(String word, int limit) {
        return jdbcTemplate.query("SELECT ADDRESS FROM WALLET WHERE WORD = ? LIMIT ?",
                new Object[] { word, limit },
                walletAddressesResultSetExtractor
        );
    }
}
