package com.bluetree.service;

import java.util.List;

import com.bluetree.domain.Stock;

public interface StockService {

    void create(Stock parent, List<Stock> children);

}
