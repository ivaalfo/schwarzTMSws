package eus.martico.schwarz.model.tms;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class SchwarzProcesoException extends Exception {
	
	
	public SchwarzProcesoException() {
		super();
	}
	
	public SchwarzProcesoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SchwarzProcesoException(Throwable cause) {
		super(cause);
	}
	
	public SchwarzProcesoException(String message) {
		super(message);
	}
	
}
