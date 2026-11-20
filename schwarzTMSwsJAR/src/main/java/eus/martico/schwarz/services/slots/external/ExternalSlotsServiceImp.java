package eus.martico.schwarz.services.slots.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.martico.schwarz.dao.slots.external.ExternalSlotsDAOException;
import eus.martico.schwarz.dao.slots.external.ExternalSlotsIDAO;
import eus.martico.schwarz.model.dto.SlotDetailDTO;
import eus.martico.schwarz.model.dto.SlotSummaryDTO;

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
	

	@Override
	public List<SlotSummaryDTO> buscarTodosSlotsLibres(String _fechaDesde, String _fechaHasta) 
			throws ExternalSlotsServiceException {
		try {
			return externalSlotsDAO.getAllFreeTimeSlots(_fechaDesde, _fechaHasta);
		} catch (ExternalSlotsDAOException e) {
			throw new ExternalSlotsServiceException(e.getMessage());
		}
	}

	@Override
	public List<SlotDetailDTO> buscarDetalleSlotsPorFecha(String _rdc, String _fechaEntrega) 
			throws ExternalSlotsServiceException {
		try {
			return externalSlotsDAO.getSlotDetail(_rdc, _fechaEntrega);
		} catch (ExternalSlotsDAOException e) {
			throw new ExternalSlotsServiceException(e.getMessage());
		}
	}
}
