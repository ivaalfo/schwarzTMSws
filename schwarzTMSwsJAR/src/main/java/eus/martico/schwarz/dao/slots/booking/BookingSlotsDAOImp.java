package eus.martico.schwarz.dao.slots.booking;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import eus.martico.schwarz.dao.BaseGenDAO;
import eus.martico.schwarz.model.dto.SlotDTO;
import eus.martico.schwarz.model.dto.TSBookDTO;
import eus.martico.schwarz.model.dto.TSBookResponseDTO;


/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
@Repository("bookingSlotsDAO")
public class BookingSlotsDAOImp extends BaseGenDAO<SlotDTO> implements BookingSlotsIDAO {
	
	//A.Query de TSBook para recuperar los datos de confirmación final y tiempos estimados
    private static final String SQL_SELECT_BOOK_SLOT = 
    	"SELECT " +
            "CHAR(sufech, iso) AS fecha_inicio, " +
            "SUBSTR(ashors, (CAST(SUNUHO AS INT) * 10) - 9, 5) || ':00.000Z' AS hora_inicio, " +
            "SUBSTR(ashors, (CAST(SUNUHO AS INT) * 10) - 4, 5) || ':00.000Z' AS hora_fin, " +
            "TRIM(dodest) || '-' || CHAR(dodoky) AS reference, " +
            "suusky AS reserva_id " +
            	"FROM ldseus, ldorca, ldalse, ldalmx, lddese " +
            	"WHERE TRIM(alxcmp4) = ? " +
            		"AND sualky = alxlky " +
            		"AND ocalky = sualky " +
    		        "AND doorky = ocorky " +
    		        "AND suorky = ocorky " +
    		        "AND ocorde = ? " +
    		        "AND sufech = ? " +
    		        "AND suusky = ? " +
    		        "AND asasky = suasky";
    
    //B.Consulta previa a las update del booking externo
    private static final String SQL_CHECK_EXTERNAL_SLOT_FREE = 
    	"SELECT COUNT(1) " +
    	    "FROM ldseus, ldalmx " +
    	    "WHERE sualky = alxlky " +
    	    	"AND TRIM(alxcmp4) = ? " +
    	    	"AND sufech = ? " +
    	    	"AND suusky = ? " +
    	    	"AND suesta <> '15' " +
    	    	"AND (suorky IS NULL OR suorky = 0)";
    
	//B1.UPDATE: LDORCA
	private static final String SQL_UPDATE_BOOK_LDORCA = 
	    "UPDATE LDORCA SET " +
	    	"ocmatr = ?, " +
	    	"ocfeen = ?, " +
	    	"octeen = ? " +
	    "WHERE ocorde = ? " +
	    "AND ocalky = (SELECT alxlky FROM ldalmx WHERE TRIM(alxcmp4) = ?)";
	
	//B2.UPDATE: LDDESE
	private static final String SQL_UPDATE_BOOK_LDDESE = 
	    "UPDATE LDDESE SET " +
	    	"doesta = '15', " +
	    	//XXX SOBRA "doorky = (SELECT ocorky FROM ldorca, ldalmx WHERE ocorde = ? AND ocalky = alxlky AND TRIM(alxcmp4) = ?), " +
	    	//TODO: Pendiente del IF(EXTERNO) -> el WHERE de LDOPTR (de momento queda a 0 si no hay match)
	    	//"dotrky = COALESCE((SELECT ottrky FROM ldoptr WHERE ... FETCH FIRST 1 ROW ONLY), 0), " +
	    	//"dotsky = COALESCE((SELECT ottrky FROM ldoptr WHERE ... FETCH FIRST 1 ROW ONLY), 0), " +
	    	"dofecg = ?, " +
	    	"dohocg = ?, " +
	    	"dodock = ?, " +
	    	"domatr = ?, " +
	    	"doremo = ?, " +
	    	"dofeen = ?, " +
	    	"doteen = ? " +
	    "WHERE doorky = (SELECT ocorky FROM ldorca, ldalmx WHERE ocorde = ? AND ocalky = alxlky AND TRIM(alxcmp4) = ?)";

	//B3.UPDATE: LDSEUS
	private static final String SQL_UPDATE_BOOK_LDSEUS = 
	    "UPDATE LDSEUS SET " +
	    	"suesta = '15', " +
	    	"sudoky = (SELECT dodoky FROM lddese WHERE doorky = (SELECT ocorky FROM ldorca, ldalmx WHERE ocorde = ? AND ocalky = alxlky AND TRIM(alxcmp4) = ?) FETCH FIRST 1 ROW ONLY), " +
	    	"suorky = (SELECT ocorky FROM ldorca, ldalmx WHERE ocorde = ? AND ocalky = alxlky AND TRIM(alxcmp4) = ?) " +
	    "WHERE suusky = ? AND suesta <> '15'";
	
	
	
