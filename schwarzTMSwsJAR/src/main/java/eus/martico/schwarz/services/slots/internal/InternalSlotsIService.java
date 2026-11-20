package eus.martico.schwarz.services.slots.internal;

import java.util.List;

import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface InternalSlotsIService {

	/**
	 * @param _rdc
	 * @param _orderNum
	 * @param _fechaDesde
	 * @param _fechaHasta
	 * 
	 * @return List<SlotDTO>
	 * @throws InternalSlotsServiceException
	 */
	List<SlotDTO> buscarSlotsPorFiltro(String _rdc, String _orderNum, String _fechaDesde, String _fechaHasta) 
			throws InternalSlotsServiceException;

	
	/**
	 * @param _rdc
	 * @param _orderNum
	 * @param _fechaEntrega
	 * 
	 * @return Long
	 * @throws InternalSlotsServiceException
	 */
	Long buscarSlotReserva(String _rdc, String _orderNum, String _fechaEntrega) 
			throws InternalSlotsServiceException;

	
	/**
	 * @param _requestData
	 * 
	 * @return TSBookResponseDTO
	 * @throws InternalSlotsServiceException
	 */
	TSBookResponseDTO confirmarReservaSlot(TSBookDTO _requestData) 
			throws InternalSlotsServiceException;
	
	
}
