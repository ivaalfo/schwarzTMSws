package eus.martico.schwarz.model.dto;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSReserveDTO {

	private String method;

	private String orderNumber;
	private String recipientCountry;
	private String recipientRDC;
	private String requestedDeliveryDate;
	private String requestedDeliveryTime;
	private String togId;
	private String transporterName;

	private Integer reservationHoursInAdvance;
	private String reservationType;
	private String palletCount;


	//Getters, Setters y Constructor...
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getRecipientCountry() {
		return recipientCountry;
	}

	public void setRecipientCountry(String recipientCountry) {
		this.recipientCountry = recipientCountry;
	}

	public String getRecipientRDC() {
		return recipientRDC;
	}

	public void setRecipientRDC(String recipientRDC) {
		this.recipientRDC = recipientRDC;
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

	public String getTogId() {
		return togId;
	}

	public void setTogId(String togId) {
		this.togId = togId;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public Integer getReservationHoursInAdvance() {
		return reservationHoursInAdvance;
	}

	public void setReservationHoursInAdvance(Integer reservationHoursInAdvance) {
		this.reservationHoursInAdvance = reservationHoursInAdvance;
	}

	public String getReservationType() {
		return reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	public String getPalletCount() {
		return palletCount;
	}

	public void setPalletCount(String palletCount) {
		this.palletCount = palletCount;
	}

}
