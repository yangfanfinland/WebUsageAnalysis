package com.lumiinsight.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lumiinsight.bean.QueryResult;
import com.lumiinsight.utils.GenericsUtils;

@Transactional 
public abstract class DaoSupport<T> implements DAO<T>{
@PersistenceContext protected EntityManager em;//代理对象
	
	/* entity class*/
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	//protected Class<T> entityClass = getEntityClass();
	
	public String getSubClass(){
		//return super.getClass().getName(); //return com.unclewoo.service.user.impl.BuyerServiceBean
		if(getClass().isAnnotationPresent(Transactional.class)){
			//.....open transaction
		}
		return getClass().isAnnotationPresent(Transactional.class)+"";
	}
	public Class<T> getEntityClass(){
		Type parentType = getClass().getGenericSuperclass();
		if(parentType instanceof ParameterizedType){//measure whether is generic or not
			ParameterizedType ptype = (ParameterizedType)parentType;
			return (Class<T>)ptype.getActualTypeArguments()[0];
		}
		return null;
	}
	
	
	/**
	 * set query parameters
	 * @param query query object
	 * @param params parameter value
	 */
	public static void setQueryParameter(Query query, Object[] params){
		if(params!=null){
			for(int i=0; i<params.length; i++){
				query.setParameter(i+1, params[i]);
			}
		}
	}
	
	/**
	 * build sort language
	 * @param orderby Key is property,  Value is asc or desc
	 * @return
	 */
	//order by o.xxx desc, aaa asc,
	public static String buildOrderby(LinkedHashMap<String, String> orderby){
		StringBuffer sb = new StringBuffer();
		if(orderby!=null && !orderby.isEmpty()){
			sb.append(" order by ");
			for(Map.Entry<String, String> entry : orderby.entrySet()){
				sb.append("o.").append(entry.getKey()).append(" ").append(entry.getValue()).append(',');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	

	public QueryResult<T> getScrollData(int firstResult, int maxReault, String where, Object[] params,
			LinkedHashMap<String, String> orderby){
		String entityName = getEntityName(entityClass);
		
	
		
		
		String whereql = where!=null && !"".equals(where.trim()) ? " where "+ where : "" ;
		Query query = em.createQuery("select o from "+ entityName +" o" + whereql + buildOrderby(orderby));
		if(firstResult!=-1 && maxReault!=-1)
			query.setFirstResult(firstResult).setMaxResults(maxReault);
		setQueryParameter(query, params);
		QueryResult<T> qr = new QueryResult<T>();
		qr.setResultlist(query.getResultList());
		query = em.createQuery("select count(o) from "+ entityName +" o"+ whereql);
		setQueryParameter(query, params);
		qr.setTotalrecord((Long)query.getSingleResult());
		return qr;
	}
	public QueryResult<T> getScrollData(int firstResult, int maxReault, String where, Object[] params){
		return getScrollData(firstResult, maxReault, where, params, null);
	}
	public QueryResult<T> getScrollData(int firstResult, int maxReault){
		return getScrollData(firstResult, maxReault, null, null, null);
	}
	public QueryResult<T> getScrollData(){
		return getScrollData(-1, -1, null, null, null);
	}
	public QueryResult<T> getScrollData(int firstResult, int maxReault, LinkedHashMap<String, String> orderby){
		return getScrollData(firstResult, maxReault, null, null, orderby);
	}
	
	
	public void save(T entity) {
		em.persist(entity);
	}

	public void update(T entity) {
		em.merge(entity);
	}

	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		for(Object id : entityids){
			em.remove(em.getReference(entityClass, id));
		}
		
	}
	public <T> void delete(Class<T> entityClass, Object entityid) {
		delete(entityClass, new Object[]{entityid});
	}
	
	public void delete(Serializable... entityids){
		for(Serializable id: entityids)
			em.remove(em.getReference(entityClass, id));
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public T find(Serializable entityid) {
		return em.find(entityClass, entityid);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public long getCount() {
		return (Long)em.createQuery("select count(o) from "+ getEntityName(this.entityClass) +" o").getSingleResult();
	}
	
	/**
	 * fetch entity name
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> entityClass){
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name())){
			entityName = entity.name();
		}
		return entityName;
	}
}
