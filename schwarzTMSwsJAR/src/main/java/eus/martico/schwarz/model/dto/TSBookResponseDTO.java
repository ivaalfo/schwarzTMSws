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
    private int reservationId;
    
    
    //Getters y Setters...
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
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
	public Integer getReservationId() {
		return reservationId;
	}
	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}
	
}
