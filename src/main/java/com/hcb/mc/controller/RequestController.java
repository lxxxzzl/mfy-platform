package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.bean.relation.AbstractBase;
import com.hcb.util.BeanUtil;

@Controller
@RequestMapping(value = "/request")
public class RequestController {

	private static Logger LOG = LoggerFactory.getLogger(RequestController.class);
	
	@Resource
	private BeanUtil beanUtil;
	
	@Resource
	private AbstractBase abstractBase;
	
    /**
     * 测试PostParserHttpServletRequestWrapper过滤器（解析post请求的消息体到request的parameter对象中）
     * http://localhost:8080/mfy-platform/request/json-parse
     * @param request
     * @param response
     */
    @RequestMapping(value="/json-parse", method =RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void jsonParse(HttpServletRequest request,HttpServletResponse response){
    	LOG.info("request.getParameterMap(): "+request.getParameterMap());
    	LOG.info("jsonParse execute ok.");
    }
	
}
