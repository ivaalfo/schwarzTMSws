package eus.martico.schwarz.model.dto;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SlotDTO {

	private String recipientRDC;
	private String dateTime;
	private int togId;

	public SlotDTO() {}

	public SlotDTO(String recipientRDC, String dateTime, Integer togId) {
		this.recipientRDC = recipientRDC;
		this.dateTime = dateTime;
		this.togId = togId;
	}

	//Getters y Setters...
	
	public String getRecipientRDC() {
		return recipientRDC;
	}
	public void setRecipientRDC(String recipientRDC) {
		this.recipientRDC = recipientRDC;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getTogId() {
		return togId;
	}
	public void setTogId(Integer togId) {
		this.togId = togId;
	}

}
