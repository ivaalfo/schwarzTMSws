package eus.martico.schwarz.services.slots;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.martico.schwarz.dao.slots.SlotsDAOException;
import eus.martico.schwarz.dao.slots.SlotsIDAO;
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
@Service(value = "SlotsIService")
public class SlotsServiceImp implements SlotsIService {

    @Autowired 
    private SlotsIDAO slotsDAO;

    @Override
    @Transactional(readOnly = true)
    public List<SlotDTO> buscarSlotsPorFiltro(String _rdc, String _orderNumber, String _fechaDesde, String _fechaHasta) 
    		throws SlotsServiceException {
        try {
            return slotsDAO.getSlotsDisponibles(_rdc, _orderNumber, _fechaDesde, _fechaHasta);
        } catch (SlotsDAOException e) {
            throw new SlotsServiceException(e.getMessage());
        }
    }
    
    
	@Override
	public String registrarReservaSlot(String _rdc, String _orderNo, String _deliveryDate) 
			throws SlotsServiceException {
		try {
			//Validamos la existencia de la reserva y recuperamos el ID.
	        return slotsDAO.getReservaSlotReal(_rdc, _orderNo, _deliveryDate);
		} catch (SlotsDAOException e) {
            throw new SlotsServiceException(e.getMessage());
        }
	}


	@Override
	public TSBookResponseDTO confirmarReservaFinal(TSBookDTO requestData) 
			throws SlotsServiceException {
		try {
			String _rdc = requestData.getRecipientRDC();
			String _orderNo = requestData.getOrderNumber();
			String _reqDeliveryDate = requestData.getRequestedDeliveryDate();
            return slotsDAO.confirmarReservaFinalReal(_rdc, _orderNo, _reqDeliveryDate);
        } catch (SlotsDAOException e) {
            throw new SlotsServiceException(e.getMessage());
        }
	}
	
}