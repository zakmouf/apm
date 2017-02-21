package com.bluetree.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluetree.dao.StockDao;
import com.bluetree.domain.Stock;
import com.bluetree.service.StockService;

@Service("stockService")
public class StockServiceImpl extends ServiceSupport implements StockService {

    @Resource
    private StockDao stockDao;

    @Override
    @Transactional
    public void create(Stock parent, List<Stock> children) {
	Stock existingParent = stockDao.findByName(parent.getName());
	if (existingParent != null) {
	    parent.setId(existingParent.getId());
	    logger.info(msg("update stock [{0}]", parent));
	    stockDao.update(parent);
	} else {
	    logger.info(msg("add stock [{0}]", parent));
	    stockDao.insert(parent);
	}
	List<Stock> existingChildren = stockDao.findChildren(parent);
	for (Stock existingChild : existingChildren) {
	    logger.info(msg("remove child [{0}] from parent [{1}]",
		    existingChild, parent));
	    stockDao.deleteRelation(parent, existingChild);
	}
	for (Stock child : children) {
	    Stock existingChild = stockDao.findByName(child.getName());
	    if (existingChild != null) {
		child.setId(existingChild.getId());
		logger.info(msg("update stock [{0}]", child));
		stockDao.update(child);
	    } else {
		logger.info(msg("add stock [{0}]", child));
		stockDao.insert(child);
	    }
	    logger.info(msg("add child [{0}] to parent [{1}]", child, parent));
	    stockDao.insertRelation(parent, child);
	}
    }

}
