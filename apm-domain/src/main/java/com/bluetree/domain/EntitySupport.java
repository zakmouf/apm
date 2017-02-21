package com.bluetree.domain;

@SuppressWarnings("serial")
public abstract class EntitySupport extends ObjectSupport {

    protected Long id;

    public EntitySupport() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
