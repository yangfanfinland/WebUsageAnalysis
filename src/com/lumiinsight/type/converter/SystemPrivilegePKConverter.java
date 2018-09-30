package com.lumiinsight.type.converter;

import java.util.Map;

import com.lumiinsight.bean.privilege.SystemPrivilegePK;

import ognl.DefaultTypeConverter;


public class SystemPrivilegePKConverter extends DefaultTypeConverter{

	@Override
	public Object convertValue(Map context, Object value, Class toType) {
		if(value instanceof SystemPrivilegePK){
			SystemPrivilegePK id = (SystemPrivilegePK)value;
			if(toType.equals(String.class))
				return id.getModule()+","+id.getPrivilege();
			else
				return value;
		}
		try{
			String param = (String)value;
			String[] arr = param.split(",");
			if(arr.length==2){
				return new SystemPrivilegePK(arr[0], arr[1]);
			}
		}catch(Exception e){}
		return null;
	}

}
