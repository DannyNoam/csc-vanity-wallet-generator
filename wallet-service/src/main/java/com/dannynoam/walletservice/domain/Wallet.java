package com.dannynoam.walletservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    private String secret;
    private String address;
    private String publicKey;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String word;
}
