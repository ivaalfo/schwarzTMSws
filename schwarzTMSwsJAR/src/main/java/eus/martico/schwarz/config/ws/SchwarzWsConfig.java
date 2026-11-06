package eus.martico.schwarz.config.ws;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eus.martico.schwarz.config.SchwarzConstantes;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class SchwarzWsConfig {

	protected static final Logger logger = LoggerFactory.getLogger(SchwarzWsConfig.class);
	
	private static PropertiesConfiguration applicationProperties = null;
	
	static {
		try {
			URL urlProperties = SchwarzWsConfig.class.getClassLoader().getResource(SchwarzConstantes.SCHWARZ_FICHERO_PROPERTIES);
			applicationProperties = new PropertiesConfiguration(urlProperties);			
		} catch (ConfigurationException e) {
			logger.error("Error inicializando SchwarzWsConfig: applicationProperties", e);
		}		
	}
	
	public static String getProperty(String _propertyName) {
		try {
			return applicationProperties.getString(_propertyName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static String[] getStringArray(String _propertyName) {
		try {
			return applicationProperties.getStringArray(_propertyName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static boolean getBooProperty(final String _propertyName, final boolean _default) {
		try {
			return applicationProperties.getBoolean(_propertyName);
		} catch (Exception e) {
			//logger.error(e.getMessage());
			return _default;
		}
	}
	
	public static Integer getIntProperty(String _propertyName) {
		try {
			return applicationProperties.getInt(_propertyName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static Long getLongProperty(String _propertyName) {
		try {
			return applicationProperties.getLong(_propertyName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
