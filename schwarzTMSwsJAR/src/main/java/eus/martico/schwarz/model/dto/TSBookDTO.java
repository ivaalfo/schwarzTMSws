package eus.martico.schwarz.model.dto;

import java.util.List;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSBookDTO {

	private String method;
	
	private String orderNumber;
	private String recipientCountry;
	private String recipientRDC;
	private String requestedDeliveryDate;
	private String requestedDeliveryTime;
	
	private int togId;
	private List<TSBookLoadDTO> load; 	//Mapea el array JSON de la carga automáticamente
	private String transporterName;
	
	private long reservationId;
	private String licensePlate;
	
	private boolean tsStatusUpdate;
	private boolean goodsIn;
	private boolean tsPrintOut;
	
	private String issuer;
	private String uploadingDCT;
	private String uploadingDateDCT;
	private String uploadingTimeDCT;


	//Getters y Setters de todos los campos...
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
	public Integer getTogId() {
		return togId;
	}
	public void setTogId(Integer togId) {
		this.togId = togId;
	}
	public List<TSBookLoadDTO> getLoad() {
		return load;
	}
	public void setLoad(List<TSBookLoadDTO> load) {
		this.load = load;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public boolean getTsStatusUpdate() {
		return tsStatusUpdate;
	}
	public void setTsStatusUpdate(boolean tsStatusUpdate) {
		this.tsStatusUpdate = tsStatusUpdate;
	}
	public boolean getGoodsIn() {
		return goodsIn;
	}
	public void setGoodsIn(boolean goodsIn) {
		this.goodsIn = goodsIn;
	}
	public boolean getTsPrintOut() {
		return tsPrintOut;
	}
	public void setTsPrintOut(boolean tsPrintOut) {
		this.tsPrintOut = tsPrintOut;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getUploadingDCT() {
		return uploadingDCT;
	}
	public void setUploadingDCT(String uploadingDCT) {
		this.uploadingDCT = uploadingDCT;
	}
	public String getUploadingDateDCT() {
		return uploadingDateDCT;
	}
	public void setUploadingDateDCT(String uploadingDateDCC) {
		this.uploadingDateDCT = uploadingDateDCC;
	}
	public String getUploadingTimeDCT() {
		return uploadingTimeDCT;
	}
	public void setUploadingTimeDCT(String uploadingTimeDCT) {
		this.uploadingTimeDCT = uploadingTimeDCT;
	}


}
