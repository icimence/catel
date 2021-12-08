package tech.pinto.catel.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilDate {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static LocalDate from(String src) {
        return LocalDate.parse(src, formatter);
    }
}
