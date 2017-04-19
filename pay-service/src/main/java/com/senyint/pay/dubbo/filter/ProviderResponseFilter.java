package com.senyint.pay.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = Constants.PROVIDER)
public class ProviderResponseFilter implements Filter {

    protected Logger logger = LoggerFactory.getLogger(ProviderResponseFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        logger.info("{}({}),[{}]",
                inv.getMethodName(),
                JSON.toJSONString(inv.getArguments(), true),
                invoker.getUrl().toString());

        return invoker.invoke(inv);
    }

}
