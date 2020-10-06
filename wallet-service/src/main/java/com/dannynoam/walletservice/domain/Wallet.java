package com.dannynoam.walletservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Wallet {

    private String secret;

    @JsonUnwrapped
    private WalletAddress address;
    private String publicKey;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String word;
}
