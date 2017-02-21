package com.bluetree.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.dao.PriceDao;
import com.bluetree.dao.StockDao;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class UpdateServiceTest extends ServiceTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private PriceDao priceDao;

    @Resource
    private UpdateService updateService;

    @Test
    @Transactional
    public void testServiceWithoutLast() {

	String name = "YHOO";
	Stock stock = stockDao.findByName(name);
	if (stock == null) {
	    stock = new Stock();
	    stock.setName(name);
	    stockDao.insert(stock);
	}
	priceDao.deleteAll(stock);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(stock);

	updateService.update(stocks);

    }

    @Test
    @Transactional
    public void testServiceWithLast() {

	String name = "YHOO";
	Stock stock = stockDao.findByName(name);
	if (stock == null) {
	    stock = new Stock();
	    stock.setName(name);
	    stockDao.insert(stock);
	}
	priceDao.deleteAll(stock);
	Price price = new Price(parseDate("2013-01-01"), 123.45D);
	priceDao.insert(stock, price);
	List<Stock> stocks = new ArrayList<Stock>();
	stocks.add(stock);

	updateService.update(stocks);

    }

}
