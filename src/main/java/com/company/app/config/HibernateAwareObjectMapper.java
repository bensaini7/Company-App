package com.company.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
/**
 * This class provides custom object-mapper for
 * Spring's JSON un/marshal-ing. Hibernate friendly 
 * object mapper avoids a need to look up lazy fetched
 * part of an object when one is being returnes as
 * @ResponseBody
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
public class HibernateAwareObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1L;

	public HibernateAwareObjectMapper() {
		registerModule(new Hibernate4Module());
	}
}
