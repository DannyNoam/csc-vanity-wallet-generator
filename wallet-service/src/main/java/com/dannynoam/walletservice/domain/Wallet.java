package com.dannynoam.walletservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    private String secret;
    private String address;
    private String publicKey;
}
