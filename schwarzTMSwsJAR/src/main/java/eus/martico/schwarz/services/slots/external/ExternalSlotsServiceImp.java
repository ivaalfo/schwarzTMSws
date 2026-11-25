package eus.martico.schwarz.services.slots.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.martico.schwarz.dao.slots.booking.BookingSlotsDAOException;
import eus.martico.schwarz.dao.slots.booking.BookingSlotsIDAO;
import eus.martico.schwarz.dao.slots.external.ExternalSlotsDAOException;
import eus.martico.schwarz.dao.slots.external.ExternalSlotsIDAO;
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
@Service(value = "ExternalSlotsIService")
public class ExternalSlotsServiceImp implements ExternalSlotsIService {
	
	@Autowired 
    private ExternalSlotsIDAO externalSlotsDAO;
	
	@Autowired 
    private BookingSlotsIDAO bookingSlotsDAO;
	

	@Override
	public List<SlotReqExternDTO> buscarTodosSlotsLibres(String _fechaDesde, String _fechaHasta) 
			throws ExternalSlotsServiceException {
		try {
			List<SlotReqExternDTO> listaLibres = externalSlotsDAO.getAllFreeTimeSlots(_fechaDesde, _fechaHasta);
		    return listaLibres; 
		} catch (ExternalSlotsDAOException e) {
			throw new ExternalSlotsServiceException(e.getMessage());
		}
	}

	@Override
	public List<SlotReserExternDTO> buscarDetalleSlotsPorFecha(String _rdc, String _fechaEntrega) 
			throws ExternalSlotsServiceException {
		try {
			List<SlotReserExternDTO> listaSlots = externalSlotsDAO.getSlotDetail(_rdc, _fechaEntrega);
			return listaSlots;
		} catch (ExternalSlotsDAOException e) {
			throw new ExternalSlotsServiceException(e.getMessage());
		}
	}
	
	@Override 
	@Transactional(rollbackFor = Exception.class)
	public TSBookResponseDTO confirmarReservaSlotExterna(TSBookDTO _requestData) 
			throws ExternalSlotsServiceException {
		try {
			String rdc = _requestData.getRecipientRDC();
	        String orderNo = _requestData.getOrderNumber();
	        String reqDeliveryDate = _requestData.getRequestedDeliveryDate();
	        Long reserveID = _requestData.getReservationId();

	        //1.Comprobamos si el slot existe y sigue libre en LDSEUS antes de tocar nada
	        boolean slotLibre = bookingSlotsDAO.isExternalBookingSlotFree(rdc, reqDeliveryDate, reserveID);
	        if (!slotLibre) {
	            return null; // El controller devolverá el JSON de error 30 ("Already taken or invalid")
	        }

	        //2.Ejecutamos los 3 UPDATES dentro de la misma transacción
	        //Si el pedido no existe en LDORCA/LDDESE o el slot no existe en LDSEUS, 
			//cada método lanza BookingSlotsDAOException y Spring hace ROLLBACK automático
	        bookingSlotsDAO.updateLdorca(_requestData);
	        bookingSlotsDAO.updateLddese(_requestData);
	        bookingSlotsDAO.updateLdseus(_requestData);

	        //3.Ahora que LDORCA, LDDESE y LDSEUS ya están enlazadas, recuperamos el DTO de respuesta
	        TSBookResponseDTO datosReserva = bookingSlotsDAO.getSlotBookInfo(rdc, orderNo, reqDeliveryDate, reserveID);

	        return datosReserva;
            
        } catch (BookingSlotsDAOException e) {
        	//Al relanzar Exception con rollbackFor = Exception.class, Spring deshace los UPDATES que se hubieran ejecutado
            throw new ExternalSlotsServiceException(e.getMessage(), e);
        }
	}
}
