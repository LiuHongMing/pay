package com.github.tiger.test.springboot.event;

import org.springframework.context.ApplicationEvent;

public class ApplicationStartEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ApplicationStartEvent(Object source) {
		super(source);
	}

}
