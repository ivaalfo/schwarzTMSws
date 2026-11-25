package eus.martico.schwarz.dao.slots.anulation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import eus.martico.schwarz.dao.BaseGenDAO;
import eus.martico.schwarz.model.dto.SlotDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@Repository("cancelSlotsDAO")
public class CancelSlotDAOImp extends BaseGenDAO<SlotDTO> implements CancelSlotIDAO {

	//A.SELECT inicial: valida que la reserva existe para ese almacén/orden/fecha y saca la 'reference'
	private static final String SQL_SELECT_DELETE_INFO = 
			"SELECT TRIM(dodest) || '-' || CHAR(dodoky) AS reference " +
				"FROM ldseus, ldorca, lddese, ldalmx " +
				"WHERE TRIM(alxcmp4) = ? " +
					"AND sualky = alxlky " +
					"AND ocalky = sualky " +
					"AND doorky = ocorky " +
					"AND suorky = ocorky " +
					"AND ocorde = ? " +
					"AND sufech = ? " +
					"AND suusky = ? " +
					"FETCH FIRST 1 ROW ONLY";

	//B.1.UPDATE (LDDESE): lee SUDOKY de LDSEUS antes de que se ponga a 0
	private static final String SQL_UPDATE_DELETE_LDDESE = 
			"UPDATE LDDESE SET " +
				"doesta = '10', " +
				"doorky = 0, " +
				"dofecg = '01.01.0001', " +
				"dohocg = '', " +
				"domatr = '', " +
				"doremo = '', " +
				"dofeen = '01.01.0001', " +
				"doteen = '' " +
					"WHERE dodoky = (SELECT sudoky FROM ldseus WHERE suusky = ?) ";

	//B.2.UPDATE (LDORCA): lee SUORKY de LDSEUS antes de que se ponga a 0
	private static final String SQL_UPDATE_DELETE_LDORCA = 
			"UPDATE LDORCA SET " +
				"octeen = '', " +
				"ocesta = '00' " +
					"WHERE ocorky = (SELECT suorky FROM ldseus WHERE suusky = ?) ";

	//B.3.UPDATE (LDSEUS): ahora sí liberamos el slot poniendo claves a 0
	private static final String SQL_UPDATE_DELETE_LDSEUS = 
			"UPDATE LDSEUS SET " +
				"suesta = '00', " +
				"sudoky = 0, " +
				"suorky = 0 " +
					"WHERE suusky = ? ";
	
	
	@Override
	public String getReferenceToCancel(String _rdc, String _orderNo, String _deliveryDate, Long _reservationId) 
			throws CancelSlotDAOException {
	    try {
	        List<String> refs = jdbcTemplate.query(SQL_SELECT_DELETE_INFO,
	            new PreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps) throws SQLException {
	                    ps.setString(1, _rdc);
	                    ps.setString(2, _orderNo);
	                    ps.setDate(3, java.sql.Date.valueOf(_deliveryDate));
	                    ps.setLong(4, _reservationId);
	                }
	            },
	            (rs, rowNum) -> rs.getString("reference")
	        );
	        return refs.isEmpty() ? null : refs.get(0).trim();
	    } catch (DataAccessException e) {
	        throw new CancelSlotDAOException("Error al consultar la reserva para anular: " + e.getMessage());
	    }
	}
	
	
	@Override
	public void setCancelReservation(Long _reservationId) 
			throws CancelSlotDAOException {
	    try {
	        //IMPORTANTE: Mantener estrictamente este orden (1. LDDESE -> 2. LDORCA -> 3. LDSEUS)
	        jdbcTemplate.update(SQL_UPDATE_DELETE_LDDESE, _reservationId);
	        jdbcTemplate.update(SQL_UPDATE_DELETE_LDORCA, _reservationId);
	        jdbcTemplate.update(SQL_UPDATE_DELETE_LDSEUS, _reservationId);
	    } catch (DataAccessException e) {
	        throw new CancelSlotDAOException("Error ejecutando los UPDATE de anulación: " + e.getMessage());
	    }
	}
	
}
