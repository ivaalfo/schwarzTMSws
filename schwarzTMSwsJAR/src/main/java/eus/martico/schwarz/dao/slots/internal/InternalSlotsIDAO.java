package eus.martico.schwarz.dao.slots.internal;

import java.util.List;

import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface InternalSlotsIDAO {
	
	/**
	 * @param _rdc
	 * @param _orderNumber
	 * @param _dateFrom
	 * @param _dateTo
	 * 
	 * @return List<SlotDTO>
	 * @throws InternalSlotsDAOException
	 */
    List<SlotDTO> getTimeSlot(String _rdc, String _orderNumber, String _dateFrom, String _dateTo) 
    		throws InternalSlotsDAOException;

	/**
	 * @param _rdc
	 * @param _orderNo
	 * @param _deliveryDate
	 * 
	 * @return Long
	 * @throws InternalSlotsDAOException
	 */
    Long getSlotReserve(String _rdc, String _orderNo, String _deliveryDate) 
			throws InternalSlotsDAOException;
    
	/**
	 * @param _rdc
	 * @param _orderNum
	 * @param _deliveryDate
	 * @param _reserveId
	 * 
	 * @return TSBookResponseDTO
	 */
	TSBookResponseDTO getSlotBookInfo(String _rdc, String _orderNum, String _deliveryDate, Long _reserveId) 
			throws InternalSlotsDAOException;
	
	

}
