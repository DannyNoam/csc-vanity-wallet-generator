package com.dannynoam.walletservice.persistence.mapper;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletResultSetExtractorUTest {

    private static final String ADDRESS = "cERKUMRxtgW2asds7Z8Sd1KWazgtENbWEH";
    private static final String PUBLIC_KEY = "038F5DDA05EBB39A4095DD6E24447024264E850A93F42065C7B0FF0E37CC181D90";
    private static final String SECRET = "ssEcnRSwQ1xXdyvYM8qdrwNMZQySm";

    @Mock
    private ResultSet resultSet;

    private final WalletResultSetExtractor testObj = new WalletResultSetExtractor();

    @Test
    public void extractData_noResultsReturnedInResultSet_returnsNull() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Wallet actual = testObj.extractData(resultSet);

        assertNull(actual);
    }

    @Test
    public void extractData_resultsReturnedInResultSet_returnsWallet() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("ADDRESS")).thenReturn(ADDRESS);
        when(resultSet.getString("PUBLIC_KEY")).thenReturn(PUBLIC_KEY);
        when(resultSet.getString("SECRET")).thenReturn(SECRET);
        Wallet expectedWallet = Wallet.builder()
                .address(new WalletAddress(ADDRESS))
                .publicKey(PUBLIC_KEY)
                .secret(SECRET)
                .build();

        Wallet actual = testObj.extractData(resultSet);

        assertEquals(expectedWallet, actual);
    }
}