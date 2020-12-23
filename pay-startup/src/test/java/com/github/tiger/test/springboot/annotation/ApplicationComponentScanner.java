package com.github.tiger.test.springboot.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ApplicationComponentScanner extends ClassPathBeanDefinitionScanner {

	public ApplicationComponentScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	protected void registerDefaultFilters() {
		this.addIncludeFilter(new AnnotationTypeFilter(ApplicationConfigure.class));
		super.registerDefaultFilters();
	}

}
