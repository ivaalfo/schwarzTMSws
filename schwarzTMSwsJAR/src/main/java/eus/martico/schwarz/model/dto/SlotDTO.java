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
	private String togId;

	public SlotDTO() {}

	public SlotDTO(String dateTime, String togId) {
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
	public String getTogId() {
		return togId;
	}
	public void setTogId(String togId) {
		this.togId = togId;
	}

}
