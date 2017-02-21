package com.bluetree.service.util;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluetree.dao.PriceDao;
import com.bluetree.dao.StockDao;
import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.service.ServiceTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class PortfolioValuatorTest extends ServiceTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private PriceDao priceDao;

    @Test
    public void testValuator() {

	Stock indice = stockDao.findByName("^GDAXI");
	Assert.assertNotNull(indice);

	List<Stock> stocks = stockDao.findChildren(indice);
	Assert.assertFalse(stocks.isEmpty());
	logger.debug(msg("find [{0,number,0}] stocks in indice=[{1}]",
		stocks.size(), indice));

	Date fromDate = parseDate("2010-01-01");
	Date toDate = parseDate("2012-12-31");
	logger.debug(msg(
		"fromDate=[{0,date,yyyy-MM-dd}] toDate=[{1,date,yyyy-MM-dd}]",
		fromDate, toDate));

	List<Price> iprices = priceDao.findBetween(indice, fromDate, toDate);
	Assert.assertFalse(iprices.isEmpty());
	logger.debug(msg("find [{0,number,0}] dates in indice=[{1}]",
		iprices.size(), indice));
	iprices = PriceUtils.rebase(iprices, 100D);

	fromDate = PriceUtils.getFirstDate(iprices);
	toDate = PriceUtils.getLastDate(iprices);
	logger.debug(msg(
		"fromDate=[{0,date,yyyy-MM-dd}] toDate=[{1,date,yyyy-MM-dd}]",
		fromDate, toDate));

	Portfolio portfolio = new Portfolio(indice.getName(), null, indice);
	List<Holding> holdings = portfolio.getHoldings();

	PriceHolder priceHolder = new PriceHolder();
	priceHolder.addPrices(indice, iprices);
	List<Price> sprices;

	logger.debug("******************************************************");

	for (Stock stock : stocks) {

	    sprices = priceDao.findBetweenInclusive(stock, fromDate, toDate);
	    Assert.assertFalse(sprices.isEmpty());
	    logger.debug(msg("find [{0,number,0}] dates in stock=[{1}]",
		    sprices.size(), stock));

	    priceHolder.addPrices(stock, sprices);
	    Double sfirstValue = PriceUtils.getFirstPrice(sprices).getValue();
	    Double quantity = 100D / stocks.size() / sfirstValue;
	    logger.debug(msg(
		    "assign [{0,number,0.00000}] quantity in stock=[{1}]",
		    quantity, stock));
	    holdings.add(new Holding(quantity, stock));

	}

	logger.debug("******************************************************");

	List<Price> pprices = PortfolioValuator.valuate(portfolio, fromDate,
		toDate, priceHolder);
	logger.debug(msg("valuate [{0,number,0}] dates in indice=[{1}]",
		pprices.size(), indice));
	Double pfirstValue = PriceUtils.getFirstValue(pprices);
	Double plastValue = PriceUtils.getLastValue(pprices);

	logger.debug("******************************************************");

	for (Holding holding : holdings) {
	    Stock stock = holding.getStock();
	    Double sfirstValue = priceHolder.getPrice(stock, fromDate)
		    .getValue();
	    Double sLastValue = priceHolder.getPrice(stock, toDate).getValue();
	    Double hfirstWeight = holding.getQuantity() * sfirstValue
		    / pfirstValue;
	    Double hlastWeight = holding.getQuantity() * sLastValue
		    / plastValue;
	    logger.debug(msg(
		    "hfirstWeight=[{0,number,0.00%}] hlastWeight=[{1,number,0.00%}] for stock=[{2}]",
		    hfirstWeight, hlastWeight, stock));
	}

	logger.debug("******************************************************");

	for (Price pprice : pprices) {
	    Date date = pprice.getDate();
	    Price iprice = priceHolder.getPrice(indice, date);
	    logger.debug(msg(
		    "{0,date,yyyy-MM-dd}    {1,number,0.00}    {2,number,0.00}",
		    date, iprice.getValue(), pprice.getValue()));
	}

	logger.debug("******************************************************");

	MeasureHolder measureHolder = new MeasureHolder(0.03);
	measureHolder.setIndicePrices(iprices);
	measureHolder.setPortfolioPrices(pprices);
	logger.debug(msg("measure=[{0}]", measureHolder.getCombinedMeasure()));

    }

}
