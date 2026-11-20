package eus.martico.schwarz.services.slots.internal;

import eus.martico.schwarz.services.MyServiceException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class InternalSlotsServiceException extends MyServiceException {

	public InternalSlotsServiceException(String _msgError) {
		super(_msgError);
	}

	public InternalSlotsServiceException(String string, Exception _ex) {
		super(_ex);
	}

	public InternalSlotsServiceException(String _codigoError, String _msgError) {
		super(_codigoError, _msgError);
	}

}

