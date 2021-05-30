package dev.negativekb.api.util;

public class TimeFormatter {

    public static String format(long time) {
        long currentTime = System.currentTimeMillis();
        long newTime = currentTime - time;

        if (newTime <= 60000L){
            int toSec = (int) (newTime / 1000) % 60;

            String second = (toSec == 1 ? "second" : "seconds");

            return (toSec + " " + second);
        }
        if (newTime <= 3599000L){
            int toSec = (int) (newTime / 1000) % 60;
            int toMin = (int) (newTime / 1000) / 60;

            boolean secondNotZero = toSec != 0;

            String minute = (toMin == 1 ? "minute" : "minutes");
            String second = (toSec == 1 ? "second" : "seconds");


            return (toMin + " " + minute + " ") + (secondNotZero ? toSec + " " + second : "");
        }

        int toSec = (int) (newTime/ 1000) % 60;
        int toMin = (int) ((newTime / (1000 * 60)) % 60);
        int toHour = (int) ((newTime / (1000 * 60 * 60)) % 24);

        boolean hourNotZero = toHour != 0;
        boolean minuteNotZero = toMin != 0;
        boolean secondNotZero = toSec != 0;

        String hour = (toHour == 1 ? "hour" : "hours");
        String minute = (toMin == 1 ? "minute" : "minutes");
        String second = (toSec == 1 ? "second" : "seconds");

        return (hourNotZero ? toHour + " " + hour + " " : "") + (minuteNotZero ? toMin + " " + minute + " " : "") + (secondNotZero ? toSec + " " + second : "");
    }


    public static Long longFromString(String s) {
        StringBuilder builder = new StringBuilder();
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                builder.append(c);
            } else {
                switch (c) {
                    case 's':
                        if (builder.length() != 0) {
                            seconds += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'm':
                        if (builder.length() != 0) {
                            minutes += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'h':
                        if (builder.length() != 0) {
                            hours += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'd':
                        if (builder.length() != 0) {
                            days += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    case 'w':
                        if (builder.length() != 0) {
                            weeks += Integer.parseInt(builder.toString());
                            builder = new StringBuilder();
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Not a valid duration format.");
                }
            }
        }
        return 1000L * (seconds + minutes * 60L + hours * 60 * 60L + days * 24 * 60 * 60L + weeks * 7 * 24 * 60 * 60L);
    }
}
