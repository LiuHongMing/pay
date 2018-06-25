package com.github.tiger.common.dao.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Intercepts({
        @Signature(type = Executor.class, method = "query",  args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class ExecutorInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorInterceptor.class);

    private void collect(long elasped, MappedStatement mappedStatement, Object parameterObject) {
        String id = mappedStatement.getId();
        String executeSql = mappedStatement.getBoundSql(parameterObject).getSql();
        String parameterString = JSON.toJSONString(parameterObject);
        if (logger.isInfoEnabled()) {
            logger.info("Elasped:{}(ms),[{}]\n{}\n{}",
                    TimeUnit.MILLISECONDS.toMillis(elasped), id, parameterString, executeSql);
        }
    }

    @Override
    public Object intercept(Invocation inv) throws Throwable {
        Object[] args = inv.getArgs();

        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];

        long start = DateTimeUtils.currentTimeMillis();
        Object ret = inv.proceed();
        long elasped = DateTimeUtils.currentTimeMillis() - start;
        collect(elasped, mappedStatement, parameterObject);

//        Object target = inv.getTarget();
//        Method method = inv.getMethod();
//        RowBounds rowBounds = null;
//        ResultHandler resultHandler = null;
//        if ("query".equals(method.getName())) {
//            rowBounds = (RowBounds) args[2];
//            resultHandler = (ResultHandler) args[3];
//        }

        return ret;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
