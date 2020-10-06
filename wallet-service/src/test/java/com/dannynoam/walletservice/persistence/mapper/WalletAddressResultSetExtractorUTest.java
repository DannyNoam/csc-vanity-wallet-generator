package com.dannynoam.walletservice.persistence.mapper;

import com.dannynoam.walletservice.domain.WalletAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletAddressResultSetExtractorUTest {

    private static final String ADDRESS = "cERKUMRxtgW2asds7Z8Sd1KWazgtENbWEH";

    @Mock
    private ResultSet resultSet;

    private final WalletAddressResultSetExtractor testObj = new WalletAddressResultSetExtractor();

    @Test
    public void extractData_noResultsReturnedInResultSet_returnsEmptyList() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        List<WalletAddress> actual = testObj.extractData(resultSet);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void extractData_resultsReturnedInResultSet_returnsListOfWalletAddresses() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("ADDRESS")).thenReturn(ADDRESS);
        List<WalletAddress> expectedWalletAddresses = Collections.singletonList(new WalletAddress(ADDRESS));

        List<WalletAddress> actual = testObj.extractData(resultSet);

        assertEquals(expectedWalletAddresses, actual);
    }
}