	@Override
    public TSBookResponseDTO getSlotBookInfo(final String _rdc, final String _orderNum, final String _deliveryDate, final Long _reserveId) 
    		throws BookingSlotsDAOException {
        try {
        	//Limpiamos parámetros y validamos antes de lanzar la query al AS400
            final String rdcLimpio = (_rdc != null) ? _rdc.trim() : "";
            final String orderNumLimpio = (_orderNum != null) ? _orderNum.trim() : "";
            final java.sql.Date fechaEntregaSql = (_deliveryDate != null && !_deliveryDate.trim().isEmpty())
                    ? java.sql.Date.valueOf(_deliveryDate.trim())
                    : null;

            if (fechaEntregaSql == null || _reserveId == null) {
                return null;
            }
            List<TSBookResponseDTO> resultados = this.jdbcTemplate.query(SQL_SELECT_BOOK_SLOT, 
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        int i = 1;
                        ps.setString(i++, rdcLimpio);
                        ps.setString(i++, orderNumLimpio);
                        //Mantenemos el truco de pasar el Date nativo al optimizador de AS400
                        ps.setDate(i++, fechaEntregaSql);
                        ps.setLong(i++, _reserveId);
                    }
                }, 
                //Mapeador para rellenar directamente el DTO de salida limpio
                (rs, rowNum) -> {
                	TSBookResponseDTO dto = new TSBookResponseDTO();
                    dto.setOrderNumber(orderNumLimpio);
                    
                    String fechaInicio = rs.getString("fecha_inicio");
                    dto.setRequestedDeliveryDate(fechaInicio != null ? fechaInicio.trim() : "");
                    
                    dto.setRequestedDeliveryTime(rs.getString("hora_inicio") != null ? rs.getString("hora_inicio").trim() : "");
                    dto.setRequestedDeliveryEndTime(rs.getString("hora_fin") != null ? rs.getString("hora_fin").trim() : "");
                    
                    String ref = rs.getString("reference");
                    dto.setReference(ref != null ? ref.trim() : "");
                    
                    dto.setReservationId(rs.getLong("reserva_id"));
                    return dto;
                }
            );

