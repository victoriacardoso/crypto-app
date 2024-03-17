package com.crypto.cryptoapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.crypto.cryptoapp.model.CoinModel;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    private static String INSERT = "insert into coin (name, price, quantity, datetime) values(?,?,?,?)";
    private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";

    private JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CoinModel insert (CoinModel coin) {
        Object[] attr = new Object[] {
            coin.getName(),
            coin.getPrice(),
            coin.getQuantity(),
            coin.getDateTime()
        };
        jdbcTemplate.update(INSERT, attr);
        return coin;
    }

    public List<CoinModel> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinModel>() {

            @Override
            public CoinModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                CoinModel coin = new CoinModel();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                
                return coin;
            }
            
        });
    }


}
