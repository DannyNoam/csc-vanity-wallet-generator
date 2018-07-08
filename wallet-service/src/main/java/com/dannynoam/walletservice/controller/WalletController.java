package com.dannynoam.walletservice.controller;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    private WalletService cscWalletService;

    @PostMapping(value = "/wallets")
    public Wallet storeWallet(@RequestBody Wallet wallet) {
        cscWalletService.saveWallet(wallet);

        return wallet;
    }

}
