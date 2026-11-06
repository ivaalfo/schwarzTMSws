package eus.martico.schwarz.config;

import eus.martico.schwarz.config.ws.SchwarzWsConfig;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SchwarzConstantes {
	
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	public static final String SECURITY_BEARER = "Bearer ";
	
	public static final Long DEFAULT_USER_NI = 0L;
	
	public static final String SCHWARZ_FICHERO_PROPERTIES = "schwarzTMS.properties";
	
	public static final String JSON_DATE_FORMAT = "yyyyMMdd";
	public static final String JSON_DATETIME_FORMAT = "yyyyMMdd'T'HH:mm:ss";
	public static final String FORMAT_DATETIME_SCHWARZ = "yyyyMMddHHmm";
	public static final String FORMAT_DATETIME_SCHWARZ_EA = "yyyyMMddHHmmss";
	
	
	public static final String PROPERTY_KEY_CLAVE_DUPLICADA_STRINGS = "schwarz.clave.duplicada.strings";
	public static final String PROPERTY_KEY_DATA_TRUNCATION_STRINGS = "schwarz.data.truncation.key.strings";
	public static final String PROPERTY_KEY_VIOLATES_FOREIGN_KEY = "schwarz.violates.foreign.key.strings";
	
	
	public static final String PROPERTY_KEY_TOKEN_TMS_WS_API = "wsTMS.api.bearer.token";
	public static final String BEARER_TOKEN_TMS_WS_API = SchwarzWsConfig.getProperty(PROPERTY_KEY_TOKEN_TMS_WS_API);
	

}
