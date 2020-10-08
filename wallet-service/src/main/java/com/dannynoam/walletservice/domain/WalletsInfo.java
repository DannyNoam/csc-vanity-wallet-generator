package com.dannynoam.walletservice.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class WalletsInfo {
    private int count;
    private int maxWordLength;
}
