package com.bluetree.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.Assert;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

public class PriceHolder {

    private TreeMap<String, TreeMap<Date, Double>> stockMap = new TreeMap<String, TreeMap<Date, Double>>();

    public void addPrices(Stock stock, List<Price> prices) {

	Assert.notNull(stock);
	Assert.hasText(stock.getName());
	Assert.notNull(prices);
	Assert.notEmpty(prices);
	Assert.isTrue(!stockMap.containsKey(stock.getName()));

	TreeMap<Date, Double> priceMap = new TreeMap<Date, Double>();

	for (Price price : prices) {

	    Assert.notNull(price.getDate());
	    Assert.notNull(price.getValue());
	    Assert.isTrue(!priceMap.containsKey(price.getDate()));

	    priceMap.put(price.getDate(), price.getValue());

	}

	stockMap.put(stock.getName(), priceMap);

    }

    public List<Price> getPrices(Stock stock) {

	Assert.notNull(stock);
	Assert.hasText(stock.getName());
	Assert.isTrue(stockMap.containsKey(stock.getName()));

	List<Price> prices = new ArrayList<Price>();
	TreeMap<Date, Double> priceMap = stockMap.get(stock.getName());

	for (Entry<Date, Double> entry : priceMap.entrySet()) {
	    prices.add(new Price(entry.getKey(), entry.getValue()));
	}

	return prices;

    }

    public Price getPrice(Stock stock, Date date) {

	Assert.notNull(stock);
	Assert.hasText(stock.getName());
	Assert.notNull(date);
	Assert.isTrue(stockMap.containsKey(stock.getName()));

	TreeMap<Date, Double> priceMap = stockMap.get(stock.getName());
	Date firstDate = priceMap.firstKey();
	Assert.isTrue(!date.before(firstDate));

	Double value = priceMap.get(date);

	while (value == null && !date.before(firstDate)) {
	    date = DateUtils.addDays(date, -1);
	    value = priceMap.get(date);
	}

	Assert.notNull(value);

	return new Price(date, value);

    }

}
