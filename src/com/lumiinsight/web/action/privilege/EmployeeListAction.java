package com.lumiinsight.web.action.privilege;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.annotation.Aspect;

import com.lumiinsight.bean.PageView;
import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.service.privilege.EmployeeService;
import com.lumiinsight.web.modeldriven.privilege.EmployeeModelDriven;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * employee paging list view
 * 
 * @author yangfan
 * 
 */
public class EmployeeListAction extends ActionSupport implements
		ModelDriven<EmployeeModelDriven>, ServletRequestAware {

	@Resource
	EmployeeService employeeService;

	EmployeeModelDriven employeeModelDriven = new EmployeeModelDriven();

	public EmployeeModelDriven getModel() {
		return employeeModelDriven;
	}

	private HttpServletRequest request;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Permission(module="employee", privilege="view")
	public String execute() throws Exception {
		PageView<Employee> pageView = new PageView<Employee>(12,
				employeeModelDriven.getPage());
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("realname", "desc");
		if ("true".equals(employeeModelDriven.getQuery())) {// 如果来自于查询界面
			StringBuilder jpql = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			if (employeeModelDriven.getUsername() != null
					&& !"".equals(employeeModelDriven.getUsername())) {
				params.add("%" + employeeModelDriven.getUsername().trim() + "%");
				jpql.append("o.username like ?").append(params.size());
			}
			if (employeeModelDriven.getRealname() != null
					&& !"".equals(employeeModelDriven.getRealname())) {
				if (!params.isEmpty())
					jpql.append(" and ");
				params.add("%" + employeeModelDriven.getRealname().trim() + "%");
				jpql.append("o.realname like ?").append(params.size());
			}
			if (employeeModelDriven.getBranchid() != null
					&& !"".equals(employeeModelDriven.getBranchid())) {
				if (!params.isEmpty())
					jpql.append(" and ");
				params.add(employeeModelDriven.getBranchid());
				jpql.append("o.branch.branchid =?").append(
						params.size());
			}
			pageView.setQueryResult(employeeService.getScrollData(
					pageView.getFirstResult(), pageView.getMaxresult(),
					jpql.toString(), params.toArray(), orderby));
		} else {
			pageView.setQueryResult(employeeService.getScrollData(
					pageView.getFirstResult(), pageView.getMaxresult(), orderby));
		}

		request.setAttribute("pageView", pageView);

		return "success";
	}
}