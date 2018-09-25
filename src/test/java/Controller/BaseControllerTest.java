package Controller;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class BaseControllerTest {

    @Test
    public void getGMT8() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        Long UTC = 8 - timeZone.getRawOffset() / TimeUnit.HOURS.toMillis(1);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + Integer.valueOf(UTC.toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH");
        assertEquals(sdf.format(cal.getTime()), sdf.format(BaseController.getGMT8().getTime()));
    }
}
