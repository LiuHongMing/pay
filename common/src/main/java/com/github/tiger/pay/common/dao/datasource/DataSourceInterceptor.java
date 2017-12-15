package com.github.tiger.pay.common.dao.datasource;

import com.alibaba.fastjson.JSON;
import com.github.tiger.pay.common.annotation.DataSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * 数据源配置拦截
 *
 * @author liuhongming
 */
public class DataSourceInterceptor implements MethodInterceptor {

    protected Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class declaringClass = method.getDeclaringClass();
        Object[] args = invocation.getArguments();
        DataSource ds = invocation.getMethod().getAnnotation(DataSource.class);
        if (!ObjectUtils.isEmpty(ds)) {
            DataSourceHolder.setDataSourcee(ds.name());
        }
        if (logger.isInfoEnabled()) {
            logger.info("DataSource：[{}], method：{}.{}, args：{}",
                    ds == null ? DataSource.SLAVE : ds.name(), declaringClass.getName(),
                    method.getName(), JSON.toJSONString(args));
        }
        return invocation.proceed();
    }

}
