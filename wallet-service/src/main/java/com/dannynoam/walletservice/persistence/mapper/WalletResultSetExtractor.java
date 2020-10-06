package com.dannynoam.walletservice.persistence.mapper;

import com.dannynoam.walletservice.domain.Wallet;
import com.dannynoam.walletservice.domain.WalletAddress;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WalletResultSetExtractor implements ResultSetExtractor<Wallet> {

    @Override
    public Wallet extractData(ResultSet rs) throws SQLException {
        if(rs.next()) {
            Wallet wallet = new Wallet();

            wallet.setAddress(new WalletAddress(rs.getString("ADDRESS")));
            wallet.setPublicKey(rs.getString("PUBLIC_KEY"));
            wallet.setSecret(rs.getString("SECRET"));

            return wallet;
        }

        return null;
    }
}
