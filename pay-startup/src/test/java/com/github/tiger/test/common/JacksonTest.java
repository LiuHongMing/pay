package com.github.tiger.test.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tiger.pay.web.vo.BaseVo;
import org.junit.Test;

import java.io.IOException;

public class JacksonTest {

    @Test
    public void testObjectMapper() throws IOException {
        BaseVo baseVo = new BaseVo();
        baseVo.setId("123456");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(baseVo);
        System.out.println(json);

        BaseVo baseVo1 = objectMapper.readValue(json, BaseVo.class);
        System.out.println("id: " + baseVo1.getId());
    }

}
