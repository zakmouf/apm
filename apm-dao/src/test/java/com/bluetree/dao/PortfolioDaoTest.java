package com.bluetree.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class PortfolioDaoTest extends DaoTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private PortfolioDao portfolioDao;

    @Test
    @Transactional
    public void testDao() {

	Stock indice = new Stock("iname");
	stockDao.insert(indice);

	Portfolio portfolio = new Portfolio("pname", parseDate("2012-01-01"),
		indice);
	portfolioDao.insert(portfolio);
	Assert.assertNotNull(portfolio.getId());

	portfolio = portfolioDao.findById(portfolio.getId());
	Assert.assertNotNull(portfolio);

	List<Portfolio> portfolios = portfolioDao.findAll();
	Assert.assertTrue(portfolios.contains(portfolio));

	portfolioDao.delete(portfolio);
	Assert.assertNull(portfolioDao.findById(portfolio.getId()));

    }

    @Test
    @Transactional
    public void testHolding() {

	Stock indice = new Stock("iname");
	stockDao.insert(indice);

	Portfolio portfolio = new Portfolio("pname", parseDate("2012-01-01"),
		indice);
	portfolioDao.insert(portfolio);

	Stock stock = new Stock("sname");
	stockDao.insert(stock);

	Holding holding = new Holding(123.45D, stock);

	portfolio = portfolioDao.findById(portfolio.getId());
	Assert.assertTrue(portfolio.getHoldings().isEmpty());

	portfolio.getHoldings().add(holding);
	portfolioDao.update(portfolio);
	portfolio = portfolioDao.findById(portfolio.getId());
	Assert.assertTrue(portfolio.getHoldings().contains(holding));

	portfolio.getHoldings().clear();
	portfolioDao.update(portfolio);
	portfolio = portfolioDao.findById(portfolio.getId());
	Assert.assertTrue(portfolio.getHoldings().isEmpty());

	portfolio.getHoldings().add(holding);
	portfolioDao.delete(portfolio);

    }

}
