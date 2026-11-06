package eus.martico.schwarz.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import eus.martico.schwarz.model.vo.BaseBDVO;
import eus.martico.schwarz.sql.SqlUtils;


public abstract class BaseDAO<T> {

	protected JdbcTemplate jdbcTemplate;
	protected DataSource dataSource;

	protected BaseDAO() {
		super();
	}

	@javax.annotation.Resource()
	public void setDataSource(DataSource _marticoDS) {
		//this.dataSource = _marticoDS;
		this.jdbcTemplate = new JdbcTemplate(_marticoDS);
	}

	protected T findBase(String sqlSelect, RowMapper<T> rowMapper, long _ni) throws DataAccessException {
		List<T> datos = this.jdbcTemplate.query(sqlSelect, (PreparedStatementSetter) ps -> {
			int i = 1;
			ps.setLong(i++, _ni);
		}, rowMapper);
		return (datos.isEmpty() ? null : datos.get(0));

	}
	protected T findByIdBase(String sqlSelect, RowMapper<T> rowMapper, String id) throws DataAccessException {
		List<T> datos = this.jdbcTemplate.query(sqlSelect, (PreparedStatementSetter) ps -> {
			int i = 1;
			ps.setString(i++, id);
		}, rowMapper);
		return (datos.isEmpty() ? null : datos.get(0));

	}
	protected List<T> findByNiListaBase(String sqlSelect, RowMapper<T> rowMapper, long _ni) throws DataAccessException {
		List<T> datos = this.jdbcTemplate.query(sqlSelect, (PreparedStatementSetter) ps -> {
			int i = 1;
			ps.setLong(i++, _ni);
		}, rowMapper);
		return datos;

	}
	protected List<T> findByIdListaBase(String sqlSelect, RowMapper<T> rowMapper, String _id) throws DataAccessException {
		List<T> datos = this.jdbcTemplate.query(sqlSelect, (PreparedStatementSetter) ps -> {
			int i = 1;
			ps.setString(i++, _id);
		}, rowMapper);
		return datos;

	}

	protected Integer eliminarByNiBase(String sqlDelete, long _ni) throws DataAccessException {
		int x = this.jdbcTemplate.update(sqlDelete.toString(), (PreparedStatementSetter) ps -> {
			int i = 1;
			ps.setLong(i++, _ni);
		});
		return x;
	}

	public Long countBaseFiltro(String sqlSelectFiltro, DAOFiltro daoFiltro) {
		List<Long> lNumregs = this.jdbcTemplate.query(sqlSelectFiltro,
				(PreparedStatementSetter) ps -> daoFiltro.setFiltro(ps), (RowMapper<Long>) (rs, rowNum) -> {
					Long numregs = rs.getLong(1);
					return numregs;
				}
		);
		if (lNumregs != null && !lNumregs.isEmpty())
			return lNumregs.get(0);
		else
			return null;
	}

	public List<T> getListadoFiltroPaginacion(String sqlPaginacion, DAOFiltro daoFiltro, RowMapper<T> rowMapper) {
		List<T> lista = this.jdbcTemplate.query(sqlPaginacion, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				daoFiltro.setFiltro(ps);
			}
		}, rowMapper);
		return lista;
	}
 
	protected int bajaLogicaBase( String sqlUpdate, long ni, BaseBDVO baseBd) {
		int x = this.jdbcTemplate.update(sqlUpdate, (PreparedStatementSetter) ps -> {
			int i = 1;
			SqlUtils.setLocalDateTimeToPs(ps, i++, baseBd.getFechaBaja());
//			ps.setTimestamp(i++, baseBd.getFechaBaja()!=null?Timestamp.valueOf(baseBd.getFechaBaja()):null);
			ps.setLong(i++, baseBd.getUserBaja());
			ps.setLong(i++, ni);
		});
		return x;
	}
	protected int reactivarBase( String _sqlUpdate, final long _ni, BaseBDVO _baseBd) {
		int x = this.jdbcTemplate.update(_sqlUpdate, (PreparedStatementSetter) ps -> {
			int i = 1;
			SqlUtils.setLocalDateTimeToPs(ps, i++, null);
			ps.setLong(i++, _baseBd.getUserBaja());
			ps.setLong(i++, _ni);
		});
		return x;
	}
	protected String getCampoString(String sqlSelect, long _ni) throws DataAccessException {
		return this.jdbcTemplate.queryForObject(sqlSelect, new Object[]{_ni}, String.class);
	}
	protected Integer getCampoInteger(String sqlSelect, long _ni) throws DataAccessException {
		return this.jdbcTemplate.queryForObject(sqlSelect, new Object[]{_ni}, Integer.class);
	}
}
