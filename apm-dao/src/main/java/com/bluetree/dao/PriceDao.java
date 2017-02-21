package com.bluetree.dao;

import java.util.Date;
import java.util.List;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

public interface PriceDao {

    List<Price> findAll(Stock stock);

    void insert(Stock stock, Price price);

    void deleteAll(Stock stock);

    void delete(Stock stock, Price price);

    List<Price> findFrom(Stock stock, Date fromDate);

    List<Price> findFromInclusive(Stock stock, Date fromDate);

    List<Price> findBetween(Stock stock, Date fromDate, Date toDate);

    List<Price> findBetweenInclusive(Stock stock, Date fromDate, Date toDate);

    List<Price> loadBetween(Stock stock, Date fromDate, Date toDate);

}
