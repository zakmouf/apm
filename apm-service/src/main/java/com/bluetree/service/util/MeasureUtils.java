package com.bluetree.service.util;

import java.util.Iterator;
import java.util.List;

import com.bluetree.domain.Price;

public class MeasureUtils {

    public static double[] getReturns(List<Price> prices) {
	Iterator<Price> iterator = prices.iterator();
	Price lastPrice = iterator.next();
	double[] performances = new double[prices.size() - 1];
	int i = 0;
	while (iterator.hasNext()) {
	    Price price = iterator.next();
	    performances[i] = price.getValue() / lastPrice.getValue() - 1;
	    lastPrice = price;
	    i++;
	}
	return performances;
    }

    // sum(xi) / n
    public static double getMean(double[] xs) {
	int n = xs.length;
	double sum = 0.0;
	for (int i = 0; i < n; i++) {
	    sum = sum + xs[i];
	}
	return sum / n;
    }

    // sum((xi-mean)2) / (n-1)
    public static double getVariance(double[] xs) {
	int n = xs.length;
	double mean = getMean(xs);
	double sum = 0.0;
	for (int i = 0; i < n; i++) {
	    sum = sum + ((xs[i] - mean) * (xs[i] - mean));
	}
	return sum / (double) (n - 1);
    }

    // sqrt(var)
    public static double getStdev(double[] xs) {
	return Math.sqrt(getVariance(xs));
    }

    // (mean-riskless) / stdev
    public static double getSharpRatio(double[] xs, double riskFreeRate) {
	return (getMean(xs) - riskFreeRate) / getStdev(xs);
    }

    //
    public static double getDecisionRatio(double[] xs, double[] ys,
	    double riskFreeRate) {
	double indiceSharpRatio = getSharpRatio(xs, riskFreeRate);
	double portfolioSharpRatio = getSharpRatio(ys, riskFreeRate);
	if (indiceSharpRatio > 0) {
	    return portfolioSharpRatio
		    / (Math.abs(portfolioSharpRatio) + indiceSharpRatio);
	} else {
	    return (portfolioSharpRatio - 2 * indiceSharpRatio)
		    / (Math.abs(portfolioSharpRatio - 2 * indiceSharpRatio) - indiceSharpRatio);
	}
    }

    // n*sum(xi*yi) - sum(xi)*sum(yi) / n*sum(xi*xi) - sum(xi)*sum(xi)
    public static double getBeta(double[] xs, double[] ys) {
	int n = xs.length;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    sumxy += xs[i] * ys[i];
	    sumx += xs[i];
	    sumy += ys[i];
	    sumx2 += xs[i] * xs[i];
	}
	double beta = (n * sumxy - sumx * sumy) / (n * sumx2 - sumx * sumx);
	return beta;
    }

    // sum(yi)*sum(xi*xi) - sum(xi)*sum(xi*yi) / n*sum(xi*xi) - sum(xi)*sum(xi)
    public static double getAlpha(double[] xs, double[] ys) {
	int n = xs.length;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    sumxy += xs[i] * ys[i];
	    sumx += xs[i];
	    sumy += ys[i];
	    sumx2 += xs[i] * xs[i];
	}
	double alpha = (sumy * sumx2 - sumx * sumxy)
		/ (n * sumx2 - sumx * sumx);
	return alpha;
    }

    public static double getBetaBear(double[] xs, double[] ys) {
	int n = xs.length;
	int m = 0;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    if (xs[i] < 0.0) {
		sumxy += xs[i] * ys[i];
		sumx += xs[i];
		sumy += ys[i];
		sumx2 += xs[i] * xs[i];
		m++;
	    }
	}
	double beta = (m * sumxy - sumx * sumy) / (m * sumx2 - sumx * sumx);
	return beta;
    }

    public static double getAlphaBear(double[] xs, double[] ys) {
	int n = xs.length;
	int m = 0;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    if (xs[i] < 0.0) {
		sumxy += xs[i] * ys[i];
		sumx += xs[i];
		sumy += ys[i];
		sumx2 += xs[i] * xs[i];
		m++;
	    }
	}
	double alpha = (sumy * sumx2 - sumx * sumxy)
		/ (m * sumx2 - sumx * sumx);
	return alpha;
    }

    public static double getBetaBull(double[] xs, double[] ys) {
	int n = xs.length;
	int m = 0;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    if (xs[i] > 0.0) {
		sumxy += xs[i] * ys[i];
		sumx += xs[i];
		sumy += ys[i];
		sumx2 += xs[i] * xs[i];
		m++;
	    }
	}
	double beta = (m * sumxy - sumx * sumy) / (m * sumx2 - sumx * sumx);
	return beta;
    }

    public static double getAlphaBull(double[] xs, double[] ys) {
	int n = xs.length;
	int m = 0;
	double sumxy = 0.0;
	double sumx = 0.0;
	double sumy = 0.0;
	double sumx2 = 0.0;
	for (int i = 0; i < n; i++) {
	    if (xs[i] > 0.0) {
		sumxy += xs[i] * ys[i];
		sumx += xs[i];
		sumy += ys[i];
		sumx2 += xs[i] * xs[i];
		m++;
	    }
	}
	double alpha = (sumy * sumx2 - sumx * sumxy)
		/ (m * sumx2 - sumx * sumx);
	return alpha;
    }

}
