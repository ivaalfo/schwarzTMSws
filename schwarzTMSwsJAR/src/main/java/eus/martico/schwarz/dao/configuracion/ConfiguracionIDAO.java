package eus.martico.schwarz.dao.configuracion;

import eus.martico.schwarz.model.configuracion.ConfiguracionVO;

public interface ConfiguracionIDAO {

	ConfiguracionVO getValor(String _codigo) throws ConfiguracionDAOException;
	int setValor(String _codigo, String _valor) throws ConfiguracionDAOException;
	
	int alta(String _codigo, String _valor) throws ConfiguracionDAOException;
	int modificar(String _codigo, String _valor) throws ConfiguracionDAOException;
}
