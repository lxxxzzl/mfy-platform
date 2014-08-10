package com.hcb.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 简单原生Servlet过滤器，仅供测试
 * @author huangcangbai
 */
public class SimpleServletFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("SimpleServletFilter init");
	}

	@Override
	public void destroy() {
		System.out.println("SimpleServletFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		System.out.println("SimpleServletFilter doFilter");
		filterChain.doFilter(request, response);
	}
	
}