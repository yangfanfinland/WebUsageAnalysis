package com.lumiinsight.web.action.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限配置
 * @author King
 *
 */
@Retention(RetentionPolicy.RUNTIME) //代表Permission注解保留在的阶段
@Target(ElementType.METHOD) //注解允许标注在什么位置上
public @interface Permission {
	/** 模块  **/
	String module();
	/** 权限值   **/
	String privilege();
}
