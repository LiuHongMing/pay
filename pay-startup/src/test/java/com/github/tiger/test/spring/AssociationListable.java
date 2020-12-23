package com.github.tiger.test.spring;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author liuhongming
 * @date 2020-05-21
 */
@Slf4j
@Component
@Import(AssociationAspect.class)
public class AssociationListable implements ApplicationContextAware, InitializingBean {

    private Map<ModuleTypeEnum,
            AssociationService> moduleAssociations = Maps.newConcurrentMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ListableBeanFactory listableBeanFactory = applicationContext;
        Map<String, Object> beanDefinitions = listableBeanFactory.getBeansWithAnnotation(Association.class);
        if (Objects.nonNull(beanDefinitions) && !beanDefinitions.isEmpty()) {
            beanDefinitions.forEach((beanName, beanObject) -> {
                Optional<ModuleTypeEnum[]> moduleTypes = Optional.of(AopUtils.getTargetClass(beanObject)
                        .getAnnotation(Association.class).moduleType());
                if (moduleTypes.isPresent()) {
                    Arrays.stream(moduleTypes.get())
                            .forEach(moduleType -> {
                                if (beanObject instanceof AssociationService) {
                                    moduleAssociations.put(moduleType,
                                            (AssociationService) beanObject);
                                }
                            });
                }
            });
        }
    }

    public AssociationService getAssociationService(ModuleTypeEnum moduleType) {
        return moduleAssociations.get(moduleType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (moduleAssociations.isEmpty()) {
            log.error("CampaignAssociationService实例Bean未定义！");
        }
    }

}
