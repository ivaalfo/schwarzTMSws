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
	private String togId;
	private List<TSBookLoadDTO> load; 	//Mapea el array JSON de la carga automáticamente
	private String transporterName;
	private String reservationId;
	private String licensePlate;
	private String tsStatusUpdate;
	private String goodsIn;
	private String tsPrintOut;
	private String issuer;
	private String uploadingDCT;
	private String uploadingDateDCC;
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
	public String getTogId() {
		return togId;
	}
	public void setTogId(String togId) {
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
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getTsStatusUpdate() {
		return tsStatusUpdate;
	}
	public void setTsStatusUpdate(String tsStatusUpdate) {
		this.tsStatusUpdate = tsStatusUpdate;
	}
	public String getGoodsIn() {
		return goodsIn;
	}
	public void setGoodsIn(String goodsIn) {
		this.goodsIn = goodsIn;
	}
	public String getTsPrintOut() {
		return tsPrintOut;
	}
	public void setTsPrintOut(String tsPrintOut) {
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
	public String getUploadingDateDCC() {
		return uploadingDateDCC;
	}
	public void setUploadingDateDCC(String uploadingDateDCC) {
		this.uploadingDateDCC = uploadingDateDCC;
	}
	public String getUploadingTimeDCT() {
		return uploadingTimeDCT;
	}
	public void setUploadingTimeDCT(String uploadingTimeDCT) {
		this.uploadingTimeDCT = uploadingTimeDCT;
	}


}
