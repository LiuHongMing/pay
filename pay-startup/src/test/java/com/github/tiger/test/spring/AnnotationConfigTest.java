package com.github.tiger.test.spring;

import com.github.tiger.pay.web.config.DefaultConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class})
public class AnnotationConfigTest {

    @Resource
//    @Autowired
//    @Qualifier("systemProperties")
    public Properties systemProperties;

    public AnnotationConfigTest() {
        System.out.println("未初始化 systemProperties=" + systemProperties);
    }

    @Test
    public void testSystemProperties() {
        System.out.println("已初始化 systemProperties=" + systemProperties);
    }

}
