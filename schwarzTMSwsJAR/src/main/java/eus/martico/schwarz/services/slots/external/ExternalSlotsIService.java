package eus.martico.schwarz.services.slots.external;

import java.util.List;

import eus.martico.schwarz.model.dto.SlotDetailDTO;
import eus.martico.schwarz.model.dto.SlotSummaryDTO;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public interface ExternalSlotsIService {

	//XXX TO_USE NUEVO: si el pedido es EXTERNO a MARTICO (SQL_SELECT_ALL_TIME_SLOTS)
	List<SlotSummaryDTO> buscarTodosSlotsLibres(String _fechaDesde, String _fechaHasta) 
			throws ExternalSlotsServiceException;

	//XXX TO_USE NUEVO: si el pedido es EXTERNO a MARTICO (SQL_SELECT_RESERVA_DETALLE)
	List<SlotDetailDTO> buscarDetalleSlotsPorFecha(String _rdc, String _fechaEntrega) 
			throws ExternalSlotsServiceException;

}
