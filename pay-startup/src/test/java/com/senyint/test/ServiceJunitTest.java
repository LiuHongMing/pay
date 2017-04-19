package com.senyint.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/service-context.xml"})
public class ServiceJunitTest {

    public void print(Object result) throws Exception {
        System.out.println(JSON.toJSONString(result, SerializerFeature.PrettyFormat));
    }

}
