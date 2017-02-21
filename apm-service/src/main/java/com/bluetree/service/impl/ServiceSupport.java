package com.bluetree.service.impl;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ServiceSupport {

    protected final Log logger = LogFactory.getLog(getClass());

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

    public ServiceSupport() {
	logger.debug(msg("instantiate {0}", getClass()));
    }

}
