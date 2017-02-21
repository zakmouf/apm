package com.bluetree.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bluetree.domain.Holding;

public class HoldingRowMapper extends RowMapperSupport implements
	RowMapper<Holding> {

    @Override
    public Holding mapRow(ResultSet rs, int rowNum) throws SQLException {
	Holding holding = mapHolding(rs);
	holding.setStock(mapStock(rs));
	return holding;
    }

}
