package eus.martico.schwarz.dao.slots.internal;

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
@Repository("internalSlotsDAO")
public class InternalSlotsDAOImp extends BaseGenDAO<SlotDTO> implements InternalSlotsIDAO {
	
	//Query de TSRequest para recuperar fechas validas
    private static final String SQL_SELECT_TIME_SLOT = 
        "SELECT " +
        	"sunudc AS muelle, " +
        	"CHAR(sufech, iso) || 'T' || SUBSTR(ashors, (CAST(SUNUHO AS INT) * 10) - 9, 5) || ':00.000Z' AS fecha " +
	        	"FROM ldseus, ldorca, ldalse, ldalmx " +
	        	"WHERE TRIM(alxcmp4) = ? " +
			        "AND sualky = alxlky " +
			        "AND ocalky = sualky " +
			        "AND suorky = ocorky " +
			        "AND ocorde = ? " +
			        "AND sufech BETWEEN ? AND ? " +
			        "AND asasky = suasky";
    
    //Query de TSReserve para recuperar el identificador único (suusky) en base a una fecha válida
    private static final String SQL_SELECT_RESERVA_SLOT = 
        "SELECT " +
        	"suusky AS reserva_id, " +
        	"sunudc AS muelle, " +
        	"CHAR(sufech, iso) || 'T' || SUBSTR(ashors, (CAST(SUNUHO AS INT) * 10) - 9, 5) || ':00.000Z' AS fecha " +
        		"FROM ldseus, ldorca, ldalse, ldalmx " +
        		"WHERE TRIM(alxcmp4) = ? " +
        			"AND sualky = alxlky " +
			        "AND ocalky = sualky " +
			        "AND suorky = ocorky " +
			        "AND ocorde = ? " +
			        "AND sufech = ? " + 
			        "AND asasky = suasky";
    
    @Override
    public List<SlotDTO> getTimeSlot(final String _rdc, final String _orderNum, final String _dateFrom, final String _dateTo) 
    		throws InternalSlotsDAOException {
        try {
            //Usamos PreparedStatementSetter para obligar al driver a tipar cada parámetro correctamente
            return this.jdbcTemplate.query(SQL_SELECT_TIME_SLOT, 
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        int i = 1;
                        ps.setString(i++, _rdc);
                        ps.setString(i++, _orderNum);
                        
                        //Forzamos al driver de AS400 a tratar estos strings como tipos DATE nativos de SQL
                        ps.setDate(i++, java.sql.Date.valueOf(_dateFrom)); 		//Transforma "2026-05-10" a Date
                        ps.setDate(i++, java.sql.Date.valueOf(_dateTo)); 		//Transforma "2026-06-13" a Date
                    }
                }, 
                //El mapeador que lee el ResultSet
                (rs, rowNum) -> {
                    SlotDTO slot = new SlotDTO();
                    slot.setRecipientRDC(_rdc);
                    slot.setTogId(rs.getInt("muelle"));
                    slot.setDateTime(rs.getString("fecha"));
                    return slot;
                }
            );
        } catch (DataAccessException e) {
            throw new InternalSlotsDAOException("Error en consulta de Slots con Setter explícito", e.getMessage());
        }
    }
    
    
    @Override
    public Long getSlotReserve(final String _rdc, final String _orderNum, final String _deliveryDate) 
    		throws InternalSlotsDAOException {
        try {
            List<Long> resultados = this.jdbcTemplate.query(SQL_SELECT_RESERVA_SLOT, 
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        int i = 1;
                        ps.setString(i++, _rdc);
                        ps.setString(i++, _orderNum);
                        //Forzamos al driver JDBC a tratar el String "2026-05-29" como DATE SQL nativo
                        ps.setDate(i++, java.sql.Date.valueOf(_deliveryDate));
                    }
                }, 
                //Mapeador rápido para recuperar solo el string de la referencia (suusky)
                (rs, rowNum) -> rs.getLong("reserva_id")
            );

            if (resultados.isEmpty()) {
            	//No se encontró coincidencia o hueco libre
                return null;
            }
            //Devolvemos el suusky como identificador de reserva
            return resultados.get(0);

        } catch (DataAccessException e) {
            throw new InternalSlotsDAOException("Error en consulta TSReserve con Setter explícito", e.getMessage());
        }
    }
    
    
    
    
    
}
