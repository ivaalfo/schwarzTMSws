package eus.martico.schwarz.seguridad.timezone;

import java.time.OffsetTime;
import java.time.format.DateTimeParseException;

/**
 * 
 * @author Martico S.L. :: ialfonso
 * @version 1.0
 * @see https://www.martico.es/
 *
 */
public class DateTimeValidator {

	public static boolean esHoraValidaUTC(String timeStr) {
		
		//Valida estrictamente formato ISO con Z al final (ej: "07:00:00.000Z" o "07:00:00Z")
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
}
