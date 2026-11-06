package eus.martico.schwarz.dao.slots;

import eus.martico.schwarz.dao.DAOException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@SuppressWarnings("serial")
public class SlotsDAOException extends DAOException{

	public SlotsDAOException(DAOException daoException) {
		super(daoException);
	}
	public SlotsDAOException(String message) {
		super(message);
	}
	public SlotsDAOException(String codigoErr, String message) {
		super(codigoErr, message);
	}
}


