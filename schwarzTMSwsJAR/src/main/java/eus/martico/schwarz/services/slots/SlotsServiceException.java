package eus.martico.schwarz.services.slots;

import eus.martico.schwarz.services.MyServiceException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class SlotsServiceException extends MyServiceException {

	public SlotsServiceException(String _msgError) {
		super(_msgError);
	}

	public SlotsServiceException(Exception _ex) {
		super(_ex);
	}

	public SlotsServiceException(String _codigoError, String _msgError) {
		super(_codigoError, _msgError);
	}

}

