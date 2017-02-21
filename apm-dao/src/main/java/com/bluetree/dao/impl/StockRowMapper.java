package com.bluetree.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bluetree.domain.Stock;

public class StockRowMapper extends RowMapperSupport implements
	RowMapper<Stock> {

    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
	Stock stock = mapStock(rs);
	return stock;
    }

}
