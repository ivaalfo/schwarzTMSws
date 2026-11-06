package eus.martico.schwarz.model.dto;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSReserveResponseDTO {

	private int result;
	private String orderNumber;
	private String reservationId;
	
	
	//Getters, Setters y Constructor...
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	
}
