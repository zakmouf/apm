package com.bluetree.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class PriceDaoTest extends DaoTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private PriceDao priceDao;

    @Test
    @Transactional
    public void testDao() {

	Stock stock = new Stock("sname");

	Date date = parseDate("2012-12-15");
	Date beforeDate = DateUtils.addDays(date, -1);
	Date afterDate = DateUtils.addDays(date, 1);

	Price price = new Price(date, 123.45d);

	stockDao.insert(stock);

	List<Price> prices;

	prices = priceDao.findAll(stock);
	Assert.assertTrue(prices.isEmpty());

	priceDao.insert(stock, price);
	prices = priceDao.findAll(stock);
	Assert.assertTrue(prices.contains(price));

	prices = priceDao.findFrom(stock, beforeDate);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findFrom(stock, date);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findFrom(stock, afterDate);
	Assert.assertTrue(prices.isEmpty());

	prices = priceDao.findFromInclusive(stock, beforeDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findFromInclusive(stock, date);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findFromInclusive(stock, afterDate);
	Assert.assertTrue(prices.contains(price));

	prices = priceDao.findBetween(stock, beforeDate, beforeDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetween(stock, beforeDate, date);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findBetween(stock, date, date);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findBetween(stock, date, afterDate);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findBetween(stock, afterDate, afterDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetween(stock, beforeDate, afterDate);
	Assert.assertTrue(prices.contains(price));

	prices = priceDao.findBetweenInclusive(stock, beforeDate, beforeDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetweenInclusive(stock, beforeDate, date);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetweenInclusive(stock, date, date);
	Assert.assertTrue(prices.contains(price));
	prices = priceDao.findBetweenInclusive(stock, date, afterDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetweenInclusive(stock, afterDate, afterDate);
	Assert.assertTrue(prices.isEmpty());
	prices = priceDao.findBetweenInclusive(stock, beforeDate, afterDate);
	Assert.assertTrue(prices.isEmpty());

	priceDao.delete(stock, price);
	prices = priceDao.findAll(stock);
	Assert.assertTrue(prices.isEmpty());

	priceDao.insert(stock, price);
	prices = priceDao.findAll(stock);
	Assert.assertTrue(prices.contains(price));

	priceDao.deleteAll(stock);
	prices = priceDao.findAll(stock);
	Assert.assertTrue(prices.isEmpty());

    }

    @Test
    @Transactional
    public void testLoad() {

	String name = "YHOO";
	Stock stock = stockDao.findByName(name);
	if (stock == null) {
	    stock = new Stock();
	    stock.setName(name);
	    stockDao.insert(stock);
	}

	Date fromDate = parseDate("2012-01-01");
	Date toDate = parseDate("2012-12-31");

	List<Price> prices;

	prices = priceDao.loadBetween(stock, fromDate, fromDate);
	Assert.assertEquals(prices.size(), 0);

	prices = priceDao.loadBetween(stock, fromDate, toDate);
	Assert.assertEquals(prices.size(), 250);

	prices = priceDao.loadBetween(stock, toDate, toDate);
	Assert.assertEquals(prices.size(), 1);

    }

}
