package eus.martico.schwarz.seguridad.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import eus.martico.schwarz.seguridad.config.ConstantesSEG;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class ApiKeyAuthFilter extends GenericFilterBean {

	//El token estático acordado
	private static final String SECURITY_BEARER = ConstantesSEG.SECURITY_BEARER;
    private static final String VALID_TOKEN = ConstantesSEG.BEARER_TOKEN_TMS_WS_API;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //1.Leemos la cabecera estándar de autorización
        String authHeader = httpRequest.getHeader("Authorization");
        String apiKey = null;

        //2.Comprobamos que viene la cabecera y empieza por "Bearer " (ojo al espacio)
        if (authHeader != null && authHeader.startsWith(SECURITY_BEARER)) {
            apiKey = authHeader.substring(7); 	//Extraemos el token puro cortando "Bearer "
        }

        //3.Validamos el token estático
        if (VALID_TOKEN.equals(apiKey)) {
            //Token correcto: permitimos que continúe hacia el controlador
            chain.doFilter(request, response);
        } else {
            //Token incorrecto o ausente: devolvemos un 401 Unauthorized limpio en JSON
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("{\"status\":\"error\",\"mensaje\":\"Unauthorized - Invalid or missing Bearer Token\"}");
        }
    }
}
