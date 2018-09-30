package com.lumiinsight.web.action.privilege;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.bean.privilege.PrivilegeGroup;
import com.lumiinsight.bean.privilege.SystemPrivilege;
import com.lumiinsight.bean.privilege.SystemPrivilegePK;
import com.lumiinsight.utils.WebUtil;
import com.lumiinsight.web.filter.RequestFilter;
import com.opensymphony.xwork2.ActionSupport;

@Aspect @Component
public class Interceptor{
	HttpServletRequest request = null;
	
	@Around("execution(java.lang.String com.lumiinsight.web.action..*.*()) && " +
			"!execution(java.lang.String com.lumiinsight.web.action..*.set*()) && " +
			"!execution(java.lang.String com.lumiinsight.web.action..*.get*())")
	public Object interceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		Object returnValue = null;
		request = RequestFilter.threadLocal.get();
		if(WebUtil.getRequestURI(request).startsWith(request.getContextPath()+"/control/")){
			
			//System.out.println("visit url address start with /control/");
			
			SystemPrivilege privilege = getMethodPermission(proceedingJoinPoint.getTarget().getClass()
					, proceedingJoinPoint.getSignature().getName());
				Employee employee = (Employee) request.getSession().getAttribute("employee");
				
				//System.out.println("Current employee's real name is : "+employee.getRealname());
				
				for(PrivilegeGroup group : employee.getGroups()){
					
					if(group.getPrivileges().contains(privilege)){
						//System.out.println("You have privilege to do this operation!");
						returnValue = proceedingJoinPoint.proceed();
						return returnValue;
					}
				}
				request.setAttribute("message", "you have no privilege to do this operation!");
				request.setAttribute("urladdress", "/control/center/right.action");
				System.out.println("You have no privilege to do this operation！");
				return "globalmessage";
		}
		//System.out.println("This operation url does not start with /control/, no interceptor !");
		returnValue = proceedingJoinPoint.proceed();
		return returnValue;
	}
	
	public SystemPrivilege getMethodPermission(Class clazz, String methodName) throws Exception{
		Method[] methods = clazz.getMethods();
		SystemPrivilege methodPrivilege = new SystemPrivilege();
		for(Method method : methods){
			if(methodName.equals(method.getName())){
				if(method.isAnnotationPresent(Permission.class)){
					Permission permission = method.getAnnotation(Permission.class);
					methodPrivilege = new SystemPrivilege(new SystemPrivilegePK(permission.module(), permission.privilege()));
				}	
				break;
			}
		}
		return methodPrivilege;
	}
	
}