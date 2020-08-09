package com.dannynoam.walletservice.controller;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class WalletController {

    private static final Logger logger = LogManager.getLogger(WalletController.class);

    @Autowired
    private WalletService cscWalletService;

    @PostMapping(value = "/wallet")
    public ResponseEntity<Wallet> storeWallet(@RequestBody Wallet wallet) throws URISyntaxException {
        logger.info(String.format("Request to store wallet address %s received.", wallet.getAddress()));

        cscWalletService.saveWallet(wallet);

        return ResponseEntity.created(new URI("/wallet/" + wallet.getAddress())).body(wallet);
    }

    @GetMapping(value = "/wallet/{address}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String address) {
        logger.info(String.format("Request to get wallet address %s received.", address));

        return ResponseEntity.ok(cscWalletService.getWallet(address));
    }

}
