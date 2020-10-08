package com.dannynoam.walletservice.controller;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.dannynoam.walletservice.domain.WalletsInfo;
import com.dannynoam.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class WalletController {

    private static final Logger logger = LogManager.getLogger(WalletController.class);

    private final WalletService cscWalletService;

    @PostMapping(value = "/wallet")
    public ResponseEntity<Wallet> storeWallet(@RequestBody Wallet wallet) throws URISyntaxException {
        logger.info(String.format("Request to store wallet address %s received, word=%s", wallet.getAddress(), wallet.getWord()));

        cscWalletService.storeWallet(wallet);

        return ResponseEntity.created(new URI("/wallet/" + wallet.getAddress().getAddress())).body(wallet);
    }

    @GetMapping(value = "/wallets", params = { "word", "limit" })
    public ResponseEntity<List<WalletAddress>> getWalletsContainingWord(@RequestParam String word,
                                                                        @RequestParam @Max(50) @Min(1) int limit) {
        logger.info(String.format("Request to get wallets for word=%s, limit=%d", word, limit));

        return ResponseEntity.ok(cscWalletService.getWalletAddressesContainingWord(word, limit));
    }

    @GetMapping(value = "/wallets")
    public ResponseEntity<WalletsInfo> getWallets() {
        logger.info(String.format("Request to get wallets"));

        return ResponseEntity.ok(cscWalletService.getWalletsInfo());
    }

    @GetMapping(value = "/wallet/{address}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String address) {
        logger.info(String.format("Request to get wallet address %s received.", address));

        return ResponseEntity.ok(cscWalletService.getWallet(address));
    }

    @GetMapping(value = "/")
    public ResponseEntity healthcheck() {
        return ResponseEntity.ok().build();
    }

}
