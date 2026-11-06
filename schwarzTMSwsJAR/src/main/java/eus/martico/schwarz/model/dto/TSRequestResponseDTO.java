package eus.martico.schwarz.model.dto;

import java.util.List;



/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSRequestResponseDTO {
	
    private String orderNumber;
    private String recipientCountry;
    private String recipientRDC;
    private List<SlotDTO> availableSlots;
	

    //Getters, Setters y Constructor...
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
	public List<SlotDTO> getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(List<SlotDTO> availableSlots) {
		this.availableSlots = availableSlots;
	}
    
}
