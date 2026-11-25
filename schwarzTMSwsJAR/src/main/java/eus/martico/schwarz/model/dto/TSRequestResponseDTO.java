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
    private List<?> availableSlots;
	

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
	public List<?> getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(List<?> availableSlots) {
		this.availableSlots = availableSlots;
	}
    
}
