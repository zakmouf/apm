package com.bluetree.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.dao.PriceDao;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.service.UpdateService;

@Service("updateService")
public class UpdateServiceImpl extends ServiceSupport implements UpdateService {

    @Value("${update.increment}")
    private Integer increment;

    @Value("#{new java.text.SimpleDateFormat('${update.start.date.pattern}').parse('${update.start.date}')}")
    private Date startDate;

    @Resource
    private PriceDao priceDao;

    @Override
    @Transactional
    public void update(List<Stock> stocks) {
	Date toDate = DateUtils.truncate(new Date(), Calendar.DATE);
	toDate = DateUtils.addDays(toDate, -1);
	for (Stock stock : stocks) {
	    Date fromDate = startDate;
	    Date lastDate = stock.getLastDate();
	    if (lastDate != null) {
		fromDate = DateUtils.addDays(lastDate, 1);
	    }
	    int update = updateInternal(stock, fromDate, toDate);
	    logger.info(msg(
		    "update [{0,number,0}] prices for stock [{1}] from [{2,date,yyyy-MM-dd}] to [{3,date,yyyy-MM-dd}]",
		    update, stock.getName(), fromDate, toDate));
	}
    }

    private int updateInternal(Stock stock, Date fromDate, Date toDate) {
	logger.debug(msg(
		"update increment [{0,number,0}] for stock [{1}] from [{2,date,yyyy-MM-dd}] to [{3,date,yyyy-MM-dd}]",
		increment, stock.getName(), fromDate, toDate));
	int update = 0;
	Calendar calendar = Calendar.getInstance();
	Date fromDateIncr = fromDate;
	calendar.setTime(fromDateIncr);
	while (fromDateIncr.compareTo(toDate) <= 0) {
	    calendar.add(Calendar.DATE, increment);
	    Date toDateIncr = calendar.getTime();
	    if (toDateIncr.compareTo(toDate) > 0) {
		toDateIncr = toDate;
	    }
	    List<Price> prices = priceDao.loadBetween(stock, fromDateIncr,
		    toDateIncr);
	    update += prices.size();
	    for (Price price : prices) {
		priceDao.insert(stock, price);
	    }
	    logger.debug(msg(
		    "update internal [{0,number,0}/{1,number,0}] prices from [{2,date,yyyy-MM-dd}] to [{3,date,yyyy-MM-dd}]",
		    prices.size(), update, fromDateIncr, toDateIncr));
	    calendar.add(Calendar.DATE, 1);
	    fromDateIncr = calendar.getTime();
	}
	return update;
    }

}
