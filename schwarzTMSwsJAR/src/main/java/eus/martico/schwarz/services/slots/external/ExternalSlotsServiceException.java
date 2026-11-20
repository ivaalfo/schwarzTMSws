package eus.martico.schwarz.services.slots.external;

import eus.martico.schwarz.services.MyServiceException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class ExternalSlotsServiceException extends MyServiceException {

	public ExternalSlotsServiceException(String _msgError) {
		super(_msgError);
	}

	public ExternalSlotsServiceException(String string, Exception _ex) {
		super(_ex);
	}

	public ExternalSlotsServiceException(String _codigoError, String _msgError) {
		super(_codigoError, _msgError);
	}

}

