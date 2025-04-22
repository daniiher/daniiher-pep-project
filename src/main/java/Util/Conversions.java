package Util;

public final class Conversions {
    private Conversions() {

    }

    public static Integer stringToInteger(String n) {
        if (n == null) return null;
        try {
            return Integer.valueOf(n);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
