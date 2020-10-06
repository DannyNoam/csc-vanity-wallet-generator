package com.dannynoam.walletservice.persistence.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CSCWalletDaoUTest {

    private static final String ADDRESS = "cERKUMRxtgW2asds7Z8Sd1KWazgtENbWEH";
    private static final String PUBLIC_KEY = "038F5DDA05EBB39A4095DD6E24447024264E850A93F42065C7B0FF0E37CC181D90";
    private static final String SECRET = "ssEcnRSwQ1xXdyvYM8qdrwNMZQySm";
    private static final String WORD = "legend";
    private static final int LIMIT = 1;

    private final WalletAddress address = new WalletAddress(ADDRESS);
    private final Wallet wallet = Wallet.builder()
            .address(address)
            .publicKey(PUBLIC_KEY)
            .secret(SECRET)
            .word(WORD)
            .build();

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ResultSetExtractor<Wallet> walletResultSetExtractor;

    @Mock
    private ResultSetExtractor<List<WalletAddress>> walletAddressesResultSetExtractor;

    private CSCWalletDao testObj;

    @Before
    public void setup() {
        testObj = new CSCWalletDao(jdbcTemplate, walletResultSetExtractor, walletAddressesResultSetExtractor);
    }

    @Test
    public void storeWallet_executesCorrectQueryToStoreWallet() {
        testObj.storeWallet(wallet);

        verify(jdbcTemplate).update("INSERT INTO WALLET (ADDRESS, PUBLIC_KEY, SECRET, WORD) VALUES (?, ?, ?, ?)",
                ADDRESS, PUBLIC_KEY, SECRET, WORD);
        verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    public void getWallet_returnsOptionalContainingWallet() {
        when(jdbcTemplate.query("SELECT ADDRESS, PUBLIC_KEY, SECRET FROM WALLET WHERE ADDRESS = ?",
                new Object[] { ADDRESS },
                walletResultSetExtractor)).thenReturn(wallet);

        Optional<Wallet> actual = testObj.getWallet(ADDRESS);

        verify(jdbcTemplate).query("SELECT ADDRESS, PUBLIC_KEY, SECRET FROM WALLET WHERE ADDRESS = ?",
                new Object[] { ADDRESS },
                walletResultSetExtractor);
        verifyNoMoreInteractions(jdbcTemplate);
        assertEquals(wallet, actual.get());
    }

    @Test
    public void getWallets_returnsListContainingWallets() {
        List<WalletAddress> walletAddresses = Collections.singletonList(address);
        when(jdbcTemplate.query("SELECT ADDRESS FROM WALLET WHERE WORD = ? LIMIT ?", new Object[] { WORD, LIMIT },
                walletAddressesResultSetExtractor)).thenReturn(walletAddresses);

        List<WalletAddress> actual = testObj.getWalletAddresses(WORD, LIMIT);

        verify(jdbcTemplate).query("SELECT ADDRESS FROM WALLET WHERE WORD = ? LIMIT ?", new Object[] { WORD, LIMIT },
                walletAddressesResultSetExtractor);
        verifyNoMoreInteractions(jdbcTemplate);
        assertEquals(walletAddresses, actual);
    }
}