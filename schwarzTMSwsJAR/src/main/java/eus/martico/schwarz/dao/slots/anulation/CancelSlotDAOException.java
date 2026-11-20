package eus.martico.schwarz.dao.slots.anulation;

import eus.martico.schwarz.dao.DAOException;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class CancelSlotDAOException extends DAOException {

	public CancelSlotDAOException(DAOException daoException) {
		super(daoException);
	}
	
	public CancelSlotDAOException(String message) {
		super(message);
	}
	
	public CancelSlotDAOException(String codigoErr, String message) {
		super(codigoErr, message);
	}
}
