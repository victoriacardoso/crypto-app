package com.crypto.cryptoapp.repository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.crypto.cryptoapp.dto.CoinDTO;
import com.crypto.cryptoapp.model.CoinModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public CoinModel insert(CoinModel coin) {
        entityManager.persist(coin);
        return coin;
    }

    @Transactional
    public CoinModel update(CoinModel coin) {
        entityManager.merge(coin);
        return coin;
    }

    public List<CoinDTO> getAll() {
        String jpql = "select new CoinDTO(c.name, sum (c.quantity)) from CoinModel c group by c.name";
        TypedQuery<CoinDTO> query = entityManager.createQuery(jpql, CoinDTO.class);
        return query.getResultList();
    }

    public List<CoinModel> getByName(String name) {
        String jpql = "select c from CoinModel c where c.name like :name";
        TypedQuery<CoinModel> query = entityManager.createQuery(jpql, CoinModel.class);
        query.setParameter("name", "%" + name + "%");

        return query.getResultList();
    }
    @Transactional
    public boolean remove(int id) {
        CoinModel coin = entityManager.find(CoinModel.class, id);

        if (coin == null) {
            throw new RuntimeException();
        }

        entityManager.remove(coin);
        return true;

    }

}
