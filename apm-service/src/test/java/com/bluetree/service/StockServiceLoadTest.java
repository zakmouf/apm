package com.bluetree.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluetree.domain.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
public class StockServiceLoadTest extends ServiceTestSupport {

    @Resource
    private StockService stockService;

    @Test
    public void testService() throws Exception {
	File file = new File("sample/DAX.TXT");
	Assert.assertTrue(file.exists());
	List<String> lines = FileUtils.readLines(file);
	Assert.assertTrue(lines.size() >= 1);
	Stock parent = null;
	List<Stock> children = new ArrayList<Stock>();
	for (String line : lines) {
	    String[] tokens = StringUtils.split(line, ';');
	    Assert.assertTrue(tokens.length >= 1);
	    Assert.assertTrue(tokens.length <= 2);
	    String name = StringUtils.trim(tokens[0]);
	    String description = null;
	    if (tokens.length == 2) {
		description = StringUtils.trim(tokens[1]);
	    }
	    if (parent == null) {
		parent = new Stock(name, description);
	    } else {
		Stock child = new Stock(name, description);
		children.add(child);
	    }
	}
	Assert.assertTrue(parent != null);
	stockService.create(parent, children);
    }

}
