package com.bluetree.dao.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.bluetree.dao.PortfolioDao;
import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;

@Repository("profolioDao")
public class PortfolioDaoImpl extends DaoSupport implements PortfolioDao {

    @Value("${portfolio.select.all.query}")
    private String selectAllQuery;

    @Value("${portfolio.select.id.query}")
    private String selectByIdQuery;

    @Value("${portfolio.insert.query}")
    private String insertQuery;

    @Value("${portfolio.update.query}")
    private String updateQuery;

    @Value("${portfolio.delete.query}")
    private String deleteQuery;

    @Value("${portfolio.select.holding.query}")
    private String selectHoldingQuery;

    @Value("${portfolio.insert.holding.query}")
    private String insertHoldingQuery;

    @Value("${portfolio.delete.holding.query}")
    private String deleteHoldingQuery;

    @Override
    public List<Portfolio> findAll() {
	List<Portfolio> portfolios = jdbcTemplate.query(selectAllQuery,
		new PortfolioRowMapper());
	Collections.sort(portfolios);
	logger.debug(msg("find [{0,number,0}] portfolios", portfolios.size()));
	return portfolios;
    }

    @Override
    public Portfolio findById(Long id) {

	Assert.notNull(id);

	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	Portfolio portfolio = null;
	try {
	    portfolio = jdbcTemplate.queryForObject(selectByIdQuery, args,
		    argTypes, new PortfolioRowMapper());
	} catch (EmptyResultDataAccessException ex) {

	}
	logger.debug(msg("find by id=[{0,number,0}] portfolio=[{1}]", id,
		portfolio));

	if (portfolio != null) {
	    List<Holding> holdings = findHoldings(portfolio);
	    portfolio.getHoldings().addAll(holdings);
	}

	return portfolio;

    }

    @Override
    public void insert(Portfolio portfolio) {

	Assert.notNull(portfolio);
	Assert.isNull(portfolio.getId());
	Assert.notNull(portfolio.getName());
	Assert.notNull(portfolio.getStartDate());
	Assert.notNull(portfolio.getIndice());
	Assert.notNull(portfolio.getIndice().getId());
	Assert.notNull(portfolio.getHoldings());

	portfolio.setId(getNextId());

	Object[] args = { portfolio.getId(), portfolio.getName(),
		portfolio.getStartDate(), portfolio.getIndice().getId() };
	int[] argTypes = { Types.NUMERIC, Types.VARCHAR, Types.DATE,
		Types.NUMERIC };
	jdbcTemplate.update(insertQuery, args, argTypes);
	logger.debug(msg("insert portfolio=[{0}]", portfolio));

	for (Holding holding : portfolio.getHoldings()) {
	    insertHolding(portfolio, holding);
	}

    }

    @Override
    public void update(Portfolio portfolio) {

	Assert.notNull(portfolio);
	Assert.notNull(portfolio.getId());
	Assert.notNull(portfolio.getName());
	Assert.notNull(portfolio.getStartDate());
	Assert.notNull(portfolio.getIndice());
	Assert.notNull(portfolio.getIndice().getId());
	Assert.notNull(portfolio.getHoldings());

	Portfolio oldPortfolio = findById(portfolio.getId());
	Assert.notNull(oldPortfolio);
	for (Holding oldHolding : oldPortfolio.getHoldings()) {
	    deleteHolding(oldPortfolio, oldHolding);
	}

	Object[] args = { portfolio.getName(), portfolio.getStartDate(),
		portfolio.getIndice().getId(), portfolio.getId() };
	int[] argTypes = { Types.VARCHAR, Types.DATE, Types.NUMERIC,
		Types.NUMERIC };
	jdbcTemplate.update(updateQuery, args, argTypes);
	logger.debug(msg("update portfolio=[{0}]", portfolio));

	for (Holding holding : portfolio.getHoldings()) {
	    insertHolding(portfolio, holding);
	}

    }

    @Override
    public void delete(Portfolio portfolio) {
	Assert.notNull(portfolio);
	Assert.notNull(portfolio.getId());
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	jdbcTemplate.update(deleteQuery, args, argTypes);
	logger.debug(msg("delete portfolio=[{0}]", portfolio));
    }

    private List<Holding> findHoldings(Portfolio portfolio) {
	Assert.notNull(portfolio);
	Assert.notNull(portfolio.getId());
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	List<Holding> holdings = jdbcTemplate.query(selectHoldingQuery, args,
		argTypes, new HoldingRowMapper());
	Collections.sort(holdings);
	logger.debug(msg("find [{0,number,0}] holdings for portfolio=[{1}]",
		holdings.size(), portfolio));
	return holdings;
    }

    private void insertHolding(Portfolio portfolio, Holding holding) {
	Assert.notNull(portfolio);
	Assert.notNull(portfolio.getId());
	Assert.notNull(holding);
	Assert.notNull(holding.getQuantity());
	Assert.notNull(holding.getStock());
	Assert.notNull(holding.getStock().getId());
	Object[] args = { portfolio.getId(), holding.getQuantity(),
		holding.getStock().getId() };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };
	jdbcTemplate.update(insertHoldingQuery, args, argTypes);
	logger.debug(msg("insert holding=[{0}] in portfolio=[{1}]", holding,
		portfolio));
    }

    private void deleteHolding(Portfolio portfolio, Holding holding) {
	Assert.notNull(portfolio);
	Assert.notNull(portfolio.getId());
	Assert.notNull(holding);
	Assert.notNull(holding.getStock());
	Assert.notNull(holding.getStock().getId());
	Object[] args = { portfolio.getId(), holding.getStock().getId() };
	int[] argTypes = { Types.NUMERIC, Types.NUMERIC };
	jdbcTemplate.update(deleteHoldingQuery, args, argTypes);
	logger.debug(msg("delete holding=[{0}] from portfolio=[{1}]", holding,
		portfolio));

    }

}
