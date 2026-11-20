package eus.martico.schwarz.dao.slots.external;

import eus.martico.schwarz.dao.DAOException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class ExternalSlotsDAOException extends DAOException {

	public ExternalSlotsDAOException(DAOException daoException) {
		super(daoException);
	}
	
	public ExternalSlotsDAOException(String message) {
		super(message);
	}
	
	public ExternalSlotsDAOException(String codigoErr, String message) {
		super(codigoErr, message);
	}
}


