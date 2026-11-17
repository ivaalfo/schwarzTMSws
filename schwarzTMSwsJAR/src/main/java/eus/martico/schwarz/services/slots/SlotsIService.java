package eus.martico.schwarz.services.slots;

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
public interface SlotsIService {
	
	/**
	 * @param rdc
	 * @param orderNumber
	 * @param fechaDesde
	 * @param fechaHasta
	 * 
	 * @return List<SlotDTO>
	 * @throws SlotsServiceException
	 */
    List<SlotDTO> buscarSlotsPorFiltro(String rdc, String orderNumber, String fechaDesde, String fechaHasta) 
    		throws SlotsServiceException;

    
	/**
	 * @param rdc
	 * @param orderNo
	 * @param deliveryDate
	 * 
	 * @return Integer
	 * @throws SlotsServiceException
	 */
	Integer registrarReservaSlot(String rdc, String orderNo, String deliveryDate) 
			throws SlotsServiceException;

	
	/**
	 * @param requestData
	 * 
	 * @return TSBookResponseDTO
	 * @throws SlotsServiceException
	 */
	TSBookResponseDTO confirmarReservaFinal(TSBookDTO requestData) 
			throws SlotsServiceException;
	
}
