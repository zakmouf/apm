package com.bluetree.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bluetree.domain.Price;

public abstract class PriceUtils {

    public static Price getFirstPrice(List<Price> prices) {
	return prices.get(0);
    }

    public static Date getFirstDate(List<Price> prices) {
	return getFirstPrice(prices).getDate();
    }

    public static Double getFirstValue(List<Price> prices) {
	return getFirstPrice(prices).getValue();
    }

    public static Price getLastPrice(List<Price> prices) {
	return prices.get(prices.size() - 1);
    }

    public static Date getLastDate(List<Price> prices) {
	return getLastPrice(prices).getDate();
    }

    public static Double getLastValue(List<Price> prices) {
	return getLastPrice(prices).getValue();
    }

    public static List<Price> rebase(List<Price> prices, Double base) {
	List<Price> bprices = new ArrayList<Price>();
	Double firstValue = getFirstValue(prices);
	for (Price price : prices) {
	    bprices.add(new Price(price.getDate(), price.getValue() * base
		    / firstValue));
	}
	return bprices;
    }

}
