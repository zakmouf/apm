package com.bluetree.dao;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DaoTestSupport {

    protected final Log logger = LogFactory.getLog(getClass());

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

    protected Date parseDate(String s) {
	try {
	    return new SimpleDateFormat("yyyy-MM-dd").parse(s);
	} catch (ParseException ex) {
	    throw new IllegalArgumentException(msg(
		    "failed to parse date [{0}]", s), ex);
	}
    }

}
