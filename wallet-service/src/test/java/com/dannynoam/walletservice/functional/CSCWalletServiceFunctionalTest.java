package com.dannynoam.walletservice.functional;

import com.dannynoam.walletservice.application.WalletServiceApplication;
import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WalletServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CSCWalletServiceFunctionalTest {

    private static final String ADDRESS = "AN_ADDRESS";
    private static final String PUBLIC_KEY = "A_PUBLIC_KEY";
    private static final String SECRET = "A_SECRET";
    private static final String WORD = "A_WORD";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void storeWallet_storesWalletInDatabase() throws Exception {
        String address = "SomeAddress";
        String publicKey = "SomePublicKey";
        String secret = "SomeSecret";
        String word = "SomeWord";

        Wallet wallet = Wallet.builder()
                .address(new WalletAddress(address))
                .publicKey(publicKey)
                .secret(secret)
                .word(word)
                .build();

        mockMvc.perform(
            post("/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(wallet)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.address", is(address)))
            .andExpect(jsonPath("$.publicKey", is(publicKey)))
            .andExpect(jsonPath("$.secret", is(secret)))
            .andExpect(jsonPath("$.word", is(word)));
    }

    @Test
    public void getWallet_walletExists_returnsWallet() throws Exception {
        mockMvc.perform(
            get("/wallet/".concat(ADDRESS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.address", is(ADDRESS)))
            .andExpect(jsonPath("$.publicKey", is(PUBLIC_KEY)))
            .andExpect(jsonPath("$.secret", is(SECRET)));

    }

    @Test
    public void getWallet_walletDoesNotExist_returns404() throws Exception {
        mockMvc.perform(
            get("/wallet/".concat("NON EXISTENT WALLET"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getWallets_walletsFoundForWord_returnsListOfWallets() throws Exception {
        mockMvc.perform(
            get("/wallets")
                .queryParam("word", WORD)
                .queryParam("limit", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].address", is(ADDRESS)));
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
