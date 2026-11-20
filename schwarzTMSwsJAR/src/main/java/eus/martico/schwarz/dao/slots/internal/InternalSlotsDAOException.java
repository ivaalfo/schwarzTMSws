package eus.martico.schwarz.dao.slots.internal;

import eus.martico.schwarz.dao.DAOException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class InternalSlotsDAOException extends DAOException {

	public InternalSlotsDAOException(DAOException daoException) {
		super(daoException);
	}
	
	public InternalSlotsDAOException(String message) {
		super(message);
	}
	
	public InternalSlotsDAOException(String codigoErr, String message) {
		super(codigoErr, message);
	}
}


