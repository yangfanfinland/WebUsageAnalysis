package com.lumiinsight.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.utils.WebUtil;


public class PrivilegeFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Employee employee = WebUtil.getEmployee(request);
		
		if(employee == null){//如果员工没有登陆，重定向到员工登陆页面
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect("/WebUsageAnalysis2/employee/logon.action");
		}else{
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
