package eus.martico.schwarz.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.martico.schwarz.config.tmsConstantes;
import eus.martico.schwarz.config.ws.tmsWsConfig;
import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;
import eus.martico.schwarz.model.dto.TSRequestDTO;
import eus.martico.schwarz.model.dto.TSRequestResponseDTO;
import eus.martico.schwarz.model.dto.TSReserveDTO;
import eus.martico.schwarz.model.dto.TSReserveResponseDTO;
import eus.martico.schwarz.model.dto.anulation.TSDeleteDTO;
import eus.martico.schwarz.model.dto.anulation.TSDeleteResponseDTO;
import eus.martico.schwarz.model.tms.SchwarzProcesoException;
import eus.martico.schwarz.seguridad.timezone.DateTimeValidator;
import eus.martico.schwarz.services.slots.anulation.CancelSlotIService;
import eus.martico.schwarz.services.slots.external.ExternalSlotsIService;
import eus.martico.schwarz.services.slots.internal.InternalSlotsIService;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@RestController
@RequestMapping("/v1")
public class SchwarzControllerV1 {
	
	//Almacenes disponibles
	private static final List<Object> RDCS_VALIDOS = tmsWsConfig.getListString(tmsConstantes.AVAILABLE_RDCS);

	protected static final Logger logger = LoggerFactory.getLogger(SchwarzControllerV1.class);

	@Autowired
	private InternalSlotsIService internalSlotService;
	
	@Autowired
	private ExternalSlotsIService externalSlotService;
	
	@Autowired
	private CancelSlotIService cancelSlotService;


