package com.senyint.test.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senyint.pay.web.vo.BaseVo;

public class JasksonTest {

    public void testObjectMapper() throws JsonProcessingException {
        BaseVo baseVo = new BaseVo();
        baseVo.setId("123456");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(baseVo));
    }

}
