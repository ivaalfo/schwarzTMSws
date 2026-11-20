package eus.martico.schwarz.seguridad.timezone;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class DateTimeValidator {
	
	//Formateador estricto para ISO YYYY-MM-DD (uuuu en modo STRICT representa el año calendario)
	private static final DateTimeFormatter STRICT_DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
	
	
	/**
     * Valida que la fecha tenga formato estricto YYYY-MM-DD y sea una fecha real en el calendario.
     */
    public static boolean esFechaValidaISO(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateStr.trim(), STRICT_DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    
	/**
	 * Valída estrictamente formato ISO con Z al final (ej: "07:00:00.000Z" o "07:00:00Z")
	 * 
	 * @param timeStr
	 * @return boolean
	 */
	public static boolean esHoraValidaUTC(String timeStr) {
		if (timeStr == null || timeStr.trim().isEmpty() || !timeStr.trim().toUpperCase().endsWith("Z")) {
			return false;
		}
		try {
			OffsetTime.parse(timeStr.trim());
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
    
    /**
     * Comprueba que la fecha fin sea igual o posterior a la fecha inicio.
     * Asume que ambas fechas ya han pasado por esFechaValidaISO().
     */
    public static boolean esRangoFechasValido(String fechaDesde, String fechaHasta) {
        LocalDate inicio = LocalDate.parse(fechaDesde.trim(), STRICT_DATE_FORMATTER);
        LocalDate fin = LocalDate.parse(fechaHasta.trim(), STRICT_DATE_FORMATTER);
        return !fin.isBefore(inicio);
    }

	
	/**
	 * Convierte una fecha (YYYY-MM-DD) y una hora UTC (HH:mm:ss.SSSZ) a un String 
	 * con formato TIMESTAMP nativo de AS400 (YYYY-MM-DD HH:mm:ss.SSSSSS) 
	 * en hora local de España (Europe/Madrid), ajustando automáticamente verano (+2) o invierno (+1).
	 * 
	 * @param fechaIso Ej: "2026-05-29"
	 * @param horaUtc  Ej: "23:00:00.000Z"
	 * @return String con la hora local formateada (ej: "2026-05-30 01:00:00.000000")
	 */
	public static String convertirUTCtoHoraLocal(String fechaIso, String horaUtc) {
		LocalDate fecha = LocalDate.parse(fechaIso.trim());
		OffsetTime hora = OffsetTime.parse(horaUtc.trim());

		//Unimos fecha y hora en zona UTC
		OffsetDateTime fechaHoraUtc = hora.atDate(fecha);

		//Convertimos a la zona horaria del almacén (hora local de España).
		//Calcula +1 o +2 automáticamente según el día del año.
		//Si cruza las 24:00, avanza el día automaticamente.
		ZonedDateTime fechaHoraLocal = fechaHoraUtc.atZoneSameInstant(ZoneId.of("Europe/Madrid"));

		//Formateamos exactamente como espera el TIMESTAMP de DB2/AS400
        return fechaHoraLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
	}
	
	
	/**
     * Versión alternativa por si en el DAO preferimos pasarle un objeto java.sql.Timestamp 
     * directamente a ps.setTimestamp(i++, ...) en el PreparedStatementSetter.
     */
    public static Timestamp convertirUTCtoSqlTimestamp(String fechaIso, String horaUtc) {
        LocalDate fecha = LocalDate.parse(fechaIso.trim());
        OffsetTime hora = OffsetTime.parse(horaUtc.trim());
        
        ZonedDateTime fechaHoraLocal = hora.atDate(fecha).atZoneSameInstant(ZoneId.of("Europe/Madrid"));
        
        //Timestamp.valueOf espera un LocalDateTime sin zona horaria (ya ajustado a local)
        return Timestamp.valueOf(fechaHoraLocal.toLocalDateTime());
    }
    
    
}
