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
        return ofDate(1990);
    }

    public static LocalDate ofDate(int yearFrom) {
        return LocalDate.of(ofInt(yearFrom, yearFrom + 20), ofInt(1, 13), ofInt(1, 29));
    }

    public static boolean ofBool(int p) {
        return ofInt(100, 100 + p) == 100;
    }

    public static boolean ofBool() {
        return ofBool(2);
    }

    public static String ofLandscape() {
        final var urls = new String[]{
            "https://m.tuniucdn.com/fb2/t1/G2/M00/C8/0F/Cii-TFknacGIJQ-NAAH--6rY4_MAAKa0gMKRaMAAf8T098_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G1/M00/9B/7B/Cii9EVi-bKCIf4akAADPUvxh07IAAIf0AE4c4MAAM9q508_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G2/M00/C7/F3/Cii-TlknPLiIeKvfAAHxUFMzsd4AAKZnQAAAAAAAfFo917_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G5/M00/64/31/Cii-slxP7Y-IaWazAAXP4V83ILoAAToWQOS6o0ABc_5560_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/filebroker/cdn/res/ab/b4/abb4746420e1ae5ce242485b392d8118_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G4/M00/0A/75/Cii-VVma1oyILk2RAAIIGYkscG8AAAPRwDcho4AAggx443_w450_h300_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/3EvqLxkcArY8rXJosNx4Q2ZZcS9S_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/du2hzeYFZPG6y6BYXcmAhihugnM_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/3V4QuWvJNdEhtPq56AfmwVHSnX8h_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/3JQuBWABzWgxY3XX26RA8EgddktU_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G6/M00/53/28/Cii-U13edVCISVL6AAQn83po8AcAAGVgwOa-bcABCgL395_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/3TUiVpt2RJeFvxY2KARG5iaptBP4_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/49N9czc3WFuMBCbcWDxXQtoeZspE_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/2UwBt9GSPoySemzCJKCBy5hSgVBL_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G6/M00/45/BD/Cii-TF3ZaBmIStrbAASnoOyg7FoAAFpYwEoz9oABKe4992_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/3FoT16PkXavKsssvktVvVq5Si6Cr_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/46HRfc58VYWErUSWUrXb7DDAi9eR_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G6/M00/53/44/Cii-TF3ee1OIGPPAAAP5XBZACX0AAGWgAEw83AAA_l0080_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb3/s1/2n9c/2w4iUXBimLphTrxEQQgJsinne6v5_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
            "https://m.tuniucdn.com/fb2/t1/G6/M00/52/B6/Cii-TF3eXKuIEx34AAYWz0tDxL4AAGRfACdY8YABhbn296_w200_h200_c1_t0_w360_h200_c1_t0.jpg",
        };
        return urls[ofInt(0, urls.length)];
    }

    public static String ofPortrait() {
        final var urls = new String[]{
        };
        return urls[ofInt(0, urls.length)];
    }

    public static String ofAvatar() {
        final var urls = new String[]{
        };
        return urls[ofInt(0, urls.length)];
    }
}

