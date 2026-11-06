package eus.martico.schwarz.model.dto;


/**
 * @author IvanAlfonsoAparicio
 *
 */
public class TSBookResponseDTO {
	
	private int result;
    private String orderNumber;
    private String reference;
    private String requestedDeliveryDate;
    private String requestedDeliveryTime;
    private String requestedDeliveryEndTime;
    private String reservationId;
    
    
    //Getters y Setters...
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
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public void setRequestedDeliveryDate(String requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public String getRequestedDeliveryTime() {
		return requestedDeliveryTime;
	}
	public void setRequestedDeliveryTime(String requestedDeliveryTime) {
		this.requestedDeliveryTime = requestedDeliveryTime;
	}
	public String getRequestedDeliveryEndTime() {
		return requestedDeliveryEndTime;
	}
	public void setRequestedDeliveryEndTime(String requestedDeliveryEndTime) {
		this.requestedDeliveryEndTime = requestedDeliveryEndTime;
	}
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	
}
