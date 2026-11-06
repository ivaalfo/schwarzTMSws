package eus.martico.schwarz.dao.configuracion;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import eus.martico.schwarz.model.configuracion.ConfiguracionVO;
import eus.martico.schwarz.sql.SqlUtils;

public class ConfiguracionDAOImpMapper implements RowMapper<ConfiguracionVO> {

	@Override
	public ConfiguracionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConfiguracionVO config = new ConfiguracionVO();
		config.setCodigo(SqlUtils.getStringTrim(rs, ConfiguracionDAOImpCons.BD_CODIGO));
		config.setValor(SqlUtils.getStringTrim(rs, ConfiguracionDAOImpCons.BD_VALOR));
		return config;
	}

}
