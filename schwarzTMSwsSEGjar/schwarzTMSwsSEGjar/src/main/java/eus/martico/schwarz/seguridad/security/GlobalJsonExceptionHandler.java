package eus.martico.schwarz.seguridad.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@RestControllerAdvice
public class GlobalJsonExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalJsonExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleJsonMalformado(HttpMessageNotReadableException ex) {
        logger.info("Error de sintaxis o tipos en el JSON recibido: {}", ex.getMostSpecificCause().getMessage());
        
        Map<String, Object> errorJson = new LinkedHashMap<>();
        errorJson.put("Result", 30);
        errorJson.put("Information", "Error");
        errorJson.put("Message", "Malformed JSON request or invalid data type");
        
        return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
    }
}
