package com.bluetree.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluetree.dao.StockDao;
import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class UpdateServiceLoadTest extends ServiceTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private UpdateService updateService;

    @Test
    public void testServiceLoad() {
	Stock stock = stockDao.findByName("^GDAXI");
	Assert.assertTrue(stock != null);
	List<Stock> stocks = stockDao.findChildren(stock);
	stocks.add(stock);
	updateService.update(stocks);
    }

}
