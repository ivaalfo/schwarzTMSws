package eus.martico.schwarz.dao.slots;

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
public interface SlotsIDAO {
	
	/**
	 * @param _rdc
	 * @param _orderNumber
	 * @param _fechaDesde
	 * @param _fechaHasta
	 * @return List<SlotDTO>
	 * @throws SlotsDAOException
	 */
    List<SlotDTO> getSlotsDisponibles(String _rdc, String _orderNumber, String _fechaDesde, String _fechaHasta) 
    		throws SlotsDAOException;

    
	/**
	 * @param _rdc
	 * @param _orderNo
	 * @param _deliveryDate
	 * @return String
	 * @throws SlotsDAOException
	 */
	String getReservaSlotReal(String _rdc, String _orderNo, String _deliveryDate) 
			throws SlotsDAOException;

	
	/**
	 * @param _rdc
	 * @param _orderNo
	 * @param _deliveryDate
	 * @return TSBookResponseDTO
	 */
	TSBookResponseDTO confirmarReservaFinalReal(String _rdc, String _orderNo, String _deliveryDate) 
			throws SlotsDAOException;
}
