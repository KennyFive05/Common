package Controller;

import javax.servlet.http.HttpServlet;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BaseServlet extends HttpServlet {
    protected static Calendar getGMT8() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        Long UTC = 8 - timeZone.getRawOffset() / TimeUnit.HOURS.toMillis(1);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + Integer.valueOf(UTC.toString()));
        return cal;
    }
}
