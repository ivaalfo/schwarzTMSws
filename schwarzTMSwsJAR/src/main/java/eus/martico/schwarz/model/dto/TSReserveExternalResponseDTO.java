package eus.martico.schwarz.model.dto;

import java.util.List;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class TSReserveExternalResponseDTO {

	private Integer result;
    private String orderNumber;
    private List<SlotReserExternDTO> availableSlots;
	
    //getters y setters
    public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public List<SlotReserExternDTO> getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(List<SlotReserExternDTO> availableSlots) {
		this.availableSlots = availableSlots;
	}
    
}
