package com.hcb.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * POST请求json入参解析过滤器
 * 将POST请求json格式入参存入request对象的parameters中<br>
 * Spring servlet过滤器实现<br>
 * @author huangcangbai
 *
 */
public class PostJsonParseFilter extends OncePerRequestFilter {
	
	private static Logger LOG = LoggerFactory.getLogger(PostJsonParseFilter.class);
	
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		LOG.info(this.getFilterName() + " initFilterBean");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOG.info(this.getFilterName() + " doFilterInternal");
		filterChain.doFilter(request, response);
	}

}

