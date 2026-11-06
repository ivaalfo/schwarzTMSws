package eus.martico.schwarz.model.mensaje;

import com.fasterxml.jackson.annotation.JsonInclude;

import eus.martico.schwarz.model.vo.EntityVO;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mensaje extends EntityVO {
	
	private static final long serialVersionUID = 1L;
	//SERAN VALIDOS?
	private String tracking_mensaje;
	private String num_tracking;
	private String num_mensaje;
	private String tipo_mensaje;
	private String paisNifEmisor;
	private String paisNifReceptor;
	private String numero_documento;
	private String numero_mensaje;
	private String formato_mensaje;
	private String firmado;
	
	public String getTracking_mensaje() {
		return tracking_mensaje;
	}
	public void setTracking_mensaje(String tracking_mensaje) {
		this.tracking_mensaje = tracking_mensaje;
	}
	public String getNum_tracking() {
		return num_tracking;
	}
	public void setNum_tracking(String num_tracking) {
		this.num_tracking = num_tracking;
	}
	public String getNum_mensaje() {
		return num_mensaje;
	}
	public void setNum_mensaje(String num_mensaje) {
		this.num_mensaje = num_mensaje;
	}
	public String getTipo_mensaje() {
		return tipo_mensaje;
	}
	public void setTipo_mensaje(String tipo_mensaje) {
		this.tipo_mensaje = tipo_mensaje;
	}
	public String getPaisNifEmisor() {
		return paisNifEmisor;
	}
	public void setPaisNifEmisor(String paisNifEmisor) {
		this.paisNifEmisor = paisNifEmisor;
	}
	public String getPaisNifReceptor() {
		return paisNifReceptor;
	}
	public void setPaisNifReceptor(String paisNifReceptor) {
		this.paisNifReceptor = paisNifReceptor;
	}
	public String getNumero_documento() {
		return numero_documento;
	}
	public void setNumero_documento(String numero_documento) {
		this.numero_documento = numero_documento;
	}
	public String getNumero_mensaje() {
		return numero_mensaje;
	}
	public void setNumero_mensaje(String numero_mensaje) {
		this.numero_mensaje = numero_mensaje;
	}
	public String getFormato_mensaje() {
		return formato_mensaje;
	}
	public void setFormato_mensaje(String formato_mensaje) {
		this.formato_mensaje = formato_mensaje;
	}
	public String getFirmado() {
		return firmado;
	}
	public void setFirmado(String firmado) {
		this.firmado = firmado;
	}
	public Mensaje() {
		super();
	}
	public Mensaje(String _entidadTipo) {
		super(_entidadTipo);
	}
}
