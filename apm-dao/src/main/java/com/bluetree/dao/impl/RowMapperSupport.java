package com.bluetree.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

public abstract class RowMapperSupport {

    protected Stock mapStock(ResultSet rs) throws SQLException {
	return mapStock(rs, "stock");
    }

    protected Stock mapStock(ResultSet rs, String prefix) throws SQLException {
	Stock stock = new Stock();
	stock.setId(getLong(rs, prefix + "_id"));
	stock.setName(getString(rs, prefix + "_name"));
	stock.setDescription(getString(rs, prefix + "_description"));
	stock.setDateCount(getInteger(rs, prefix + "_date_count"));
	stock.setFirstDate(getDate(rs, prefix + "_first_date"));
	stock.setLastDate(getDate(rs, prefix + "_last_date"));
	return stock;
    }

    protected Price mapPrice(ResultSet rs) throws SQLException {
	return mapPrice(rs, "price");
    }

    protected Price mapPrice(ResultSet rs, String prefix) throws SQLException {
	Price price = new Price();
	price.setDate(getDate(rs, prefix + "_date"));
	price.setValue(getDouble(rs, prefix + "_value"));
	return price;
    }

    protected Portfolio mapPortfolio(ResultSet rs) throws SQLException {
	return mapPortfolio(rs, "portfolio");
    }

    protected Portfolio mapPortfolio(ResultSet rs, String prefix)
	    throws SQLException {
	Portfolio portfolio = new Portfolio();
	portfolio.setId(getLong(rs, prefix + "_id"));
	portfolio.setName(getString(rs, prefix + "_name"));
	portfolio.setStartDate(getDate(rs, prefix + "_start_date"));
	return portfolio;
    }

    protected Holding mapHolding(ResultSet rs) throws SQLException {
	return mapHolding(rs, "holding");
    }

    protected Holding mapHolding(ResultSet rs, String prefix)
	    throws SQLException {
	Holding holding = new Holding();
	holding.setQuantity(getDouble(rs, prefix + "_quantity"));
	return holding;
    }

    protected Long getLong(ResultSet rs, String columnLabel)
	    throws SQLException {
	long l = rs.getLong(columnLabel);
	return rs.wasNull() ? null : Long.valueOf(l);
    }

    protected Integer getInteger(ResultSet rs, String columnLabel)
	    throws SQLException {
	int i = rs.getInt(columnLabel);
	return rs.wasNull() ? null : Integer.valueOf(i);
    }

    protected String getString(ResultSet rs, String columnLabel)
	    throws SQLException {
	String s = rs.getString(columnLabel);
	return rs.wasNull() ? null : s;
    }

    protected Date getDate(ResultSet rs, String columnLabel)
	    throws SQLException {
	java.sql.Date d = rs.getDate(columnLabel);
	return rs.wasNull() ? null : new Date(d.getTime());
    }

    protected Double getDouble(ResultSet rs, String columnLabel)
	    throws SQLException {
	double d = rs.getDouble(columnLabel);
	return rs.wasNull() ? null : Double.valueOf(d);
    }

}
