package eus.martico.schwarz.services.slots.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.martico.schwarz.dao.slots.booking.BookingSlotsDAOException;
import eus.martico.schwarz.dao.slots.booking.BookingSlotsIDAO;
import eus.martico.schwarz.dao.slots.internal.InternalSlotsDAOException;
import eus.martico.schwarz.dao.slots.internal.InternalSlotsIDAO;
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
@Service(value = "InternalSlotsIService")
public class InternalSlotsServiceImp implements InternalSlotsIService {

    @Autowired 
    private InternalSlotsIDAO internalSlotsDAO;
    
    @Autowired
    private BookingSlotsIDAO bookingSlotsDAO;
    
    
    
    @Override
    @Transactional(readOnly = true)
    public List<SlotDTO> buscarSlotsPorFiltro(String _rdc, String _orderNum, String _fechaDesde, String _fechaHasta) 
    		throws InternalSlotsServiceException {
        try {
            return internalSlotsDAO.getTimeSlot(_rdc, _orderNum, _fechaDesde, _fechaHasta);
        } catch (InternalSlotsDAOException e) {
            throw new InternalSlotsServiceException(e.getMessage());
        }
    }
    
	@Override
	public Long buscarSlotReserva(String _rdc, String _orderNum, String _fechaEntrega) 
			throws InternalSlotsServiceException {
		try {
			//Validamos la existencia de la reserva y recuperamos el ID.
	        return internalSlotsDAO.getSlotReserve(_rdc, _orderNum, _fechaEntrega);
		} catch (InternalSlotsDAOException e) {
            throw new InternalSlotsServiceException(e.getMessage());
        }
	}	

	@Override
	@Transactional(readOnly = true)
	public TSBookResponseDTO confirmarReservaSlotInterna(TSBookDTO _requestData) 
			throws InternalSlotsServiceException {
		try {
			String rdc = _requestData.getRecipientRDC();
			String orderNo = _requestData.getOrderNumber();
			String reqDeliveryDate = _requestData.getRequestedDeliveryDate();
			Long reserveID = _requestData.getReservationId();
			
			//1.Comprobamos si el slot existe y si es válido para confirmar
			TSBookResponseDTO datosReserva = bookingSlotsDAO.getSlotBookInfo(rdc, orderNo, reqDeliveryDate, reserveID);
            
			//Si no existe o ya estaba cogido
	        if (datosReserva == null) {
	            return null;
	        }
	        
	        return datosReserva;
            
        } catch (BookingSlotsDAOException e) {
            throw new InternalSlotsServiceException(e.getMessage(), e);
        }
	}
	
	
}