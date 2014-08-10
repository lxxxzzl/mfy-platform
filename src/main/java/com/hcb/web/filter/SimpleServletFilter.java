package com.hcb.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 简单原生Servlet过滤器，仅供测试
 * @author huangcangbai
 */
public class SimpleServletFilter implements Filter {

	static Logger LOG = LoggerFactory.getLogger(SimpleServletFilter.class);
	
	private String filterName;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterName = filterConfig.getFilterName();
		LOG.info(this.filterName + " init");
	}

	@Override
	public void destroy() {
		LOG.info(this.filterName + " destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		//LOG.info(this.filterName + " doFilter");
		filterChain.doFilter(request, response);
	}
	
}