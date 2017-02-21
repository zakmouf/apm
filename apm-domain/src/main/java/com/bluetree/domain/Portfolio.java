package com.bluetree.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Portfolio extends EntitySupport implements Comparable<Portfolio> {

    private String name;
    private Date startDate;
    private Stock indice;
    private List<Holding> holdings = new ArrayList<Holding>();

    public Portfolio() {

    }

    public Portfolio(String name, Date startDate, Stock indice) {
	this.name = name;
	this.startDate = startDate;
	this.indice = indice;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Stock getIndice() {
	return indice;
    }

    public void setIndice(Stock indice) {
	this.indice = indice;
    }

    public List<Holding> getHoldings() {
	return holdings;
    }

    public void setHoldings(List<Holding> holdings) {
	this.holdings = holdings;
    }

    @Override
    public String toString() {
	return msg(
		"id={0,number,0},name={1},startDate={2,date,yyyy-MM-dd},indice=[{3}],holdings={4,number,0}",
		id, name, startDate, indice, holdings.size());
    }

    @Override
    public boolean equals(Object other) {
	return id.equals(((Portfolio) other).getId());
    }

    @Override
    public int compareTo(Portfolio other) {
	return id.compareTo(other.getId());
    }

    @Override
    public int hashCode() {
	return id.hashCode();
    }

}
