package com.bluetree.service.domain;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.bluetree.service.ServiceTestSupport;

public class RandomizerTest extends ServiceTestSupport {

    @Test
    public void testRandomizer() {
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 5).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(10, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(20, 20).nextBasket()));
    }

}
