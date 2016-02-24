package com.company.app.config;

import java.util.ArrayList;
import java.util.Iterator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * This class configures aspect logging on
 * all the public methods of certain packages.
 * Through aspect it allows to auto-log method's name,
 * input and output parameters; and execution time. It
 * leverages pretty-print to make logs more readable.
 * One can use @JsonIgnore on any property to hide it
 * from being logged. Ex: CreditCard number to achieve
 * PCI compliance. 
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@Aspect
@Component
@Order(value=2)
public class LoggingAspect { 

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	public static final String NEW_LINE = System.getProperty("line.separator");

	@Pointcut("execution(* com.company..controller..*.*(..))")
	private void controllers(){};

	@Pointcut("execution(* com.company..service..*.*(..))")
	private void services(){};

	@Around("controllers() || services()")
	public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable{

		Object retVal = null;
		String methodCall  = null;   

		try {
			ArrayList<String> logBody = new ArrayList<String>();
			logBody.add("CALLING METHOD : {");
			logBody.add("}");
			logBody.add(joinPoint.getSignature().getDeclaringTypeName());
			logBody.add(".");
			logBody.add(joinPoint.getSignature().getName());
			logBody.add(" With Input Paramas : ");
			logBody.add(NEW_LINE);

			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				logBody.add(getFormattedString(args[i]));
				logBody.add(NEW_LINE);
			}

			logBody.add(" END INPUT PARAMS ");
			logBody.add(NEW_LINE);
			logger.debug(getLogBody(logBody));
			logBody.clear();

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			retVal = joinPoint.proceed();

			stopWatch.stop();

			logBody.add("FINISHED METHOD {");
			logBody.add("}");
			logBody.add(joinPoint.getSignature().getDeclaringTypeName());
			logBody.add(".");
			logBody.add(joinPoint.getSignature().getName());
			logBody.add(NEW_LINE);
			logBody.add(" Return Value:");
			logBody.add(NEW_LINE);
			if(retVal !=null){
				logBody.add(getFormattedString(retVal));
			}
			logBody.add(NEW_LINE);
			logBody.add(" execution time: ");
			logBody.add(""+stopWatch.getTotalTimeMillis());
			logBody.add(" ms");
			logBody.add(NEW_LINE);

			logger.debug(getLogBody(logBody));
		} catch (Throwable ex) {
			StringBuilder esb = new StringBuilder();
			esb.append("EXCEPTION IN METHOD:");
			esb.append(methodCall).append(";");
			esb.append(ex);
			logger.error(esb.toString(), ex);
			throw ex;
		}
		return retVal;
	}
	public String getFormattedString(Object inputString){
		String response = "";
		if(inputString != null){
			try{
				response = OBJECT_MAPPER.writer()
						.withDefaultPrettyPrinter().writeValueAsString(inputString);
				return response;
			}catch(Exception ex){
				return inputString.toString();
			}finally{

			}
		}else{
			return "null";
		}
	}

	private String getLogBody(ArrayList<String> inputList){
		String response = "";
		Iterator<String> iterator = inputList.iterator();

		while(iterator.hasNext()){
			response = response.concat(iterator.next());
		}
		return response;
	}
}	