            if (resultados.isEmpty()) {
            	//Si no encuentra concordancias en las tablas
                return null;
            }
            //Devolvemos el DTO relleno para el controlador
            return resultados.get(0);
            
        } catch (IllegalArgumentException ex) {
            //Por si _deliveryDate viene con un formato distinto a yyyy-MM-dd
            throw new BookingSlotsDAOException("Formato de fecha inválido en consulta TSBook: " + _deliveryDate, ex);
        } catch (DataAccessException ex) {
            throw new BookingSlotsDAOException("Error en consulta TSBook con Setter explícito", ex.getMessage());
        }
    }
	
	@Override
	public boolean isExternalBookingSlotFree(final String _rdc, final String _deliveryDate, final Long _reserveId) 
	        throws BookingSlotsDAOException {
	    try {
	        final String rdcLimpio = (_rdc != null) ? _rdc.trim() : "";
	        final java.sql.Date fechaEntregaSql = (_deliveryDate != null && !_deliveryDate.trim().isEmpty())
	                ? java.sql.Date.valueOf(_deliveryDate.trim())
	                : null;

	        if (fechaEntregaSql == null || _reserveId == null) {
	            return false;
	        }

	        Integer count = this.jdbcTemplate.query(
	            SQL_CHECK_EXTERNAL_SLOT_FREE,
	            new PreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps) throws SQLException {
	                    int i = 1;
	                    ps.setString(i++, rdcLimpio);
	                    ps.setDate(i++, fechaEntregaSql);
	                    ps.setLong(i++, _reserveId);
	                }
	            },
	            rs -> rs.next() ? rs.getInt(1) : 0
	        );

	        return count != null && count > 0;

	    } catch (IllegalArgumentException e) {
	        throw new BookingSlotsDAOException("Formato de fecha inválido al comprobar slot libre: " + _deliveryDate, e);
	    } catch (DataAccessException e) {
	        throw new BookingSlotsDAOException("Error comprobando disponibilidad del slot externo: " + e.getMessage(), e);
	    }
	}
	
	@Override
	public void updateLdorca(TSBookDTO _requestData) 
			throws BookingSlotsDAOException {
	    try {
	    	String matricula = _requestData.getLicensePlate() != null ? _requestData.getLicensePlate().trim() : "";
	        java.sql.Date fechaDescarga = (_requestData.getUploadingDateDCT() != null && !_requestData.getUploadingDateDCT().trim().isEmpty())
	                ? java.sql.Date.valueOf(_requestData.getUploadingDateDCT().trim())
	                : null;
	        String obsDescarga = formatearObservacionDescarga(_requestData.getUploadingDCT(), _requestData.getUploadingTimeDCT());

	        int rowsLdorca = jdbcTemplate.update(SQL_UPDATE_BOOK_LDORCA, new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	                ps.setString(1, matricula);
	                ps.setDate(2, fechaDescarga);
	                ps.setString(3, obsDescarga);
	                ps.setString(4, _requestData.getOrderNumber().trim());
	                ps.setString(5, _requestData.getRecipientRDC().trim());
	            }
	        });
	        
	        if (rowsLdorca == 0) {
	            throw new BookingSlotsDAOException("No existe registro en LDORCA para el pedido indicado: " + _requestData.getOrderNumber());
	        }
	    } catch (DataAccessException ex) {
	        throw new BookingSlotsDAOException("Error actualizando LDORCA en booking: " + ex.getMessage(), ex);
	    }
	}

	@Override
	public void updateLddese(TSBookDTO _requestData) 
			throws BookingSlotsDAOException {
		try {
			String orderNo = _requestData.getOrderNumber().trim();
	        String rdc = _requestData.getRecipientRDC().trim();
	        java.sql.Date fechaEntrega = java.sql.Date.valueOf(_requestData.getRequestedDeliveryDate().trim());
	        String horaEntrega = extraerHoraHHMM(_requestData.getRequestedDeliveryTime()); // De "07:00:00.000Z" saca "07:00"
	        String muelle = _requestData.getTogId() != null ? String.valueOf(_requestData.getTogId()) : "";
	        String matricula = _requestData.getLicensePlate() != null ? _requestData.getLicensePlate().trim() : "";
	        String remolque = "";
	        java.sql.Date fechaDescarga = (_requestData.getUploadingDateDCT() != null && !_requestData.getUploadingDateDCT().trim().isEmpty())
	                ? java.sql.Date.valueOf(_requestData.getUploadingDateDCT().trim())
	                : null;
	        String obsDescarga = formatearObservacionDescarga(_requestData.getUploadingDCT(), _requestData.getUploadingTimeDCT());

	        int rowsLddese = jdbcTemplate.update(SQL_UPDATE_BOOK_LDDESE, new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	            	ps.setDate(1, fechaEntrega);
	                ps.setString(2, horaEntrega);
	                ps.setString(3, muelle);
	                ps.setString(4, matricula);
	                ps.setString(5, remolque);
	                ps.setDate(6, fechaDescarga);
	                ps.setString(7, obsDescarga);
	                ps.setString(8, orderNo);
	                ps.setString(9, rdc);
	            }
	        });
	        
	        if (rowsLddese == 0) {
	            throw new BookingSlotsDAOException("El pedido aún no tiene expedición creada en LDDESE.");
	        }
	    } catch (DataAccessException ex) {
	        throw new BookingSlotsDAOException("Error actualizando LDDESE en booking: " + ex.getMessage(), ex);
	    }
	}

	@Override
	public void updateLdseus(TSBookDTO _requestData) throws BookingSlotsDAOException {
	    try {
	        String orderNo = _requestData.getOrderNumber().trim();
	        String rdc = _requestData.getRecipientRDC().trim();
	        long reservationId = _requestData.getReservationId();

	        int rowsLdseus = jdbcTemplate.update(SQL_UPDATE_BOOK_LDSEUS, ps -> {
	            ps.setString(1, orderNo);
	            ps.setString(2, rdc);
	            ps.setString(3, orderNo);
	            ps.setString(4, rdc);
	            ps.setLong(5, reservationId);
	        });
	        
	        if (rowsLdseus == 0) {
	            throw new BookingSlotsDAOException("El pedido no registrado en LDSEUS.");
	        }
	    } catch (DataAccessException ex) {
	        throw new BookingSlotsDAOException("Error actualizando LDSEUS en booking: " + ex.getMessage(), ex);
	    }
	}

	/**
	 * Convierte "07:00:00.000Z" o "07:00" en "07:00" (5 caracteres para columnas CHAR(5) de AS400)
	 */
	private String extraerHoraHHMM(String horaIso) {
	    if (horaIso == null || horaIso.trim().isEmpty()) {
	        return " ";
	    }
	    String limpia = horaIso.trim();
	    return limpia.length() >= 5 ? limpia.substring(0, 5) : limpia;
	}
	
	/**
	 * Combina uploadingDCT + hora formateada (ej: "RES_271124123" + "12:30:00.000Z" -> "RES_271124123 12.30h")
	 */
	private String formatearObservacionDescarga(String uploadingDCT, String uploadingTimeDCT) {
	    String codigo = (uploadingDCT != null) ? uploadingDCT.trim() : "";
	    String hora = "";
	    
	    if (uploadingTimeDCT != null && uploadingTimeDCT.trim().length() >= 5) {
	        // De "12:30:00.000Z" sacamos "12:30" y cambiamos ':' por '.' + 'h' -> "12.30h"
	        hora = uploadingTimeDCT.trim().substring(0, 5).replace(':', '.') + "h";
	    }
	    
	    return (codigo + " " + hora).trim();
	}
	
}
