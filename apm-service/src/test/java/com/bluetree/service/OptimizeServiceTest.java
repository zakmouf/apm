package com.bluetree.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluetree.dao.StockDao;
import com.bluetree.domain.Stock;
import com.bluetree.service.domain.OptimizeConfig;
import com.bluetree.service.domain.OptimizeResult;
import com.bluetree.service.domain.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class OptimizeServiceTest extends ServiceTestSupport {

    @Resource
    private StockDao stockDao;

    @Resource
    private OptimizeService service;

    @Test
    public void testService() throws ServiceException {

	Stock indice = stockDao.findByName("^GDAXI");
	Assert.assertNotNull(indice);

	OptimizeConfig config = new OptimizeConfig();
	config.setIndice(indice);
	config.setFromDate(parseDate("2008-01-01"));
	config.setToDate(parseDate("2009-12-31"));
	config.setRiskFreeRate(0.03);
	config.setMinDateCount(10);
	config.setMinStockCount(10);
	config.setBasketSize(5);

	config.setSuccessPortfolio(500);
	config.setMaxUnsuccessPortfolio(5000);
	config.setStartDecisionRatio(0.7);
	config.setMinBeta(0.8);
	config.setMaxBeta(1.0);
	config.setMinAlpha(0.0);
	config.setMaxAlpha(1.0);

	OptimizeResult result = service.process(config);

	// logger.debug("no;pf_mean;pf_stdev,beta");
	// int i = 1;
	// for (CombinedMeasure combinedMeasure : result.getCombinedMeasures())
	// {
	// logger.debug(msg(
	// "{0,number,0};{1,number,0.0000%};{2,number,0.0000%};{3,number,0.0000};{4,number,0.0000%}",
	// i++, combinedMeasure.getPortfolioMeasure()
	// .getAverageReturn(), combinedMeasure
	// .getPortfolioMeasure().getStandardDeviation(),
	// combinedMeasure.getRegressionMeasure().getBeta(),
	// combinedMeasure.getRegressionMeasure().getDecisionRatio()));
	// }

    }

}
