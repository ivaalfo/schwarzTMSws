package eus.martico.schwarz.model.vo;

public class EntidadVaciaVO extends EntityVO {
	
	private static final long serialVersionUID = 1L;

	public EntidadVaciaVO() {
		super("entidadVacia");
	}

	private String entidadvacia = "";
	
	public EntidadVaciaVO(String _nombreEntidad) {
		super(_nombreEntidad);
	}

	public String getEntidadvacia() {
		return entidadvacia;
	}

	public void setEntidadvacia(String entidadvacia) {
		this.entidadvacia = entidadvacia;
	}

}
