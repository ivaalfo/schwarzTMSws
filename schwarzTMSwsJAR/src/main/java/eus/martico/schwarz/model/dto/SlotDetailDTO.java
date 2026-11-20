package eus.martico.schwarz.model.dto;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class SlotDetailDTO {

	private String date;
	private int dock;      //Mapea 'muelle' (sunudc)
	private String startTime;
	private String endTime;
	
	//Getters y Setters de todos los campos...
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
