package eus.martico.schwarz.dao.slots.external;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import eus.martico.schwarz.dao.BaseGenDAO;
import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.SlotReserExternDTO;
import eus.martico.schwarz.model.dto.SlotReqExternDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@Repository("externalSlotsDAO")
public class ExternalSlotsDAOImp extends BaseGenDAO<SlotDTO> implements ExternalSlotsIDAO {
    
    //IF el pedido es EXTERNO a MARTICO :: Query de TSRequest para recuperar fechas validas ::
    private static final String SQL_SELECT_ALL_TIME_SLOTS = 
    	"SELECT " +
    		"SUBSTR(alxcmp4, 1, 4) as almacen_cod, " +
    		"sufech as fecha, " +
    		"count(*) as slots_libres " +
    			"FROM ldseus ,ldalmx, ldalma " +
    			"WHERE SUFECH BETWEEN ? AND ? " +
    				"AND suesta = '00' " +
    				"AND alxlky = sualky " +
    				"AND alxact = 'Y' " +
    				"AND alalky = alxlky " + 
    				"AND SUBSTR(aluscr, 1, 1) = '2' " +
    			"GROUP BY SUBSTR(alxcmp4, 1,  4), sufech " + 
    			"ORDER BY SUBSTR(alxcmp4, 1, 4), sufech";
    
    
    //IF el pedido es EXTERNO a MARTICO :: Query de TSReserve para recuperar el identificador único (suusky) en base a una fecha válida ::
    private static final String SQL_SELECT_RESERVA_DETALLE = 
    	"SELECT "+
    		"sufech as fecha, " + 
    		"sunudc as muelle, " +
    		"suusky as reserva_id, " +
    		"SUBSTR(ashors, (int(sunuho) *10)-9, 5) as h_inicio, " +
    		"SUBSTR(ashors, (int(sunuho) *10)-4, 5) as h_fin " +
    			"FROM ldseus ,ldalmx , ldalma , ldalse " +
    			"WHERE sufech = ? " +
					"AND suesta = '00' " +
					"AND SUBSTR(alxcmp4, 1, 4) = ? " +
					"AND alxlky = sualky " +
					"AND alxact = 'Y' " +
					"AND alalky = alxlky " +
					"AND SUBSTR(aluscr, 1, 1) = '2' " + 
					"AND asasky = suasky " +
				"ORDER BY sufech ,sunudc, sunuho";
    
    
    @Override
    public List<SlotReqExternDTO> getAllFreeTimeSlots(final String _dateFrom, final String _dateTo) 
    		throws ExternalSlotsDAOException {
    	try {
    		//Usamos PreparedStatementSetter para obligar al driver a tipar cada parámetro correctamente
            return this.jdbcTemplate.query(SQL_SELECT_ALL_TIME_SLOTS,
            		new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							int i = 1;
	                        ps.setDate(i++, java.sql.Date.valueOf(_dateFrom));
	                        ps.setDate(i++, java.sql.Date.valueOf(_dateTo));
						}
					},
            		//El mapeador que lee el ResultSet
                    (rs, rowNum) -> {
                    	SlotReqExternDTO slotReqEx = new SlotReqExternDTO();
                    	slotReqEx.setRecipientRDC(rs.getString("almacen_cod") != null ? rs.getString("almacen_cod").trim() : "");
                    	java.sql.Date fechaSql = rs.getDate("fecha");
                    	slotReqEx.setDate(fechaSql != null ? fechaSql.toString() : "");
                    	slotReqEx.setFreeSlots(rs.getInt("slots_libres"));
                        return slotReqEx;
                    }
                );
    	} catch (DataAccessException e) {
            throw new ExternalSlotsDAOException("Error en consulta resumen de slots", e.getMessage());
        }
    }
    
    
    @Override
    public List<SlotReserExternDTO> getSlotDetail(final String _rdc, final String _deliveryDate) 
    		throws ExternalSlotsDAOException {
    	try {
    		return this.jdbcTemplate.query(SQL_SELECT_RESERVA_DETALLE,
    				new PreparedStatementSetter() {
		    			@Override
		    			public void setValues(PreparedStatement ps) throws SQLException {
		    				int i = 1;
		    				ps.setDate(i++, java.sql.Date.valueOf(_deliveryDate));
		    				ps.setString(i++, _rdc);
		    			}
		    		}, 
    				(rs, rowNum) -> {
    					SlotReserExternDTO slotReserEx = new SlotReserExternDTO();
    					slotReserEx.setReservationId(rs.getLong("reserva_id"));
    					java.sql.Date fechaSql = rs.getDate("fecha");
    					slotReserEx.setDate(fechaSql != null ? fechaSql.toString() : "");
    					slotReserEx.setDock(rs.getInt("muelle"));
    					slotReserEx.setStartTime(rs.getString("h_inicio") != null ? rs.getString("h_inicio").trim() : "");
    					slotReserEx.setEndTime(rs.getString("h_fin") != null ? rs.getString("h_fin").trim() : "");
    					return slotReserEx;
    				}
    			);
    	} catch (DataAccessException e) {
    		throw new ExternalSlotsDAOException("Error en consulta detalle de slots ", e.getMessage());
    	}
    }
    
}
