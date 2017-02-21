package com.bluetree.service.domain;

import java.util.Date;

import com.bluetree.domain.ObjectSupport;
import com.bluetree.domain.Stock;

public class PreviewConfig extends ObjectSupport {

    private static final long serialVersionUID = 1L;

    private Stock indice;
    private Date fromDate;
    private Date toDate;
    private Double riskFreeRate;
    private Integer minDateCount;
    private Integer minStockCount;
    private Integer basketSize;
    private Integer portfolioCount;

    public Stock getIndice() {
	return indice;
    }

    public void setIndice(Stock indice) {
	this.indice = indice;
    }

    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public Date getToDate() {
	return toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
    }

    public Integer getBasketSize() {
	return basketSize;
    }

    public void setBasketSize(Integer basketSize) {
	this.basketSize = basketSize;
    }

    public Integer getPortfolioCount() {
	return portfolioCount;
    }

    public void setPortfolioCount(Integer portfolioCount) {
	this.portfolioCount = portfolioCount;
    }

    public Integer getMinDateCount() {
	return minDateCount;
    }

    public void setMinDateCount(Integer minDateCount) {
	this.minDateCount = minDateCount;
    }

    public Integer getMinStockCount() {
	return minStockCount;
    }

    public void setMinStockCount(Integer minStockCount) {
	this.minStockCount = minStockCount;
    }

    public Double getRiskFreeRate() {
	return riskFreeRate;
    }

    public void setRiskFreeRate(Double riskFreeRate) {
	this.riskFreeRate = riskFreeRate;
    }
}
