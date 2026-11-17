package eus.martico.schwarz.model.dto;



/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSRequestDTO {

	private String method;

	private String orderNumber;
	private String recipientCountry;
	private String recipientRDC;
	private String requestedDeliveryDateFrom;
	private String requestedDeliveryDateTo;
	
	private int mainTogId;
	private int togId;
	private String transporterName;
	
	private int palletCountEP;

	//Getters y Setters...
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
	public String getRequestedDeliveryDateFrom() {
		return requestedDeliveryDateFrom;
	}
	public void setRequestedDeliveryDateFrom(String requestedDeliveryDateFrom) {
		this.requestedDeliveryDateFrom = requestedDeliveryDateFrom;
	}
	public String getRequestedDeliveryDateTo() {
		return requestedDeliveryDateTo;
	}
	public void setRequestedDeliveryDateTo(String requestedDeliveryDateTo) {
		this.requestedDeliveryDateTo = requestedDeliveryDateTo;
	}
	public Integer getMaintogId() {
		return mainTogId;
	}
	public void setMaintogId(Integer maintogId) {
		this.mainTogId = maintogId;
	}
	public Integer getTogId() {
		return togId;
	}
	public void setTogId(Integer togId) {
		this.togId = togId;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public Integer getPalletCountEP() {
		return palletCountEP;
	}
	public void setPalletCountEP(Integer palletCountEP) {
		this.palletCountEP = palletCountEP;
	}
	
}
