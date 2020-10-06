package com.dannynoam.walletservice.controller;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class WalletControllerUTest {

    private static final String ADDRESS = "cERKUMRxtgW2asds7Z8Sd1KWazgtENbWEH";
    private static final String WORD = "legend";
    private static final int LIMIT = 1;

    private final WalletAddress address = new WalletAddress(ADDRESS);
    private final Wallet wallet = Wallet.builder()
            .address(address)
            .build();

    @Mock
    private WalletService cscWalletService;

    @InjectMocks
    private WalletController testObj;

    @Test
    public void storeWallet_storesWalletUsingUnderlyingServiceAndReturnsLinkToCreatedResource() throws URISyntaxException {
        String expectedResourceLocation = "/wallet/".concat(ADDRESS);

        ResponseEntity<Wallet> actual = testObj.storeWallet(wallet);

        verify(cscWalletService).storeWallet(wallet);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(wallet, actual.getBody());
        assertEquals(expectedResourceLocation, actual.getHeaders().getLocation().toString());
    }

    @Test
    public void getWallets_callsUnderlyingServiceAndReturnsListOfWalletResources() {
        List<WalletAddress> walletAddresses = Collections.singletonList(address);
        when(cscWalletService.getWalletAddresses(WORD, LIMIT)).thenReturn(walletAddresses);

        ResponseEntity<List<WalletAddress>> actual = testObj.getWallets(WORD, LIMIT);

        verify(cscWalletService).getWalletAddresses(WORD, LIMIT);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(walletAddresses, actual.getBody());
    }

    @Test
    public void getWallet_callsUnderlyingServiceAndReturnsWalletResource() {
        when(cscWalletService.getWallet(ADDRESS)).thenReturn(wallet);

        ResponseEntity<Wallet> actual = testObj.getWallet(ADDRESS);

        verify(cscWalletService).getWallet(ADDRESS);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(wallet, actual.getBody());
    }

    @Test
    public void healthcheck_returns200OK() {
        assertEquals(HttpStatus.OK, testObj.healthcheck().getStatusCode());
    }
}
