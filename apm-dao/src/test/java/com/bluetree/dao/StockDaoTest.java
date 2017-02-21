package com.bluetree.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class StockDaoTest extends DaoTestSupport {

    @Resource
    private StockDao stockDao;

    @Test
    @Transactional
    public void testDao() {

	Stock stock = new Stock("sname", "sdescription");

	Assert.assertNull(stockDao.findByName(stock.getName()));

	stockDao.insert(stock);
	Assert.assertNotNull(stock.getId());

	stock = stockDao.findById(stock.getId());
	Assert.assertNotNull(stock);

	stock = stockDao.findByName(stock.getName());
	Assert.assertNotNull(stock);

	List<Stock> stocks = stockDao.findAll();
	Assert.assertTrue(stocks.contains(stock));

	stock.setName("#" + stock.getName() + "#");
	stock.setDescription("#" + stock.getDescription() + "#");
	stockDao.update(stock);
	stock = stockDao.findByName(stock.getName());
	Assert.assertNotNull(stock);

	stockDao.delete(stock);
	Assert.assertNull(stockDao.findById(stock.getId()));
	Assert.assertNull(stockDao.findByName(stock.getName()));

    }

    @Test
    @Transactional
    public void testRelation() {

	logger.debug("relation prepare");

	Stock parent = new Stock("pname");
	Stock child = new Stock("cname");
	stockDao.insert(parent);
	stockDao.insert(child);

	List<Stock> parents;
	List<Stock> children;

	logger.debug("relation before insert");

	children = stockDao.findChildren(parent);
	Assert.assertTrue(children.isEmpty());

	parents = stockDao.findParents(child);
	Assert.assertTrue(parents.isEmpty());

	logger.debug("relation after insert");

	stockDao.insertRelation(parent, child);

	children = stockDao.findChildren(parent);
	Assert.assertTrue(children.contains(child));

	parents = stockDao.findParents(child);
	Assert.assertTrue(parents.contains(parent));

	logger.debug("relation after delete");

	stockDao.deleteRelation(parent, child);

	children = stockDao.findChildren(parent);
	Assert.assertTrue(children.isEmpty());

	parents = stockDao.findParents(child);
	Assert.assertTrue(parents.isEmpty());

	logger.debug("relation clean");

	stockDao.delete(parent);
	stockDao.delete(child);

	logger.debug("relation cascade parent");

	parent = new Stock("pname");
	child = new Stock("cname");
	stockDao.insert(parent);
	stockDao.insert(child);
	stockDao.insertRelation(parent, child);
	stockDao.delete(parent);
	stockDao.delete(child);

	logger.debug("relation cascade child");

	parent = new Stock("pname");
	child = new Stock("cname");
	stockDao.insert(parent);
	stockDao.insert(child);
	stockDao.insertRelation(parent, child);
	stockDao.delete(child);
	stockDao.delete(parent);

    }

}
