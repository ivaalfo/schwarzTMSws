package eus.martico.schwarz.model.mensaje;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

import eus.martico.schwarz.model.vo.EntityVO;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensajeDTO extends EntityVO {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Mensaje> mensaje = new ArrayList<>();
	private String next_date;
	
	public ArrayList<Mensaje> getMensaje() {
		return mensaje;
	}
	public void setMensaje(ArrayList<Mensaje> mensaje) {
		this.mensaje = mensaje;
	}
	public String getNext_date() {
		return next_date;
	}
	public void setNext_date(String next_date) {
		this.next_date = next_date;
	}
	public MensajeDTO(ArrayList<Mensaje> mensaje, String next_date) {
		super();
		this.mensaje = mensaje;
		this.next_date = next_date;
	}
	public MensajeDTO() {
		super();
	}
	public MensajeDTO(String _entidadTipo) {
		super(_entidadTipo);
	}
}
