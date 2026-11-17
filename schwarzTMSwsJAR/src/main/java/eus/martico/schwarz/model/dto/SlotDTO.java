package eus.martico.schwarz.model.dto;



/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SlotDTO {

	private String dateTime;
	private int togId;

	public SlotDTO() {}

	public SlotDTO(String dateTime, Integer togId) {
		this.dateTime = dateTime;
		this.togId = togId;
	}

	// Getters y Setters...
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
