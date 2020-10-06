package com.dannynoam.walletservice.persistence.mapper;

import com.dannynoam.walletservice.domain.WalletAddress;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WalletAddressResultSetExtractor implements ResultSetExtractor<List<WalletAddress>> {

    @Override
    public List<WalletAddress> extractData(ResultSet rs) throws SQLException {
        List<WalletAddress> walletAddresses = new ArrayList<>();

        while(rs.next()) {
            walletAddresses.add(new WalletAddress(rs.getString("ADDRESS")));
        }

        return walletAddresses;
    }
}
