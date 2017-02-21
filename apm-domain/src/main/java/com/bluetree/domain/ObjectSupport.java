package com.bluetree.domain;

import java.io.Serializable;
import java.text.MessageFormat;

@SuppressWarnings("serial")
public abstract class ObjectSupport implements Serializable {

    public ObjectSupport() {

    }

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

}
