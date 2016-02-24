package com.company.app.service;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.domain.CompanyInfo;

/**
 * This is an auto generated DAO code by Hibernate
 * tools. Instead of EJB it has been exposed as Spring
 * service. Most generated methods are standard and not
 * modified.
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@Transactional
@Repository
public class CompanyInfoDaoImpl {

	private static final Logger log = LoggerFactory.getLogger(CompanyInfoDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CompanyInfo transientInstance) {
		log.debug("persisting CompanyInfo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyInfo instance) {
		log.debug("attaching dirty CompanyInfo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void update(CompanyInfo instance) {
		log.debug("attaching dirty CompanyInfo instance");
		try {
			sessionFactory.getCurrentSession().update(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyInfo instance) {
		log.debug("attaching clean CompanyInfo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CompanyInfo persistentInstance) {
		log.debug("deleting CompanyInfo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyInfo merge(CompanyInfo detachedInstance) {
		log.debug("merging CompanyInfo instance");
		try {
			CompanyInfo result = (CompanyInfo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CompanyInfo findById(int id) {
		log.debug("getting CompanyInfo instance with id: " + id);
		try {
			CompanyInfo instance = (CompanyInfo) sessionFactory
					.getCurrentSession().get(
							"com.company.app.domain.CompanyInfo", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CompanyInfo> findByExample(CompanyInfo instance) {
		log.debug("finding CompanyInfo instance by example");
		try {
			List<CompanyInfo> results = (List<CompanyInfo>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.company.app.domain.CompanyInfo")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
