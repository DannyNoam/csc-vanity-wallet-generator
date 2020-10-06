package com.dannynoam.walletservice.service.impl;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.persistence.WalletDao;
import com.dannynoam.walletservice.service.exception.WalletNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CSCWalletServiceUTest {

    private static final String ADDRESS = "cERKUMRxtgW2asds7Z8Sd1KWazgtENbWEH";
    private static final String WORD = "legend";
    private static final int LIMIT = 1;

    private final WalletAddress address = new WalletAddress(ADDRESS);
    private final Wallet wallet = Wallet.builder()
            .address(address)
            .build();

    @Mock
    private WalletDao cscWalletDao;

    @InjectMocks
    private CSCWalletService testObj;

    @Test
    public void storeWallet_storesWalletUsingUnderlyingDao() {
        testObj.storeWallet(wallet);

        verify(cscWalletDao).storeWallet(wallet);
    }

    @Test
    public void getWalletAddresses_returnsWalletAddressesFromUnderlyingDao() {
        List<WalletAddress> walletAddresses = Collections.singletonList(address);
        when(cscWalletDao.getWalletAddresses(WORD, LIMIT)).thenReturn(walletAddresses);

        List<WalletAddress> actual = testObj.getWalletAddresses(WORD, LIMIT);

        assertEquals(walletAddresses, actual);
    }

    @Test
    public void getWallet_walletDoesntExist_walletNotFoundExceptionThrown() {
        when(cscWalletDao.getWallet(ADDRESS)).thenReturn(Optional.empty());

        assertThrows((WalletNotFoundException.class), () -> {
            testObj.getWallet(ADDRESS);
        });
    }

    @Test
    public void getWallet_walletExists_returnsWalletFromUnderlyingDao() {
        when(cscWalletDao.getWallet(ADDRESS)).thenReturn(Optional.of(wallet));

        Wallet actual = testObj.getWallet(ADDRESS);

        assertEquals(wallet, actual);
    }
}