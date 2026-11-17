package eus.martico.schwarz.seguridad.config;

import eus.martico.schwarz.config.ws.SchwarzWsConfig;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class ConstantesSEG {
	
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	public static final String SECURITY_BEARER = "Bearer ";
	
	public static final String PROPERTY_KEY_TOKEN_TMS_WS_API = "wsTMS.api.bearer.token";
	public static final String BEARER_TOKEN_TMS_WS_API = SchwarzWsConfig.getProperty(PROPERTY_KEY_TOKEN_TMS_WS_API);
	
}
