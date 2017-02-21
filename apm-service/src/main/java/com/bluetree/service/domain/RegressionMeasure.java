package com.bluetree.service.domain;

import com.bluetree.domain.ObjectSupport;

@SuppressWarnings("serial")
public class RegressionMeasure extends ObjectSupport {

    private Double decisionRatio;
    private Double alpha;
    private Double beta;
    private Double alphaBear;
    private Double betaBear;
    private Double alphaBull;
    private Double betaBull;

    public Double getDecisionRatio() {
	return decisionRatio;
    }

    public void setDecisionRatio(Double decisionRatio) {
	this.decisionRatio = decisionRatio;
    }

    public Double getAlpha() {
	return alpha;
    }

    public void setAlpha(Double alpha) {
	this.alpha = alpha;
    }

    public Double getBeta() {
	return beta;
    }

    public void setBeta(Double beta) {
	this.beta = beta;
    }

    public Double getAlphaBear() {
	return alphaBear;
    }

    public void setAlphaBear(Double alphaBear) {
	this.alphaBear = alphaBear;
    }

    public Double getBetaBear() {
	return betaBear;
    }

    public void setBetaBear(Double betaBear) {
	this.betaBear = betaBear;
    }

    public Double getAlphaBull() {
	return alphaBull;
    }

    public void setAlphaBull(Double alphaBull) {
	this.alphaBull = alphaBull;
    }

    public Double getBetaBull() {
	return betaBull;
    }

    public void setBetaBull(Double betaBull) {
	this.betaBull = betaBull;
    }

    @Override
    public String toString() {
	return msg(
		"decisionRatio={0,number,0.0000%},alpha={1,number,0.0000%},beta={2,number,0.0000},alphaBear={3,number,0.0000%},betaBear={4,number,0.0000},alphaBull={5,number,0.0000%},betaBull={6,number,0.0000}",
		decisionRatio, alpha, beta, alphaBear, betaBear, alphaBull,
		betaBull);
    }

}
