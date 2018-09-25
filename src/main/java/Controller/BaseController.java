package Controller;

import Utility.NumberUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BaseController {

    private static long Time;

    protected static Calendar getGMT8() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        Long UTC = 8 - timeZone.getRawOffset() / TimeUnit.HOURS.toMillis(1);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + Integer.valueOf(UTC.toString()));
        return cal;
    }

    protected static void printFunc(String func) {
        System.out.println(String.format("\r\n--- %s ---", func));
    }

    protected static void printStart() {
        System.out.println("*****************");
        System.out.println("*     Start     *");
        System.out.println("*****************");
        Time = new Date().getTime();
    }

    protected static void printEnd() {
        System.out.println(String.format("running: %s s",
                NumberUtility.format("9(5,3)", String.valueOf((new Date().getTime() - Time) / 1000.0), "Z,ZZ9.Z")));
        System.out.println("*****************");
        System.out.println("*      End      *");
        System.out.println("*****************");

    }
}

