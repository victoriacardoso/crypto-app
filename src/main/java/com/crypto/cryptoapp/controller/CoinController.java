package com.crypto.cryptoapp.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.cryptoapp.model.CoinModel;
import com.crypto.cryptoapp.repository.CoinRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @Bean
    public CoinModel init() {
        CoinModel c1 = new CoinModel();
        c1.setName("Bitcoin");
        c1.setPrice(new BigDecimal(100));
        c1.setQuantity(new BigDecimal(0.0005));
        c1.setDateTime(new Timestamp(System.currentTimeMillis()));

        CoinModel c2 = new CoinModel();
        c2.setName("Bitcoin");
        c2.setPrice(new BigDecimal(150));
        c2.setQuantity(new BigDecimal(0.0025));
        c2.setDateTime(new Timestamp(System.currentTimeMillis()));

        CoinModel c3 = new CoinModel();
        c3.setName("Ethereum");
        c3.setPrice(new BigDecimal(500));
        c3.setQuantity(new BigDecimal(0.0045));
        c3.setDateTime(new Timestamp(System.currentTimeMillis()));

        coinRepository.insert(c1);
        coinRepository.insert(c2);
        coinRepository.insert(c3);
        return c1;
    }

    @GetMapping("")
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> get(@PathVariable String name) {
        try {
            return new ResponseEntity<>(coinRepository.getByName(name), HttpStatus.OK);

        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody CoinModel coin) {
        try {
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping() 
    public ResponseEntity<?> put(@RequestBody CoinModel coinModel) {
        try {
            coinModel.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.update(coinModel), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable int id) {
        try {
            return new ResponseEntity<>(coinRepository.remove(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

}
