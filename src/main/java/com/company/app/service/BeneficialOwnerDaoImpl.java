package com.company.app.service;

// Generated Feb 21, 2016 2:06:09 AM by Hibernate Tools 3.4.0.CR1

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

import com.company.app.domain.BeneficialOwner;

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
public class BeneficialOwnerDaoImpl {

	private static final Logger log = LoggerFactory.getLogger(BeneficialOwnerDaoImpl.class);

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

	public void persist(BeneficialOwner transientInstance) {
		log.debug("persisting BeneficialOwner instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(BeneficialOwner instance) {
		log.debug("attaching dirty BeneficialOwner instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BeneficialOwner instance) {
		log.debug("attaching clean BeneficialOwner instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BeneficialOwner persistentInstance) {
		log.debug("deleting BeneficialOwner instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BeneficialOwner merge(BeneficialOwner detachedInstance) {
		log.debug("merging BeneficialOwner instance");
		try {
			BeneficialOwner result = (BeneficialOwner) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BeneficialOwner findById(int id) {
		log.debug("getting BeneficialOwner instance with id: " + id);
		try {
			BeneficialOwner instance = (BeneficialOwner) sessionFactory
					.getCurrentSession().get(
							"com.company.app.domain.BeneficialOwner", id);
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

	public List<BeneficialOwner> findByExample(BeneficialOwner instance) {
		log.debug("finding BeneficialOwner instance by example");
		try {
			List<BeneficialOwner> results = (List<BeneficialOwner>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.company.app.domain.BeneficialOwner")
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
