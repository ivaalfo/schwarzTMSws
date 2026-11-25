package eus.martico.schwarz.dao.slots.booking;

import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface BookingSlotsIDAO {
	
	/**
	 * @param _rdc
	 * @param _orderNum
	 * @param _deliveryDate
	 * @param _reserveId
	 * 
	 * @return TSBookResponseDTO
	 * @throws BookingSlotsDAOException 
	 */
	TSBookResponseDTO getSlotBookInfo(String _rdc, String _orderNum, String _deliveryDate, Long _reserveId) 
			throws BookingSlotsDAOException;
	
	/**
	 * @param _rdc
	 * @param _deliveryDate
	 * @param _reserveId
	 * 
	 * @return boolean
	 * @throws BookingSlotsDAOException
	 */
	boolean isExternalBookingSlotFree(String _rdc, String _deliveryDate, Long _reserveId) throws BookingSlotsDAOException;


	/**
	 * @param _requestData
	 * @throws BookingSlotsDAOException
	 */
	void updateLdorca(TSBookDTO _requestData) throws BookingSlotsDAOException;

	
	/**
	 * @param _requestData
	 * @throws BookingSlotsDAOException
	 */
	void updateLddese(TSBookDTO _requestData) throws BookingSlotsDAOException;

	
	/**
	 * @param _requestData
	 * @throws BookingSlotsDAOException
	 */
	void updateLdseus(TSBookDTO _requestData) throws BookingSlotsDAOException;
	
}
