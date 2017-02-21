package com.bluetree.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bluetree.domain.Holding;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

public class PortfolioValuator {

    public static List<Price> valuate(Portfolio portfolio, Date fromDate,
	    Date toDate, PriceHolder priceHolder) {

	Stock indice = portfolio.getIndice();
	List<Holding> holdings = portfolio.getHoldings();

	List<Price> indicePrices = priceHolder.getPrices(indice);
	List<Price> portfolioPrices = new ArrayList<Price>();
	for (Price indicePrice : indicePrices) {
	    Date date = indicePrice.getDate();
	    if (date.compareTo(fromDate) >= 0 && date.compareTo(toDate) <= 0) {
		Double portfolioValue = 0D;
		Double holdingValue = null;
		Stock holdingStock = null;
		Price holdingStockPrice = null;
		for (Holding holding : holdings) {
		    holdingStock = holding.getStock();
		    holdingStockPrice = priceHolder
			    .getPrice(holdingStock, date);
		    holdingValue = holding.getQuantity()
			    * holdingStockPrice.getValue();
		    portfolioValue += holdingValue;
		}
		portfolioPrices.add(new Price(date, portfolioValue));
	    }
	}

	return portfolioPrices;

    }

}
