package eus.martico.schwarz.model.dto;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSBookLoadDTO {

	private String purchaseOrderNumber;
	private String deliveryNoteNumber;
	private String supplierName;
	private String targetCountry;
	private String targetRdc;
	private String targetName;
	private int amount;
	private String loadUnitType;


	//Getters y Setters...
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getDeliveryNoteNumber() {
		return deliveryNoteNumber;
	}
	public void setDeliveryNoteNumber(String deliveryNoteNumber) {
		this.deliveryNoteNumber = deliveryNoteNumber;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getTargetCountry() {
		return targetCountry;
	}
	public void setTargetCountry(String targetCountry) {
		this.targetCountry = targetCountry;
	}
	public String getTargetRdc() {
		return targetRdc;
	}
	public void setTargetRdc(String targetRdc) {
		this.targetRdc = targetRdc;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getLoadUnitType() {
		return loadUnitType;
	}
	public void setLoadUnitType(String loadUnitType) {
		this.loadUnitType = loadUnitType;
	}

}
