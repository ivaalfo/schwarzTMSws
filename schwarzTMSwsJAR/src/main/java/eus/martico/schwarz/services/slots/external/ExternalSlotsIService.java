package eus.martico.schwarz.services.slots.external;

import java.util.List;

import eus.martico.schwarz.model.dto.SlotReserExternDTO;
import eus.martico.schwarz.model.dto.SlotReqExternDTO;
import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface ExternalSlotsIService {

	/**
	 * @param _fechaDesde
	 * @param _fechaHasta
	 * @param deliveryDateTo 
	 * 
	 * @return List<SlotDTO>
	 * @throws ExternalSlotsServiceException
	 */
	List<SlotReqExternDTO> buscarTodosSlotsLibres(String _fechaDesde, String _fechaHasta)
			throws ExternalSlotsServiceException;

	/**
	 * @param _rdc
	 * @param _fechaEntrega
	 * 
	 * @return List<SlotDetailDTO>
	 * @throws ExternalSlotsServiceException
	 */
	List<SlotReserExternDTO> buscarDetalleSlotsPorFecha(String _rdc, String _fechaEntrega)
			throws ExternalSlotsServiceException;

	/**
	 * @param _requestData
	 * @param _isInterno
	 * 
	 * @return TSBookResponseDTO
	 * @throws ExternalSlotsServiceException 
	 */
	TSBookResponseDTO confirmarReservaSlotExterna(TSBookDTO _requestData)
			throws ExternalSlotsServiceException;

}
