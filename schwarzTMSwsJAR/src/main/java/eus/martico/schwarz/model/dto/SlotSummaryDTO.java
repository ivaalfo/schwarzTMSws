package eus.martico.schwarz.model.dto;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SlotSummaryDTO {
	
	private String recipientRDC;
    private String date;
    private int availableSlots;
    
    
    //Getters y Setters...
    public String getRecipientRDC() {
		return recipientRDC;
	}
	public void setRecipientRDC(String recipientRDC) {
		this.recipientRDC = recipientRDC;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(Integer freeSlots) {
		this.availableSlots = freeSlots;
	}
    
}
