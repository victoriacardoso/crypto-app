package com.crypto.cryptoapp.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.cryptoapp.model.CoinModel;
import com.crypto.cryptoapp.repository.CoinRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @GetMapping("")
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
    }
    

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody CoinModel coin){
        try {
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
