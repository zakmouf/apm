package com.bluetree.domain;

import java.util.Date;

@SuppressWarnings("serial")
public class Stock extends EntitySupport implements Comparable<Stock> {

    private String name;
    private String description;
    private Integer dateCount;
    private Date firstDate;
    private Date lastDate;

    public Stock() {

    }

    public Stock(String name) {
	setName(name);
    }

    public Stock(String name, String description) {
	setName(name);
	setDescription(description);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getDateCount() {
	return dateCount;
    }

    public void setDateCount(Integer dateCount) {
	this.dateCount = dateCount;
    }

    public Date getFirstDate() {
	return firstDate;
    }

    public void setFirstDate(Date firstDate) {
	this.firstDate = firstDate;
    }

    public Date getLastDate() {
	return lastDate;
    }

    public void setLastDate(Date lastDate) {
	this.lastDate = lastDate;
    }

    @Override
    public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append(msg("id={0,number,0}", id));
	buf.append(msg(",name={0}", name));
	buf.append(msg(",description=\"{0}\"", description));
	buf.append(msg(",dateCount={0,number,0}", dateCount));
	buf.append(msg(",firstDate={0,date,yyyy-MM-dd}", firstDate));
	buf.append(msg(",lastDate={0,date,yyyy-MM-dd}", lastDate));
	return buf.toString();
    }

    @Override
    public boolean equals(Object other) {
	return name.equals(((Stock) other).getName());
    }

    @Override
    public int compareTo(Stock other) {
	return name.compareTo(other.getName());
    }

    @Override
    public int hashCode() {
	return name.hashCode();
    }

}
