package eus.martico.schwarz.dao.slots.booking;

import eus.martico.schwarz.dao.DAOException;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
//@SuppressWarnings("serial")
public class BookingSlotsDAOException extends DAOException {
	
	private static final long serialVersionUID = 1L;

	public BookingSlotsDAOException(DAOException daoException) {
		super(daoException);
	}
	
	public BookingSlotsDAOException(String _msgError) {
		super(_msgError);
	}
	
	public BookingSlotsDAOException(String _string, Exception _ex) {
		super(_ex);
	}
	
	public BookingSlotsDAOException(String _codigoError, String _msgError) {
		super(_codigoError, _msgError);
	}

	
}


