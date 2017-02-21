package com.bluetree.dao;

import java.util.List;

import com.bluetree.domain.Stock;

public interface StockDao {

    List<Stock> findAll();

    Stock findById(Long id);

    Stock findByName(String name);

    void insert(Stock stock);

    void update(Stock stock);

    void delete(Stock stock);

    List<Stock> findParents(Stock stock);

    List<Stock> findChildren(Stock stock);

    void insertRelation(Stock parent, Stock child);

    void deleteRelation(Stock parent, Stock child);

}
