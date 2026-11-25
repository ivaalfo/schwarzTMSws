package eus.martico.schwarz.dao.slots.external;

import java.util.List;

import eus.martico.schwarz.dao.slots.internal.InternalSlotsDAOException;
import eus.martico.schwarz.model.dto.SlotReserExternDTO;
import eus.martico.schwarz.model.dto.SlotReqExternDTO;


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
	List<SlotReqExternDTO> getAllFreeTimeSlots(String _dateFrom, String _dateTo) 
			throws ExternalSlotsDAOException;
	
    /**
	 * @param _rdc
	 * @param _deliveryDate
	 * 
	 * @return List<SlotDetailDTO>
	 * @throws InternalSlotsDAOException
	 */
    List<SlotReserExternDTO> getSlotDetail(String _rdc, String _deliveryDate) 
			throws ExternalSlotsDAOException;

}
