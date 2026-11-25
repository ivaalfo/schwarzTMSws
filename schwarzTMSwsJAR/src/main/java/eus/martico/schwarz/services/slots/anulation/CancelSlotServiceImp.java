package eus.martico.schwarz.services.slots.anulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.martico.schwarz.dao.slots.anulation.CancelSlotDAOException;
import eus.martico.schwarz.dao.slots.anulation.CancelSlotIDAO;
import eus.martico.schwarz.model.dto.anulation.TSDeleteDTO;
import eus.martico.schwarz.model.dto.anulation.TSDeleteResponseDTO;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@Service(value = "CancelSlotIService")
public class CancelSlotServiceImp implements CancelSlotIService {
	
	@Autowired 
    private CancelSlotIDAO cancelSlotsDAO;
	
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TSDeleteResponseDTO anularReservaSlot(TSDeleteDTO _requestData) 
			throws CancelSlotServiceException {
		try {
	        String rdc = _requestData.getRecipientRDC().trim();
	        String orderNo = _requestData.getOrderNumber();
	        String reqDeliveryDate = _requestData.getRequestedDeliveryDate();
	        Long reserveId = _requestData.getReservationId();

	        //1.Comprobamos que la reserva existe y obtenemos su Reference
	        String reference = cancelSlotsDAO.getReferenceToCancel(rdc, orderNo, reqDeliveryDate, reserveId);
	        if (reference == null) {
	            return null;
	        }

	        //2.Ejecutamos los 3 UPDATES en orden dentro de la transacción JTA
	        cancelSlotsDAO.setCancelReservation(reserveId);

	        //3.Montamos DTO de respuesta
	        TSDeleteResponseDTO response = new TSDeleteResponseDTO();
	        response.setResult(10);
	        response.setReference(reference);
	        response.setReservationId(reserveId);
	        response.setOrderNumber(orderNo);
	        response.setRecipientCountry(_requestData.getRecipientCountry());
	        response.setRecipientRDC(rdc);
	        response.setRequestedDeliveryDate(reqDeliveryDate);
	        response.setMessage("Reservation successfully cancelled");

	        return response;

	    } catch (CancelSlotDAOException e) {
	        throw new CancelSlotServiceException(e.getMessage(), e);
	    }
	}
	
}
