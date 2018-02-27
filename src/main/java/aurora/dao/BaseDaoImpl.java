package aurora.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;



@Repository
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

    @Override
	public Serializable save(T o) {
		if (o != null) {
			 sessionFactory.getCurrentSession().persist(o);
			 return null;
		}
		return null;
	}

 
	public T getById(Class<T> c, Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(c, id);
	}
	
 
	public T getByHql(String hql) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

 
	public T getByHql(String hql, Map<String, Object> params) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

    @Override
	public void delete(T o) {
		if (o != null) {
			sessionFactory.getCurrentSession().delete(o);
		}
	}


 
	public void update(T o) {
		if (o != null) {
			sessionFactory.getCurrentSession().update(o);
		}
	}

 
	public void saveOrUpdate(T o) {
		if (o != null) {
			sessionFactory.getCurrentSession().saveOrUpdate(o);
		}
	}

 
	public List<T> find(String hql) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		return q.list();
	}

 
	public List<T> find(String hql, Map<String, Object> params) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

 
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

 
	public List<T> find(String hql, int page, int rows) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

 
	public Long count(String hql) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

 
	public Long count(String hql, Map<String, Object> params) {
		Query<T> q = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

 
	public int executeHql(String hql) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

 
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

 
	public List<Map> findBySql(String sql) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		return (List<Map>) q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

 
	public List<Map> findBySql(String sql, int page, int rows) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return (List<Map>) q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

 
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (List<Map>) q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

 
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (List<Map>) q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

 
	public int executeSql(String sql) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

 
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

 
	public BigInteger countBySql(String sql) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

 
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery<T> q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

 
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	public Session getSession(){
	    return this.getCurrentSession();
    }
}
