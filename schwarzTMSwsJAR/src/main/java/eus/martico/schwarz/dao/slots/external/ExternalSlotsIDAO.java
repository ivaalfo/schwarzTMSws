package eus.martico.schwarz.dao.slots.external;

import java.util.List;

import eus.martico.schwarz.dao.slots.internal.InternalSlotsDAOException;
import eus.martico.schwarz.model.dto.SlotDetailDTO;
import eus.martico.schwarz.model.dto.SlotSummaryDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface ExternalSlotsIDAO {
	
    /**
	 * @param _dateFrom
	 * @param _dateTo
	 * 
	 * @return List<SlotSummaryDTO>
	 * @throws InternalSlotsDAOException
	 */
	List<SlotSummaryDTO> getAllFreeTimeSlots(String _dateFrom, String _dateTo) 
			throws ExternalSlotsDAOException;
	
    /**
	 * @param _deliveryDate
	 * @param _rdc
	 * 
	 * @return List<SlotDetailDTO>
	 * @throws InternalSlotsDAOException
	 */
    List<SlotDetailDTO> getSlotDetail(String _deliveryDate, String _rdc) 
			throws ExternalSlotsDAOException;

}
