package tech.pinto.catel.util;

import java.security.SecureRandom;
import java.time.LocalDate;

public class UtilRandom {
    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T ofEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static int ofInt(int lower, int upper) {
        return lower + random.nextInt(upper - lower);
    }

    public static long ofLong(long lower, long upper) {
        return lower + Math.abs(random.nextLong()) % (upper - lower);
    }

    public static long ofLong() {
        return random.nextLong();
    }

    public static String ofPhoneNumber() {
        long number = (long) Math.floor(Math.random() * 1_000_000_000L) + 10_000_000_000L;
        return String.valueOf(number);
    }

    public static String ofIdNo() {
        var builder = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            builder.append(ofInt(0, 10));
        }
        return builder.toString();
    }

    public static LocalDate ofDate() {
        return LocalDate.of(ofInt(1990, 2010), ofInt(1, 13), ofInt(1, 29));
    }

    public static boolean ofBool(int p) {
        return ofInt(100, 100 + p) == 100;
    }

    public static boolean ofBool() {
        return ofBool(2);
    }
}
