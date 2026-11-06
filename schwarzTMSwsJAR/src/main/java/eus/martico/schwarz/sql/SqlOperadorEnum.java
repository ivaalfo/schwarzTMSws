package eus.martico.schwarz.sql;

public enum SqlOperadorEnum {

	AND("AND"),
	OR("OR");
	
	private SqlOperadorEnum(String _nombre) {
		this.valor = _nombre;
	}

	private String valor;

	public String getValor() {
        return this.valor;
    }

}
