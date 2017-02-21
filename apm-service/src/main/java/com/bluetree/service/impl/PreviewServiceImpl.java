package com.bluetree.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bluetree.dao.PriceDao;
import com.bluetree.dao.StockDao;
import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.service.PreviewService;
import com.bluetree.service.domain.PreviewConfig;
import com.bluetree.service.domain.PreviewException;
import com.bluetree.service.domain.PreviewResult;
import com.bluetree.service.domain.Randomizer;
import com.bluetree.service.util.MeasureHolder;
import com.bluetree.service.util.PortfolioValuator;
import com.bluetree.service.util.PriceHolder;
import com.bluetree.service.util.PriceUtils;

@Service("previewService")
public class PreviewServiceImpl extends ServiceSupport implements
	PreviewService {

    @Resource
    private StockDao stockDao;

    @Resource
    private PriceDao priceDao;

    private void ValidateIndicePrices(PreviewConfig config,
	    List<Price> indicePrices) throws PreviewException {
	if (indicePrices.size() < config.getMinDateCount()) {
	    throw new PreviewException(
		    msg("indice [{0}] has [{1,number,0}] datas between [{2,date,yyyy-MM-dd}] and [{3,date,yyyy-MM-dd}], needed [{4,number,0}]",
			    config.getIndice().getName(), indicePrices.size(),
			    config.getFromDate(), config.getToDate(),
			    config.getMinDateCount()));
	}
    }

    private void validateStocks(PreviewConfig config, List<Stock> indiceStocks,
	    List<Stock> previewStocks) throws PreviewException {
	if (previewStocks.size() < config.getMinStockCount()) {
	    throw new PreviewException(
		    msg("only [{0,number,0}] stocks of indice [{1}] match dates, needed [{2,number,0}]",
			    previewStocks.size(), config.getIndice().getName(),
			    indiceStocks.size()));
	}
    }

    @Override
    public PreviewResult process(PreviewConfig config) throws PreviewException {

	Stock indice = config.getIndice();
	Date fromDate = config.getFromDate();
	Date toDate = config.getToDate();

	logger.debug(msg(
		"load indice [{0}] between [{1,date,yyyy-MM-dd}] and [{2,date,yyyy-MM-dd}]",
		indice.getName(), fromDate, toDate));
	List<Price> indicePrices = priceDao.findBetween(indice, fromDate,
		toDate);
	logger.debug(msg(
		"indice [{0}] has [{1,number,0}] datas between [{2,date,yyyy-MM-dd}] and [{3,date,yyyy-MM-dd}]",
		indice.getName(), indicePrices.size(), fromDate, toDate));

	ValidateIndicePrices(config, indicePrices);

	Date effectiveFromDate = PriceUtils.getFirstDate(indicePrices);
	Date effectiveToDate = PriceUtils.getLastDate(indicePrices);
	logger.debug(msg(
		"fromDate=[{0,date,yyyy-MM-dd}] -> effectiveFromDate=[{1,date,yyyy-MM-dd}]",
		fromDate, effectiveFromDate));
	logger.debug(msg(
		"toDate=[{0,date,yyyy-MM-dd}] -> effectiveToDate=[{1,date,yyyy-MM-dd}]",
		toDate, effectiveToDate));

	List<Stock> indiceStocks = stockDao.findChildren(indice);
	logger.debug(msg("indice [{0}] has [{1,number,0}] stocks",
		indice.getName(), indiceStocks.size()));

	List<Stock> previewStocks = new ArrayList<Stock>();
	PriceHolder priceHolder = new PriceHolder();
	priceHolder.addPrices(indice, indicePrices);

	for (Stock stock : indiceStocks) {
	    logger.debug(msg(
		    "load stock [{0}] between inclusive [{1,date,yyyy-MM-dd}] and [{2,date,yyyy-MM-dd}]",
		    stock.getName(), effectiveFromDate, effectiveToDate));
	    List<Price> stockPrices = priceDao.findBetweenInclusive(stock,
		    effectiveFromDate, effectiveToDate);
	    logger.debug(msg(
		    "stock [{0}] has [{1,number,0}] datas between inclusive [{2,date,yyyy-MM-dd}] and [{3,date,yyyy-MM-dd}]",
		    stock.getName(), stockPrices.size(), effectiveFromDate,
		    effectiveToDate));
	    if (!stockPrices.isEmpty()) {
		previewStocks.add(stock);
		priceHolder.addPrices(stock, stockPrices);
	    }
	}

	logger.debug(msg(
		"use [{0,number,0}] of [{1,number,0}] indice [{2}] stocks",
		previewStocks.size(), indiceStocks.size(), indice.getName()));
	validateStocks(config, indiceStocks, previewStocks);

	PreviewResult result = new PreviewResult();
	Randomizer randomizer = new Randomizer(config.getBasketSize(),
		previewStocks.size());
	MeasureHolder measureHolder = new MeasureHolder(
		config.getRiskFreeRate());
	measureHolder.setIndicePrices(indicePrices);

	logger.debug(msg("iterate [{0,number,0}] times",
		config.getPortfolioCount()));
	for (int i = 0; i < config.getPortfolioCount(); i++) {

	    int[] keys = randomizer.nextBasket();

	    Portfolio portfolio = new Portfolio();
	    portfolio.setIndice(indice);
	    for (int j = 0; j < keys.length; j++) {
		Stock stock = previewStocks.get(keys[j]);
		Price firstPrice = priceHolder.getPrice(stock,
			effectiveFromDate);
		Double firstValue = firstPrice.getValue();
		Double quantity = 100D / keys.length / firstValue;
		Holding holding = new Holding(quantity, stock);
		portfolio.getHoldings().add(holding);
	    }

	    List<Price> portfolioPrices = PortfolioValuator.valuate(portfolio,
		    effectiveFromDate, effectiveToDate, priceHolder);
	    measureHolder.setPortfolioPrices(portfolioPrices);
	    result.addCombinedMeasure(measureHolder.getCombinedMeasure());

	}

	return result;
    }

}
