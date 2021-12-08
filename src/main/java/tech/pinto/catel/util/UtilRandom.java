package tech.pinto.catel.util;

import java.security.SecureRandom;

public class UtilRandom {
    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T ofEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static int ofInt(int lower, int upper) {
        return lower + random.nextInt(upper - lower);
    }

    public static String ofPhoneNumber() {
        long number = (long) Math.floor(Math.random() * 1_000_000_000L) + 10_000_000_000L;
        return String.valueOf(number);
    }
}
