package com.lumiinsight.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.lumiinsight.bean.QueryResult;


public interface DAO<T> {
public String getSubClass();
	
	public Class<T> getEntityClass();
	
	/**
	 * paging record
	 * @param firstResult begin index, if value is -1, fetch all data
	 * @param maxReault record per page
	 * @param where condition language, without where key word, only use location param, 
	 * 			location param index value start 1，example, o.username=?1 and o.password=?2
	 * @param params param value in condition language
	 * @param orderby key is property，value is asc or desc, for instance:
	 * 	LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("email", "asc");
		orderby.put("password", "desc");
	 * @return 
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxReault, String where, Object[] params,
			LinkedHashMap<String, String> orderby);
	/**
	 * paging record
	 * @param firstResult begin index, if value is -1, fetch all data
	 * @param maxReault record per page
	 * @param where condition language, without where key word, only use location param, 
	 * 			location param index value start 1，example, o.username=?1 and o.password=?2
	 * @param params param value in condition language
	 * @return 
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxReault, String where, Object[] params);
	/**
	 * paging record
	 * @param firstResult begin index, if value is -1, fetch all data
	 * @param maxReault record per page
	 * @return 
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxReault);
	/**
	 * paging record
	 * @return 
	 */
	public QueryResult<T> getScrollData();
	/**
	 * paging record
	 * @param firstResult begin index, if value is -1, fetch all data
	 * @param maxReault record per page
	 * @param orderby key is property，value is asc or desc, for instance:
	 * 	LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("email", "asc");
		orderby.put("password", "desc");
	 * @return 
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxReault, LinkedHashMap<String, String> orderby);
	
	
	/**
	 * store entity
	 * @param entity entity object
	 */
	public void save(T entity);
	/**
	 * update entity
	 * @param entity entity object
	 */
	public void update(T entity);
	/**
	 * delete entity
	 * @param entityid entity identifier
	 */
	public void delete(Serializable... entityids);//JPA rule: entity id must complete serializtion
	/**
	 * delete entity
	 * @param entityClass  entity class
	 * @param entityid  entity id
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	/**
	 * delete entity
	 * @param entityClass  entity class
	 * @param entityids  entity id array
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	/**
	 * search entity
	 * @param entityid entity identifier
	 * @return entity object
	 */
	public T find(Serializable entityid);
	/**
	 * fetch entity total records
	 * @return total records
	 */
	public long getCount();
}
