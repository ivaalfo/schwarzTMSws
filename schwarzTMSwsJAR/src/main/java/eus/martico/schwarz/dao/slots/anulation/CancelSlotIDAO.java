package eus.martico.schwarz.dao.slots.anulation;

import eus.martico.schwarz.dao.slots.internal.InternalSlotsDAOException;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface CancelSlotIDAO {

	/**
	 * @param _rdc
	 * @param _orderNo
	 * @param _deliveryDate
	 * @param _reservationId
	 * 
	 * @return String
	 * @throws InternalSlotsDAOException
	 */
	String getReferenceParaAnular(String _rdc, String _orderNo, String _deliveryDate, Long _reservationId)
			throws CancelSlotDAOException;

	
	/**
	 * @param _reservationId
	 * @throws InternalSlotsDAOException
	 */
	void ejecutarAnulacionReserva(Long _reservationId) 
			throws CancelSlotDAOException;

}
