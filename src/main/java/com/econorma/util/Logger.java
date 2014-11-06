package com.econorma.util;

import org.apache.log4j.Level;


public class Logger {

	private final org.apache.log4j.Logger logger;

	private Logger(Class<?> clazz) {
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}

	public static Logger getLogger(Class<?> clazz) {
		return new Logger(clazz);
	}
	
	public void trace(String message) {
		logger.trace(message);
	}

	public void info(String tag, String message) {
		logger.info("[" + tag + "] " + message);
	}

	public void debug(String tag, String message) {
		logger.debug("[" + tag + "] " + message);
	}
	
	public void debug(String tag, String message, Throwable e) {
		logger.debug("[" + tag + "] " + message,e);
	}

	public void error(String tag, String message) {
		logger.error("[" + tag + "] " + message);
	}
	
	public void error(String tag, Throwable e) {
		logger.error("[" + tag + "] ", e);
	}
	

	public void error(String tag, String message, Throwable e) {
		logger.error("[" + tag + "] " + message, e);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void fatal(String tag, String message, Exception e) {
		logger.fatal("[" + tag + "] " + message, e);
	}
	
	public void warn(String tag, String message, Exception e) {
		logger.warn("[" + tag + "] " + message, e);
	}
	
	public void warn(String tag, String message) {
		logger.warn("[" + tag + "] " + message);
	}

	public void log(Level level, String string) {
		logger.log(level, string);
	}
 
}
