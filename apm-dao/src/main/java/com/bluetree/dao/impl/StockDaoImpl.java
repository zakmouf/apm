package com.bluetree.dao.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.bluetree.dao.StockDao;
import com.bluetree.domain.Stock;

@Repository("stockDao")
public class StockDaoImpl extends DaoSupport implements StockDao {

    @Value("${stock.select.all.query}")
    private String selectAllQuery;

    @Value("${stock.select.id.query}")
    private String selectByIdQuery;

    @Value("${stock.select.name.query}")
    private String selectByNameQuery;

    @Value("${stock.insert.query}")
    private String insertQuery;

    @Value("${stock.update.query}")
    private String updateQuery;

    @Value("${stock.delete.query}")
    private String deleteQuery;

    @Value("${stock.select.parent.query}")
    private String selectParentQuery;

    @Value("${stock.select.child.query}")
    private String selectChildQuery;

    @Value("${stock.insert.relation.query}")
    private String insertRelationQuery;

    @Value("${stock.delete.relation.query}")
    private String deleteRelationQuery;

    @Override
    public List<Stock> findAll() {
	Object[] args = {};
	int[] argTypes = {};
	List<Stock> stocks = jdbcTemplate.query(selectAllQuery, args, argTypes,
		new StockRowMapper());
	Collections.sort(stocks);
	logger.debug("find [{}] stocks", stocks.size());
	return stocks;
    }

    @Override
    public Stock findById(Long id) {
	Assert.notNull(id);
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	Stock stock = null;
	try {
	    stock = jdbcTemplate.queryForObject(selectByIdQuery, args,
		    argTypes, new StockRowMapper());
	} catch (EmptyResultDataAccessException ex) {

	}
	logger.debug(msg("find by id=[{0,number,0}] stock=[{1}]", id, stock));
	return stock;
    }

    @Override
    public Stock findByName(String name) {
	Assert.notNull(name);
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	Stock stock = null;
	try {
	    stock = jdbcTemplate.queryForObject(selectByNameQuery, args,
		    argTypes, new StockRowMapper());
	} catch (EmptyResultDataAccessException ex) {

	}
	logger.debug(msg("find by name=[{0}] stock=[{1}]", name, stock));
	return stock;
    }

    @Override
    public void insert(Stock stock) {
	Assert.notNull(stock);
	Assert.isNull(stock.getId());
	Assert.notNull(stock.getName());
	stock.setId(getNextId());
	Object[] args = { stock.getId(), stock.getName(),
		stock.getDescription() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR };
	jdbcTemplate.update(insertQuery, args, argTypes);
	logger.debug(msg("insert stock=[{0}]", stock));
    }

    @Override
    public void update(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Assert.notNull(stock.getName());
	Object[] args = { stock.getName(), stock.getDescription(),
		stock.getId() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.NUMERIC };
	jdbcTemplate.update(updateQuery, args, argTypes);
	logger.debug(msg("update stock=[{0}]", stock));
    }

    @Override
    public void delete(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	jdbcTemplate.update(deleteQuery, args, argTypes);
	logger.debug(msg("delete stock=[{0}]", stock));
    }

    @Override
    public List<Stock> findParents(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	List<Stock> stocks = jdbcTemplate.query(selectParentQuery, args,
		argTypes, new StockRowMapper());
	Collections.sort(stocks);
	logger.debug(msg("find [{0,number,0}] parents for stock=[{1}]",
		stocks.size(), stock));
	return stocks;
    }

    @Override
    public List<Stock> findChildren(Stock stock) {
	Assert.notNull(stock);
	Assert.notNull(stock.getId());
	Object[] args = { stock.getId() };
	int[] argTypes = { Types.NUMERIC };
	List<Stock> stocks = jdbcTemplate.query(selectChildQuery, args,
		argTypes, new StockRowMapper());
	Collections.sort(stocks);
	logger.debug(msg("find [{0,number,0}] children for stock=[{1}]",
		stocks.size(), stock));
	return stocks;
    }

    @Override
    public void insertRelation(Stock parent, Stock child) {
	Assert.notNull(parent);
	Assert.notNull(parent.getId());
	Assert.notNull(child);
	Assert.notNull(child.getId());
	Object[] args = { parent.getId(), child.getId() };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC };
	jdbcTemplate.update(insertRelationQuery, args, argTypes);
	logger.debug(msg("insert relation parent=[{0}] child=[{1}]", parent,
		child));
    }

    @Override
    public void deleteRelation(Stock parent, Stock child) {
	Assert.notNull(parent);
	Assert.notNull(parent.getId());
	Assert.notNull(child);
	Assert.notNull(child.getId());
	Object[] args = { parent.getId(), child.getId() };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC };
	jdbcTemplate.update(deleteRelationQuery, args, argTypes);
	logger.debug(msg("delete relation parent=[{0}] child=[{1}]", parent,
		child));
    }

}
