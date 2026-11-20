package eus.martico.schwarz.services.slots.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(rollbackFor = Exception.class)
	public TSBookResponseDTO confirmarReservaSlot(TSBookDTO _requestData) 
			throws InternalSlotsServiceException {
		try {
			String rdc = _requestData.getRecipientRDC();
			String orderNo = _requestData.getOrderNumber();
			String reqDeliveryDate = _requestData.getRequestedDeliveryDate();
			Long reserveID = _requestData.getReservationId();
			
			
			//1.Comprobamos si el slot existe y si es válido para confirmar
			TSBookResponseDTO datosReserva = internalSlotsDAO.getSlotBookInfo(rdc, orderNo, reqDeliveryDate, reserveID);
            
			//Si no existe o ya estaba cogido, salimos sin tocar ninguna tabla
	        if (datosReserva == null) {
	            return null;
	        }

	        //TODO confirmar booking
	        //2.Si existe, ejecutamos los 3 UPDATES dentro de la misma transacción
//	        slotsDAO.actualizarLdorca(...);
//	        slotsDAO.actualizarLddese(...);
//	        slotsDAO.actualizarLdseus(...);

	        //3.Si los 3 UPDATES van bien, Spring hace COMMIT al salir y devolvemos el DTO
	        return datosReserva;
            
        } catch (InternalSlotsDAOException e) {
        	//Al relanzar Exception con rollbackFor = Exception.class, Spring deshace los UPDATES que se hubieran ejecutado
            throw new InternalSlotsServiceException(e.getMessage(), e);
        }
	}
	
	
}