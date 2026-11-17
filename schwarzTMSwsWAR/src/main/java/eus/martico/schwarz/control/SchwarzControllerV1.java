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

import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;
import eus.martico.schwarz.model.dto.TSRequestDTO;
import eus.martico.schwarz.model.dto.TSRequestResponseDTO;
import eus.martico.schwarz.model.dto.TSReserveDTO;
import eus.martico.schwarz.model.dto.TSReserveResponseDTO;
import eus.martico.schwarz.model.tms.SchwarzProcesoException;
import eus.martico.schwarz.seguridad.timezone.DateTimeValidator;
import eus.martico.schwarz.services.slots.SlotsIService;


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
	
	protected static final Logger logger = LoggerFactory.getLogger(SchwarzControllerV1.class);

	@Autowired
	private SlotsIService iSlotService;


	@PostMapping("/tsRequest")
	public ResponseEntity<Object> consultarTimeSlots(@RequestBody TSRequestDTO requestData) {
		try {
			//Validamos que el método invocado sea el correcto
			if (!"TSRequest".equalsIgnoreCase(requestData.getMethod())) {
				Map<String, Object> errorBadMethod = new LinkedHashMap<>();
				errorBadMethod.put("Result", 30);
				errorBadMethod.put("Information", "Error");
				errorBadMethod.put("Message", "Method mismatch. Expected 'TSRequest'");
				return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
			}

			logger.info("Procesando TSRequest para la orden: {}", requestData.getOrderNumber());

			String recipienRDC = requestData.getRecipientRDC();
			String oNumber = requestData.getOrderNumber();
			String deliveryDateFrom = requestData.getRequestedDeliveryDateFrom();
			String deliveryDateTo = requestData.getRequestedDeliveryDateTo();
			//LLAMADA para buscar slots en nuestra BD
			List<SlotDTO> slotsLibres = iSlotService.buscarSlotsPorFiltro(recipienRDC, oNumber, deliveryDateFrom, deliveryDateTo);

			//Si el JAR no encuentra nada, disparamos el JSON de error controlado que pide su negocio
			if (slotsLibres == null || slotsLibres.isEmpty()) {
				Map<String, Object> errorNoSlots = new LinkedHashMap<>();
				errorNoSlots.put("Result", 30);
				errorNoSlots.put("Information", "Error");
				errorNoSlots.put("Message", "No free timeslots this day");
				
				//XXX Ellos esperan JSON de negocio incluso con HTTP 200 o el que acordemos
				return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
			}

			//Si todo va bien, montamos DTO de éxito
			TSRequestResponseDTO response = new TSRequestResponseDTO();
			response.setOrderNumber(oNumber);
			response.setRecipientCountry(requestData.getRecipientCountry());
			response.setRecipientRDC(recipienRDC);
			response.setAvailableSlots(slotsLibres);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error crítico en TSRequest: ", e);
			Map<String, Object> errorCritico = new LinkedHashMap<>();
			errorCritico.put("Result", 99);
			errorCritico.put("Information", "Critical Error");
			errorCritico.put("Message", e.getMessage());
			return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/tsReserve")
	public ResponseEntity<Object> reservarTimeSlots(@RequestBody TSReserveDTO requestData) {
		try {
			//Validamos que el método invocado sea el correcto
			if (!"TSReserve".equalsIgnoreCase(requestData.getMethod())) {
				Map<String, Object> errorBadMethod = new LinkedHashMap<>();
				errorBadMethod.put("Result", 30);
				errorBadMethod.put("Information", "Error");
				errorBadMethod.put("Message", "Method mismatch. Expected 'TSReserve'");
				return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
			}
			//Validamos que la hora tenga un formato UTC válido (ej: 07:00:00.000Z)
			if (!DateTimeValidator.esHoraValidaUTC(requestData.getRequestedDeliveryTime())) {
			    Map<String, Object> errorBadTime = new LinkedHashMap<>();
			    errorBadTime.put("Result", 30);
			    errorBadTime.put("Information", "Error");
			    errorBadTime.put("Message", "Invalid time format. Expected ISO UTC format (07:00:00Z or 07:00:00.000Z)");
			    return new ResponseEntity<>(errorBadTime, HttpStatus.BAD_REQUEST);
			}

			logger.info("Procesando TSReserve para la orden: {} en el muelle (togId): {}", 
					requestData.getOrderNumber(), requestData.getTogId());
			
			String rdc = requestData.getRecipientRDC();
	        String orderNo = requestData.getOrderNumber();
	        String deliveryDate = requestData.getRequestedDeliveryDate();
	        //String deliveryTime = requestData.getRequestedDeliveryTime();
	        //XXX No hace falta pues ya esta reservado previamente por salidas por lo que la hora coincidirá ???¿¿¿
	        //String togId = requestData.getTogId();
	        
	        //XXX LLAMADA: El servicio debe insertar/actualizar en AS400 y retornar el ID generado. Si falla, debería devolver null o vacío.
	        Integer idReservaGenerated = iSlotService.registrarReservaSlot(rdc, orderNo, deliveryDate);
			
	        //Control de errores del negocio
	        //Si no se ha podido realizar la reserva no se genera ID (porque el muelle se ha ocupado en ese instante, etc.)
	        if (idReservaGenerated == null || idReservaGenerated <= 0) {
	            Map<String, Object> errorNoSlots = new LinkedHashMap<>();
	            errorNoSlots.put("Result", 30);
	            errorNoSlots.put("Information", "Error");
	            errorNoSlots.put("Message", "Cannot reserve this timeslot. Already taken or invalid.");
	            return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
	        }

	        TSReserveResponseDTO response = new TSReserveResponseDTO();
	        response.setResult(10); 		//Código de éxito "OK = 10"
	        response.setOrderNumber(orderNo);
	        response.setReservationId(idReservaGenerated);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	        
	    } catch (Exception e) {
	        logger.error("Error crítico en TSReserve: ", e);
	        Map<String, Object> errorCritico = new LinkedHashMap<>();
	        errorCritico.put("Result", 99);
	        errorCritico.put("Information", "Critical Error");
	        errorCritico.put("Message", e.getMessage());
	        return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@PostMapping("/tsBook")
	public ResponseEntity<Object> confirmarReservaTimeSlots(@RequestBody TSBookDTO requestData) {
	    try {
	        if (!"TSBook".equalsIgnoreCase(requestData.getMethod())) {
	            Map<String, Object> errorBadMethod = new LinkedHashMap<>();
	            errorBadMethod.put("Result", 30);
	            errorBadMethod.put("Information", "Error");
	            errorBadMethod.put("Message", "Method mismatch. Expected 'TSBook'");
	            return new ResponseEntity<>(errorBadMethod, HttpStatus.BAD_REQUEST);
	        }
	        //Validamos que la hora tenga un formato UTC válido (ej: 07:00:00.000Z)
	        if (!DateTimeValidator.esHoraValidaUTC(requestData.getRequestedDeliveryTime())) {
	            Map<String, Object> errorBadTime = new LinkedHashMap<>();
	            errorBadTime.put("Result", 30);
	            errorBadTime.put("Information", "Error");
	            errorBadTime.put("Message", "Invalid time format. Expected ISO UTC format (07:00:00Z or 07:00:00.000Z)");
	            return new ResponseEntity<>(errorBadTime, HttpStatus.BAD_REQUEST);
	        }

	        logger.info("Confirmando TSBook definitivo para la orden: {} con matricula: {}", 
	                requestData.getOrderNumber(), requestData.getLicensePlate());
	        
	        //XXX LLAMADA: El servicio procesará la carga, ¿¿registrará la matrícula en el AS400??
	        //Deberá devolver un objeto DTO con los datos de confirmación y tiempos que genera la BD.
	        TSBookResponseDTO resultadoBook = iSlotService.confirmarReservaFinal(requestData);
			
	        if (resultadoBook == null) {
	            Map<String, Object> errorNoSlots = new LinkedHashMap<>();
	            errorNoSlots.put("Result", 30);
	            errorNoSlots.put("Information", "Error");
	            //errorNoSlots.put("Message", "No free timeslots this day");
	            errorNoSlots.put("Message", "Cannot book this timeslot. Already taken or invalid.");
	            return new ResponseEntity<>(errorNoSlots, HttpStatus.OK);
	        }

	        //Si todo va bien, inyectamos los códigos fijos y retornamos
	        resultadoBook.setResult(10); 	//Código de éxito "OK = 10"
	        return new ResponseEntity<>(resultadoBook, HttpStatus.OK);
	        
	    } catch (Exception e) {
	        logger.error("Error crítico en TSBook: ", e);
	        Map<String, Object> errorCritico = new LinkedHashMap<>();
	        errorCritico.put("Result", 99);
	        errorCritico.put("Information", "Critical Error");
	        errorCritico.put("Message", e.getMessage());
	        return new ResponseEntity<>(errorCritico, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//TODO NEXT
//	@PostMapping("/tsDelete")
//	public ResponseEntity<Object> anularReserva(@RequestBody TSDeleteDTO requestData){
//		
//	}
	

	public ResponseEntity<String> handleException(SchwarzProcesoException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
