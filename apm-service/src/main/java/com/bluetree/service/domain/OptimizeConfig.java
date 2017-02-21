package com.bluetree.service.domain;

import java.util.Date;

import com.bluetree.domain.ObjectSupport;
import com.bluetree.domain.Stock;

public class OptimizeConfig extends ObjectSupport {

    private static final long serialVersionUID = 1L;

    private Stock indice;
    private Date fromDate;
    private Date toDate;
    private Double riskFreeRate;
    private Integer minDateCount;
    private Integer minStockCount;
    private Integer basketSize;
    private Integer successPortfolio;

    private Double startDecisionRatio;
    private Integer maxUnsuccessPortfolio;
    private Double minBeta;
    private Double maxBeta;
    private Double minAlpha;
    private Double maxAlpha;

    public Double getMaxAlpha() {
	return maxAlpha;
    }

    public void setMaxAlpha(Double maxAlpha) {
	this.maxAlpha = maxAlpha;
    }

    public Double getMinAlpha() {
	return minAlpha;
    }

    public void setMinAlpha(Double minAlpha) {
	this.minAlpha = minAlpha;
    }

    public Double getStartDecisionRatio() {
	return startDecisionRatio;
    }

    public void setStartDecisionRatio(Double startDecisionRatio) {
	this.startDecisionRatio = startDecisionRatio;
    }

    public Integer getMaxUnsuccessPortfolio() {
	return maxUnsuccessPortfolio;
    }

    public void setMaxUnsuccessPortfolio(Integer maxUnsuccessPortfolio) {
	this.maxUnsuccessPortfolio = maxUnsuccessPortfolio;
    }

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

    public Integer getSuccessPortfolio() {
	return successPortfolio;
    }

    public void setSuccessPortfolio(Integer successPortfolio) {
	this.successPortfolio = successPortfolio;
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

    public Double getMinBeta() {
	return minBeta;
    }

    public void setMinBeta(Double minBeta) {
	this.minBeta = minBeta;
    }

    public Double getMaxBeta() {
	return maxBeta;
    }

    public void setMaxBeta(Double maxBeta) {
	this.maxBeta = maxBeta;
    }

}
