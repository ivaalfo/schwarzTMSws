package eus.martico.schwarz.services.slots.anulation;

import eus.martico.schwarz.model.dto.anulation.TSDeleteDTO;
import eus.martico.schwarz.model.dto.anulation.TSDeleteResponseDTO;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface CancelSlotIService {

	/**
	 * @param _requestData
	 * 
	 * @return TSDeleteResponseDTO
	 * @throws CancelSlotServiceException
	 */
	TSDeleteResponseDTO anularReservaSlot(TSDeleteDTO _requestData) 
			throws CancelSlotServiceException;

}
