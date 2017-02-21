package com.bluetree.service.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.service.ServiceTestSupport;

public class PriceHolderTest extends ServiceTestSupport {

    @Test
    public void testHolder() {

	Stock stock = new Stock("s1");

	List<Price> prices = new ArrayList<Price>();
	Price price1 = new Price(parseDate("2012-01-01"), 1D);
	prices.add(price1);
	Price price2 = new Price(parseDate("2012-02-01"), 2D);
	prices.add(price2);
	Price price3 = new Price(parseDate("2012-03-01"), 3D);
	prices.add(price3);

	PriceHolder holder = new PriceHolder();
	holder.addPrices(stock, prices);

	Assert.assertEquals(price1,
		holder.getPrice(stock, parseDate("2012-01-01")));
	Assert.assertEquals(price1,
		holder.getPrice(stock, parseDate("2012-01-02")));
	Assert.assertEquals(price1,
		holder.getPrice(stock, parseDate("2012-01-15")));
	Assert.assertEquals(price1,
		holder.getPrice(stock, parseDate("2012-01-31")));

	Assert.assertEquals(price2,
		holder.getPrice(stock, parseDate("2012-02-01")));
	Assert.assertEquals(price2,
		holder.getPrice(stock, parseDate("2012-02-02")));
	Assert.assertEquals(price2,
		holder.getPrice(stock, parseDate("2012-02-15")));
	Assert.assertEquals(price2,
		holder.getPrice(stock, parseDate("2012-02-28")));

	Assert.assertEquals(price3,
		holder.getPrice(stock, parseDate("2012-03-01")));
	Assert.assertEquals(price3,
		holder.getPrice(stock, parseDate("2012-03-02")));
	Assert.assertEquals(price3,
		holder.getPrice(stock, parseDate("2012-03-15")));
	Assert.assertEquals(price3,
		holder.getPrice(stock, parseDate("2012-03-31")));

    }

}
