package eus.martico.schwarz.model.dto;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SlotReserExternDTO {

	private long reservationId;
	private String date;
	private int dock;
	private String startTime;
	private String endTime;
	
	//Getters y Setters de todos los campos...
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getDock() {
		return dock;
	}
	public void setDock(Integer dock) {
		this.dock = dock;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
