package com.bluetree.domain;

@SuppressWarnings("serial")
public class Holding extends ObjectSupport implements Comparable<Holding> {

    private Double quantity;
    private Stock stock;

    public Holding() {

    }

    public Holding(Double quantity, Stock stock) {
	setQuantity(quantity);
	setStock(stock);
    }

    public Double getQuantity() {
	return quantity;
    }

    public void setQuantity(Double quantity) {
	this.quantity = quantity;
    }

    public Stock getStock() {
	return stock;
    }

    public void setStock(Stock stock) {
	this.stock = stock;
    }

    @Override
    public String toString() {
	return msg("quantity={0,number,0.00},stock=[{1}]", quantity, stock);
    }

    @Override
    public boolean equals(Object other) {
	return stock.equals(((Holding) other).getStock());
    }

    @Override
    public int compareTo(Holding other) {
	return stock.compareTo(other.getStock());
    }

    @Override
    public int hashCode() {
	return stock.hashCode();
    }

}
