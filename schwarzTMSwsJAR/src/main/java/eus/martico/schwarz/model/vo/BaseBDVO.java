package eus.martico.schwarz.model.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import eus.martico.schwarz.config.tmsConstantes;

/* Bean basico de base de datos */
public class BaseBDVO extends EntityVO {
	
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long userAlta;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long userBaja;	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern = tmsConstantes.JSON_DATETIME_FORMAT)
	private LocalDateTime fechaAlta;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern = tmsConstantes.JSON_DATETIME_FORMAT)
	private LocalDateTime fechaBaja;

	public BaseBDVO() {
		super();
	}
	public BaseBDVO(String _entidadTipo) {
		super(_entidadTipo);
	}
	public Long getUserAlta() {
		return userAlta;
	}
	public void setUserAlta(Long userAlta) {
		this.userAlta = userAlta;
	}
	public Long getUserBaja() {
		return userBaja;
	}
	public void setUserBaja(Long userBaja) {
		this.userBaja = userBaja;
	}
	@JsonFormat(pattern=tmsConstantes.JSON_DATETIME_FORMAT)
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean isBaja() {
		return (this.fechaBaja == null ? false : this.fechaBaja.compareTo(LocalDateTime.now())<=0);
	}

}
