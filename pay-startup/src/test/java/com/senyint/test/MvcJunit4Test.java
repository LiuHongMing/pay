package com.senyint.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/mvc-context.xml" })
@WebAppConfiguration
public abstract class MvcJunit4Test {
	
	@Autowired
	protected WebApplicationContext webContext;
	protected MockMvc mockMvc;

	protected MockHttpServletRequestBuilder httpGet;
	protected MockHttpServletRequestBuilder httpPost;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	public void print(MockHttpServletRequestBuilder requestBuilder) throws Exception {
		MockHttpServletRequest request = requestBuilder.buildRequest(webContext.getServletContext());
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		try {
			System.out.println(JSON.toJSONString(JSON.parse(content), SerializerFeature.PrettyFormat));
		} catch (Exception e) {
			System.out.println(content);
		}
	}
}
