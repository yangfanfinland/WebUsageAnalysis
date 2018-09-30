package com.lumiinsight.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 在普通类中获取request对象
 * @author yangfan
 *
 */
public class RequestFilter implements Filter{
	
	//创建线程
	public static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<HttpServletRequest>();

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		threadLocal.set((HttpServletRequest)arg0);
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
