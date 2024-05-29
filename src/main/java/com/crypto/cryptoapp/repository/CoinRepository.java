package com.crypto.cryptoapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.crypto.cryptoapp.dto.CoinDTO;
import com.crypto.cryptoapp.model.CoinModel;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    private static String INSERT = "insert into coin (name, price, quantity, datetime) values(?,?,?,?)";
    private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";
    private static String SELECT_BY_NAME = "select * from coin where name = ?";
    private static String DELETE = "delete from coin where id = ?";
    private static String UPDATE = "update coin set name = ?, price = ?, quantity = ? where id = ?";

    private JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public CoinModel insert(CoinModel coin) {
        Object[] attr = new Object[] {
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT, attr);
        return coin;
    }

    public CoinModel update(CoinModel coin) {
        Object[] attr = new Object[] {
            coin.getName(),
            coin.getPrice(),
            coin.getQuantity(),
            coin.getId()
        };
        jdbcTemplate.update(UPDATE, attr);
        return coin;
    }

    public List<CoinDTO> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinDTO>() {

            @Override
            public CoinDTO mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
                CoinDTO coin = new CoinDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }

        });
    }

    public List<CoinModel> getByName(String name) {
        Object[] attr = new Object[] {name};
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<CoinModel>() {

            @Override
            public CoinModel mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
                CoinModel coinModel = new CoinModel();
                coinModel.setId(rs.getInt("id"));
                coinModel.setName(rs.getString("name"));
                coinModel.setPrice(rs.getBigDecimal("price"));
                coinModel.setQuantity(rs.getBigDecimal("quantity"));
                coinModel.setDateTime(rs.getTimestamp("datetime"));
                return coinModel;
            }

        }, attr);
    }

    public int remove(int id){
        return jdbcTemplate.update(DELETE, id);
    }

}
