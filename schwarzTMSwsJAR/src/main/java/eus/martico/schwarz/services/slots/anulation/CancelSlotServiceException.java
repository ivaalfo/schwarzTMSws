package eus.martico.schwarz.services.slots.anulation;

import eus.martico.schwarz.services.MyServiceException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class CancelSlotServiceException extends MyServiceException {

	public CancelSlotServiceException(String _msgError) {
		super(_msgError);
	}

	public CancelSlotServiceException(String string, Exception _ex) {
		super(_ex);
	}

	public CancelSlotServiceException(String _codigoError, String _msgError) {
		super(_codigoError, _msgError);
	}

}

