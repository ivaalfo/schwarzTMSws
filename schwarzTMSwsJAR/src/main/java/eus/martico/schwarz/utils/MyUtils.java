package eus.martico.schwarz.utils;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import eus.martico.schwarz.sys.StoreProceduresConstantes;

/**
 * 
 * @author ialfonso :: Martico S.L.
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class MyUtils {

	public static String generarStringBusquedas(String cadena) {
		String limpio = null;
		if(cadena != null){
			String valor = cadena.trim();
			valor = valor.toUpperCase();
			// Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
			limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
			// Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
			//limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
			// Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
			//limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
		}
		return limpio;
	}

	public static String generarCadenaBusqueda(final String... _cadenas) {
		StringBuilder x = new StringBuilder();
		int i = x.length();
		for(String cadena: _cadenas){
			if(cadena != null){
				if(x.length() > i){
					x.append(' ');
				}
				i = x.length();
				x.append(generarStringBusquedas(cadena));
			}
		}
		return x.toString();
	}

	public static void gestionRespNumRowsPerPage(	HttpServletRequest _request, 
			HttpServletResponse _response, 
			String _cookiePerpage, 
			String _perpage,
			String _keyLista) {
		// Si ha llegado informado el campo perpage lo guardamos como cookie
		if(!StringUtils.isEmpty(_perpage) &&
				/* Si la cookie esta vacía o no son iguales */
				(StringUtils.isEmpty(_cookiePerpage) || !_perpage.equals(_cookiePerpage)) ) {
			Cookie cooPerpage = getCookie(_request, _keyLista);
			if(cooPerpage == null){
				cooPerpage = crearCookie(_keyLista, _perpage/*, 60 * 60 * 24 * 365 * 10, "/entralmidu"*/);
			} else {
				cooPerpage.setValue(_perpage);
			}
			_response.addCookie(cooPerpage);
		}

	}

	public static Cookie crearCookie(final String _nombreCookie, final String _cookieValue) {
		return crearCookie(_nombreCookie, _cookieValue, -1);
	}

	public static Cookie crearCookie(final String _nombreCookie, final String _cookieValue, final int _cookieExpiry) {
		return crearCookie(_nombreCookie, _cookieValue, _cookieExpiry, null);
	}

	public static Cookie crearCookie(final String _nombreCookie, final String _cookieValue, final int _cookieExpiry, final String cookiePath) {
		Cookie cookie = new Cookie(_nombreCookie, _cookieValue);
		if(!(_cookieExpiry < 0)){
			cookie.setMaxAge(_cookieExpiry);
		}
		if(!StringUtils.isEmpty(cookiePath)){
			cookie.setPath(cookiePath);
		}
		cookie.setHttpOnly(true);
		return cookie;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		if(request.getCookies() != null) {
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static byte parseOrdenDirection(final String _direction) {
		//Si no es positiva ASC entonces es DESC
		byte byOrden = -1;
		if(!StringUtils.isEmpty(_direction)) {
			if(_direction.equals("+")) {
				byOrden += 1;
			}
		}
		return byOrden;
	}

	public static BigDecimal aplicarPorcentaje(final BigDecimal _valor, final BigDecimal _porcentaje) {
		if(_valor == null){
			return null;
		}
		if(_porcentaje == null){
			return null;
		}
		return _valor.multiply(_porcentaje).divide(BigDecimal.valueOf(100.00));		
	}

	public static String ponerCampoStringBD(String valorCampo) {
		String valor = null;
		if(valorCampo != null && !valorCampo.trim().equalsIgnoreCase("")) {
			valor = "'" + valorCampo.trim().replaceAll("'", "''") + "'";
		}
		if(valorCampo != null) {
			if(!valorCampo.trim().equalsIgnoreCase("")){
				valor = "'" + valorCampo.trim().replaceAll("'", "''") + "'";
			} else {
				valor = "''";
			}
		}
		return valor;
	}

	public static String agregarCerosIzquierda(String string, int tamanio){
		if(string != null){
			String ceros = "";
			int cantidad = tamanio - string.length();
			if(cantidad >= 1){
				for(int i = 0; i < cantidad; i++){
					ceros += "0";
				}
				return (ceros + string);
			} else {
				return string;
			}
		} else {
			return "";
		}
	}

	public static String stringOrVacio(String string) {
		return string == null ? StoreProceduresConstantes.VACIO : string;
	}

	public static boolean isFechaInit400(Date fecha) {
		boolean correcto = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (!sdf.format(fecha).equals(StoreProceduresConstantes.FECHA_NULL_PA_Form)) {
			correcto = true;
		}
		return correcto;
	}

	public static boolean isStringInList(List<String> _lista, String _buscado) {
		Optional<String> codigo = null;
		if(_lista != null){
			codigo = _lista.stream().filter(cod->cod.equalsIgnoreCase(_buscado)).findFirst();
			return codigo.isPresent();
		} else {
			return false;
		}
	}

	public static  double doubleExcel(Long _entero) {
		if(_entero == null){
			return 0;
		} else {
			return _entero;
		}
	}

	public static  double doubleExcel(Short _entero) {
		if(_entero == null){
			return 0;
		} else { 
			return _entero;
		}
	}

	public static  double doubleExcel(Integer _entero) {
		if(_entero == null){
			return 0;
		} else {
			return _entero;
		}
	}

	public static String[] separarEnDosApellidos(final String _apellidos) {
		return separarEnDosApellidos( _apellidos, " ");
	}

	public static String[] separarEnDosApellidos(final String _apellidos, String _separador) {
		String[] resultado = new String[2];
		if(!StringUtils.isEmpty(_apellidos)){
			String[] apellidos = _apellidos.trim().split(_separador);
			if(apellidos.length > 1){
				List<String> lApe = new ArrayList<String>(Arrays.asList(apellidos));
				//Quitamos posibles caracteres raros
				Iterator<String> it = lApe.iterator();
				while(it.hasNext()){
					String s = it.next();
					if(StringUtils.isEmpty(s.trim())){
						it.remove();
					}
				}
				apellidos = lApe.stream().map(String :: trim).toArray(String[] :: new);
				if(apellidos != null){
					if(apellidos.length > 2){
						//Hay dos espacios cogemos el segundo como separador de los apellidos
						resultado[0] = apellidos[0] + _separador + apellidos[1];
						StringBuilder ape2 = new StringBuilder();
						for(int i=2; i < apellidos.length; i++){
							ape2.append(" ");
							ape2.append(apellidos[i]);
						}
						resultado[1] = ape2.toString().trim();
					} else if(apellidos.length > 1) {
						resultado[0] = apellidos[0];
						resultado[1] = apellidos[1];
					} else if(apellidos.length > 0) {
						resultado[0] = apellidos[0];
					}
				}
			} else {
				resultado[0] = apellidos[0];
			}
		}
		return resultado;
	}

	public static String quitarCaracterEspeciales(String _original) {
		if(!StringUtils.isEmpty(_original)) {
			String cadenaNormalize = Normalizer.normalize(_original, Normalizer.Form.NFD);   
			return cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
		} else {
			return _original;
		}
	}

	public static boolean arrayContainslong(long[] _ls, long _l) {
		if(_ls == null){
			return false;
		} else if(_ls.length == 0){
			return false;
		} else {
			for(long l: _ls){
				if(l == _l){
					return true;
				}
			}
			return false;
		}
	}

}