	@PostMapping("/tsRequest")
	public ResponseEntity<Object> requestTimeSlots(@RequestBody TSRequestDTO requestData) {
		try {
			//1.Validamos que el método invocado sea el correcto
			if (!"TSRequest".equalsIgnoreCase(requestData.getMethod())) {
				logger.info("TSRequest: orderNumber: {}. Method mismatch. Expected 'TSRequest'", requestData.getOrderNumber());
				Map<String, Object> errorBadMethod = new LinkedHashMap<>();
				errorBadMethod.put("Result", 30);
				errorBadMethod.put("Information", "Error");
				errorBadMethod.put("Message", "Method mismatch. Expected 'TSRequest'");
				return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
			}
			//2.Validamos que recipientRDC exista y esté en la lista permitida
	        String codigoAlmacen = requestData.getRecipientRDC() != null ? requestData.getRecipientRDC().trim() : "";
	        if (!RDCS_VALIDOS.contains(codigoAlmacen)) {
	        	logger.info("TSRequest: orderNumber: {}. Invalid recipientRDC: '{}'", requestData.getOrderNumber(), codigoAlmacen);
	            Map<String, Object> errorBadRdc = new LinkedHashMap<>();
	            errorBadRdc.put("Result", 30);
	            errorBadRdc.put("Information", "Error");
	            errorBadRdc.put("Message", "Invalid or missing recipientRDC");	//Allowed values: 1000, 1200, 3000, 4000, 6000
	            return new ResponseEntity<>(errorBadRdc, HttpStatus.BAD_REQUEST);
	        }
	        
			String numOrden = requestData.getOrderNumber();
			String deliveryDateFrom = requestData.getRequestedDeliveryDateFrom();
			String deliveryDateTo = requestData.getRequestedDeliveryDateTo();
			
			//3.Validamos formato y existencia real de ambas fechas (YYYY-MM-DD)
			if (!DateTimeValidator.esFechaValidaISO(deliveryDateFrom) || !DateTimeValidator.esFechaValidaISO(deliveryDateTo)) {
				logger.info("TSRequest: orderNumber: {}. Invalid dates: from='{}', to='{}'", numOrden, deliveryDateFrom, deliveryDateTo);
			    Map<String, Object> errorBadDate = new LinkedHashMap<>();
			    errorBadDate.put("Result", 30);
			    errorBadDate.put("Information", "Error");
			    errorBadDate.put("Message", "Invalid date format. Expected valid ISO date (YYYY-MM-DD)");
			    return new ResponseEntity<>(errorBadDate, HttpStatus.BAD_REQUEST);
			}
			//4.Validamos que la fecha fin no sea anterior a la fecha inicio
			if (!DateTimeValidator.esRangoFechasValido(deliveryDateFrom, deliveryDateTo)) {
				logger.info("TSRequest: orderNumber: {}. Invalid date range: from='{}', to='{}'", numOrden, deliveryDateFrom, deliveryDateTo);
			    Map<String, Object> errorDateRange = new LinkedHashMap<>();
			    errorDateRange.put("Result", 30);
			    errorDateRange.put("Information", "Error");
			    errorDateRange.put("Message", "RequestedDeliveryDateTo cannot be earlier than RequestedDeliveryDateFrom");
			    return new ResponseEntity<>(errorDateRange, HttpStatus.BAD_REQUEST);
			}
			
			//Si el pedido es INTERNO se devuelve una unica fecha posible que previamente se ha reservado de forma interna.
			List<SlotDTO> slotsLibres = internalSlotService.buscarSlotsPorFiltro(codigoAlmacen, numOrden, deliveryDateFrom, deliveryDateTo);
			//Si el pedido es EXTERNO se devuelve la lista de fechas y nº de huecos disponibles
			//TODO: (SQL_SELECT_ALL_TIME_SLOTS) -> externalSlotService.buscarTodosSlotsLibres();
			
			//5.Si no encuentra nada, disparamos el JSON de error controlado
			if (slotsLibres == null || slotsLibres.isEmpty()) {
				logger.info("TSRequest: orderNumber: {}. No free timeslots between those dates", requestData.getOrderNumber());
				Map<String, Object> errorNoSlots = new LinkedHashMap<>();
				errorNoSlots.put("Result", 30);
				errorNoSlots.put("Information", "Error");
				errorNoSlots.put("Message", "No free timeslots between those dates");
				return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
			}
			//6.Si todo va bien, montamos DTO de éxito
			TSRequestResponseDTO response = new TSRequestResponseDTO();
			response.setOrderNumber(numOrden);
			response.setRecipientCountry(requestData.getRecipientCountry());
			response.setRecipientRDC(codigoAlmacen);
			response.setAvailableSlots(slotsLibres);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("TSRequest: Internal Server Error 500: ", e);
			Map<String, Object> errorCritico = new LinkedHashMap<>();
			errorCritico.put("Result", 99);
			errorCritico.put("Information", "Critical Error");
			errorCritico.put("Message", e.toString());
			return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/tsReserve")
	public ResponseEntity<Object> reserveTimeSlots(@RequestBody TSReserveDTO requestData) {
		try {
			//1.Validamos que el método invocado sea el correcto
			if (!"TSReserve".equalsIgnoreCase(requestData.getMethod())) {
				logger.info("TSReserve: orderNumber: {}. Method mismatch. Expected 'TSReserve'", requestData.getOrderNumber());
				Map<String, Object> errorBadMethod = new LinkedHashMap<>();
				errorBadMethod.put("Result", 30);
				errorBadMethod.put("Information", "Error");
				errorBadMethod.put("Message", "Method mismatch. Expected 'TSReserve'");
				return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
			}
			//2.Validamos que recipientRDC exista y esté en la lista permitida
	        String codigoAlmacen = requestData.getRecipientRDC() != null ? requestData.getRecipientRDC().trim() : "";
	        if (!RDCS_VALIDOS.contains(codigoAlmacen)) {
	        	logger.info("TSReserve: orderNumber: {}. Invalid recipientRDC: '{}'", requestData.getOrderNumber(), codigoAlmacen);
	            Map<String, Object> errorBadRdc = new LinkedHashMap<>();
	            errorBadRdc.put("Result", 30);
	            errorBadRdc.put("Information", "Error");
	            errorBadRdc.put("Message", "Invalid or missing recipientRDC");	//Allowed values: 1000, 1200, 3000, 4000, 6000
	            return new ResponseEntity<>(errorBadRdc, HttpStatus.BAD_REQUEST);
	        }
	        
	        String numOrden = requestData.getOrderNumber();
	        String fechaEntrega = requestData.getRequestedDeliveryDate();
	        
	        //3.Validamos formato y existencia real de la fecha (YYYY-MM-DD)
			if (!DateTimeValidator.esFechaValidaISO(fechaEntrega)) {
				logger.info("TSReserve: orderNumber: {}. Invalid date format: '{}'", numOrden, fechaEntrega);
			    Map<String, Object> errorBadDate = new LinkedHashMap<>();
			    errorBadDate.put("Result", 30);
			    errorBadDate.put("Information", "Error");
			    errorBadDate.put("Message", "Invalid date format. Expected valid ISO date (YYYY-MM-DD)");
			    return new ResponseEntity<>(errorBadDate, HttpStatus.BAD_REQUEST);
			}
			//4.Validamos que la hora tenga un formato UTC válido (ej: 07:00:00.000Z)
			if (!DateTimeValidator.esHoraValidaUTC(requestData.getRequestedDeliveryTime())) {
				logger.info("TSReserve: orderNumber: {}. Invalid time format: '{}'", numOrden, requestData.getRequestedDeliveryTime());
			    Map<String, Object> errorBadTime = new LinkedHashMap<>();
			    errorBadTime.put("Result", 30);
			    errorBadTime.put("Information", "Error");
			    errorBadTime.put("Message", "Invalid time format. Expected ISO UTC format (07:00:00Z or 07:00:00.000Z)");
			    return new ResponseEntity<>(errorBadTime, HttpStatus.BAD_REQUEST);
			}
			
	        //Si es una reserva INTERNA devolvemos el ID de la reserva (suusky) que previamente y de forma interna se dió de alta.
	        Long idReserva = internalSlotService.buscarSlotReserva(codigoAlmacen, numOrden, fechaEntrega);
	        //Si es una reserva EXTERNA, como no estará previamente dada de alta, se retornan la lista de slots libres.
	        //TODO: (SQL_SELECT_ALL_TIME_SLOTS) -> externalSlotService.buscarDetalleSlotsPorFecha();
			
	        //5.Si no encuentra nada, disparamos el JSON de error controlado
	        if (idReserva == null || idReserva <= 0) {
	        	logger.info("TSReserve: orderNumber: {}. Cannot reserve this timeslot. Already taken or invalid.", requestData.getOrderNumber());
	            Map<String, Object> errorNoSlots = new LinkedHashMap<>();
	            errorNoSlots.put("Result", 30);
	            errorNoSlots.put("Information", "Error");
	            errorNoSlots.put("Message", "Cannot reserve this timeslot. Already taken or invalid.");
	            return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
	        }
	        //6.Si todo va bien, montamos DTO de éxito (Código de éxito "OK = 10")
	        TSReserveResponseDTO response = new TSReserveResponseDTO();
	        response.setResult(10);
	        response.setOrderNumber(numOrden);
	        response.setReservationId(idReserva);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	        
	    } catch (Exception e) {
	    	logger.error("TSReserve: Internal Server Error 500: ", e);
			Map<String, Object> errorCritico = new LinkedHashMap<>();
			errorCritico.put("Result", 99);
			errorCritico.put("Information", "Critical Error");
			errorCritico.put("Message", e.toString());
			return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@PostMapping("/tsBook")
	public ResponseEntity<Object> bookingTimeSlots(@RequestBody TSBookDTO requestData) {
	    try {
	    	//1.Validamos que el método invocado sea el correcto
	    	if (!"TSBook".equalsIgnoreCase(requestData.getMethod())) {
	            logger.info("TSBook: orderNumber: {}. Method mismatch. Expected 'TSBook'", requestData.getOrderNumber());
	            Map<String, Object> errorBadMethod = new LinkedHashMap<>();
	            errorBadMethod.put("Result", 30);
	            errorBadMethod.put("Information", "Error");
	            errorBadMethod.put("Message", "Method mismatch. Expected 'TSBook'");
	            return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
	        }
	    	//2.Validamos que recipientRDC exista y esté en la lista permitida
	        String codigoAlmacen = requestData.getRecipientRDC() != null ? requestData.getRecipientRDC().trim() : "";
	        if (!RDCS_VALIDOS.contains(codigoAlmacen)) {
	        	logger.info("TSBook: orderNumber: {}. Invalid recipientRDC: '{}'", requestData.getOrderNumber(), codigoAlmacen);
	            Map<String, Object> errorBadRdc = new LinkedHashMap<>();
	            errorBadRdc.put("Result", 30);
	            errorBadRdc.put("Information", "Error");
	            errorBadRdc.put("Message", "Invalid or missing recipientRDC");	//Allowed values: 1000, 1200, 3000, 4000, 6000
	            return new ResponseEntity<>(errorBadRdc, HttpStatus.BAD_REQUEST);
	        }
	        //3.Validamos formato y existencia real de la fecha (YYYY-MM-DD)
	        if (!DateTimeValidator.esFechaValidaISO(requestData.getRequestedDeliveryDate())) {
	            logger.info("TSBook: orderNumber: {}. Invalid date format: '{}'", requestData.getOrderNumber(), requestData.getRequestedDeliveryDate());
	            Map<String, Object> errorBadDate = new LinkedHashMap<>();
	            errorBadDate.put("Result", 30);
	            errorBadDate.put("Information", "Error");
	            errorBadDate.put("Message", "Invalid date format. Expected valid ISO date (YYYY-MM-DD)");
	            return new ResponseEntity<>(errorBadDate, HttpStatus.BAD_REQUEST);
	        }
	        //4.Validamos que la hora tenga un formato UTC válido (ej: 07:00:00.000Z)
	        if (!DateTimeValidator.esHoraValidaUTC(requestData.getRequestedDeliveryTime())) {
	            logger.info("TSBook: orderNumber: {}. Invalid time format: '{}'", requestData.getOrderNumber(), requestData.getRequestedDeliveryTime());
	            Map<String, Object> errorBadTime = new LinkedHashMap<>();
	            errorBadTime.put("Result", 30);
	            errorBadTime.put("Information", "Error");
	            errorBadTime.put("Message", "Invalid time format. Expected ISO UTC format (07:00:00Z or 07:00:00.000Z)");
	            return new ResponseEntity<>(errorBadTime, HttpStatus.BAD_REQUEST);
	        }
	        //5.Validamos que reservationId venga informado y sea positivo (DECIMAL 15,0 en BD)
	        if (requestData.getReservationId() <= 0) {
	            logger.info("TSBook: orderNumber: {}. Invalid or missing reservationId: '{}'", requestData.getOrderNumber(), requestData.getReservationId());
	            Map<String, Object> errorBadResId = new LinkedHashMap<>();
	            errorBadResId.put("Result", 30);
	            errorBadResId.put("Information", "Error");
	            errorBadResId.put("Message", "Invalid or missing reservationId.");
	            return new ResponseEntity<>(errorBadResId, HttpStatus.BAD_REQUEST);
	        }
	        
	        //El servicio buscará el slot correspondiente a ese codigoAlmacen, numOrden, fecha e idReserva'.
	        //Si lo encuentra lo registramos en nuestra BD. Y devolvemos un objetoDTO con los datos de confirmación.
	        TSBookResponseDTO resultadoBook = internalSlotService.confirmarReservaSlot(requestData);
			
	        //6.Si no se puede confirmar, disparamos el JSON de error controlado
	        if (resultadoBook == null) {
	        	logger.info("TSBook: orderNumber: {}. Cannot book this timeslot {}. Already taken or invalid", requestData.getOrderNumber(), requestData.getReservationId());
	            Map<String, Object> errorNoSlots = new LinkedHashMap<>();
	            errorNoSlots.put("Result", 30);
	            errorNoSlots.put("Information", "Error");
	            errorNoSlots.put("Message", "Cannot book this timeslot. Already taken or invalid.");
	            return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
	        }

	        //7.Si todo va bien, montamos DTO de éxito (Código de éxito "OK = 10")
	        resultadoBook.setResult(10);
	        return new ResponseEntity<>(resultadoBook, HttpStatus.OK);
	        
	    } catch (Exception e) {
	    	logger.error("TSBook: Internal Server Error 500: ", e);
	        Map<String, Object> errorCritico = new LinkedHashMap<>();
	        errorCritico.put("Result", 99);
	        errorCritico.put("Information", "Critical Error");
	        errorCritico.put("Message", e.toString());
	        return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@PostMapping("/tsDelete")
	public ResponseEntity<Object> anularReserva(@RequestBody TSDeleteDTO requestData) {
	    try {
	        //1.Validar método
	        if (!"TSDelete".equalsIgnoreCase(requestData.getMethod())) {
	            logger.info("TSDelete: orderNumber: {}. Method mismatch. Expected 'TSDelete'", requestData.getOrderNumber());
	            Map<String, Object> errorBadMethod = new LinkedHashMap<>();
	            errorBadMethod.put("Result", 30);
	            errorBadMethod.put("Information", "Error");
	            errorBadMethod.put("Message", "Method mismatch. Expected 'TSDelete'");
	            return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
	        }
	        //2.Validar recipientRDC
	        String codigoAlmacen = requestData.getRecipientRDC() != null ? requestData.getRecipientRDC().trim() : "";
	        if (!RDCS_VALIDOS.contains(codigoAlmacen)) {
	            logger.info("TSDelete: orderNumber: {}. Invalid recipientRDC: '{}'", requestData.getOrderNumber(), codigoAlmacen);
	            Map<String, Object> errorBadRdc = new LinkedHashMap<>();
	            errorBadRdc.put("Result", 30);
	            errorBadRdc.put("Information", "Error");
	            errorBadRdc.put("Message", "Invalid or missing recipientRDC");
	            return new ResponseEntity<>(errorBadRdc, HttpStatus.BAD_REQUEST);
	        }
	        //3.Validar fecha (YYYY-MM-DD)
	        if (!DateTimeValidator.esFechaValidaISO(requestData.getRequestedDeliveryDate())) {
	            logger.info("TSDelete: orderNumber: {}. Invalid date format: '{}'", requestData.getOrderNumber(), requestData.getRequestedDeliveryDate());
	            Map<String, Object> errorBadDate = new LinkedHashMap<>();
	            errorBadDate.put("Result", 30);
	            errorBadDate.put("Information", "Error");
	            errorBadDate.put("Message", "Invalid date format. Expected valid ISO date (YYYY-MM-DD)");
	            return new ResponseEntity<>(errorBadDate, HttpStatus.BAD_REQUEST);
	        }
	        //4.Validar reservationId
	        if (requestData.getReservationId() == null || requestData.getReservationId() <= 0) {
	            logger.info("TSDelete: orderNumber: {}. Invalid or missing reservationId: '{}'", requestData.getOrderNumber(), requestData.getReservationId());
	            Map<String, Object> errorBadResId = new LinkedHashMap<>();
	            errorBadResId.put("Result", 30);
	            errorBadResId.put("Information", "Error");
	            errorBadResId.put("Message", "Invalid or missing reservationId. Must be a positive numeric value");
	            return new ResponseEntity<>(errorBadResId, HttpStatus.BAD_REQUEST);
	        }
	        //5.Llamar al servicio transaccional
	        TSDeleteResponseDTO resultadoDelete = cancelSlotService.anularReservaSlot(requestData);
	      
	        //6.Si no se puede cancelar, disparamos el JSON de error controlado
	        if (resultadoDelete == null) {
	            logger.info("TSDelete: orderNumber: {}. Cannot cancel reservation {}. Not found or already cancelled.", requestData.getOrderNumber(), requestData.getReservationId());
	            Map<String, Object> errorNotFound = new LinkedHashMap<>();
	            errorNotFound.put("Result", 30);
	            errorNotFound.put("Information", "Error");
	            errorNotFound.put("Message", "Cannot cancel reservation. Not found or already cancelled.");
	            return new ResponseEntity<>(errorNotFound, HttpStatus.OK);
	        }
	        
	        //7.Si todo va bien, montamos DTO de éxito (Código de éxito "OK = 10")
	        resultadoDelete.setResult(10);
	        return new ResponseEntity<>(resultadoDelete, HttpStatus.OK);

	    } catch (Exception e) {
	        logger.error("TSDelete: Internal Server Error 500: ", e);
	        Map<String, Object> errorCritico = new LinkedHashMap<>();
	        errorCritico.put("Result", 99);
	        errorCritico.put("Information", "Critical Error");
	        errorCritico.put("Message", e.toString());
	        return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}	

	public ResponseEntity<String> handleException(SchwarzProcesoException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
