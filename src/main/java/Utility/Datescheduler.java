package Utility;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class DateScheduler extends TimerTask {
    private String jobName = "";

    public DateScheduler(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() { System.out.println("Date = " + new Date() + ", execute " + jobName); }

    /**
     * 計算從當前時間currentDate開始，滿足條件dayOfWeek, hourOfDay, * minuteOfHour, secondOfMinite的最近時間 * @return
     */
    public Calendar getEarliestDate(Calendar currentDate, int dayOfWeek, int hourOfDay, int minuteOfHour, int secondOfMinite) {
        //計算當前時間的WEEK_OF_YEAR,DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND等各個欄位值
        int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
        int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        int currentSecond = currentDate.get(Calendar.SECOND);
        //如果輸入條件中的dayOfWeek小於當前日期的dayOfWeek,則WEEK_OF_YEAR需要推遲一週
        boolean weekLater = false;
        if (dayOfWeek < currentDayOfWeek) {
            weekLater = true;
        } else if (dayOfWeek == currentDayOfWeek) {
            //當輸入條件與當前日期的dayOfWeek相等時，如果輸入條件中的
            // hourOfDay小於當前日期的 //currentHour，則WEEK_OF_YEAR需要推遲一週
            if (hourOfDay < currentHour) {
                weekLater = true;
            } else if (hourOfDay == currentHour) {
                //當輸入條件與當前日期的dayOfWeek, hourOfDay相等時，
                // 如果輸入條件中的minuteOfHour小於當前日期的
                // currentMinute，則WEEK_OF_YEAR需要推遲一週
                if (minuteOfHour < currentMinute) {
                    weekLater = true;
                } else if (minuteOfHour == currentSecond) {
                    // 當輸入條件與當前日期的dayOfWeek, hourOfDay，
                    // minuteOfHour相等時，如果輸入條件中的
                    // secondOfMinite小於當前日期的currentSecond，
                    // 則WEEK_OF_YEAR需要推遲一週
                    if (secondOfMinite < currentSecond) {
                        weekLater = true;
                    }
                }
            }
        }
        if (weekLater) {
            //設定當前日期中的WEEK_OF_YEAR為當前周推遲一週
            currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
        }
        // 設定當前日期中的DAY_OF_WEEK,HOUR_OF_DAY,MINUTE,SECOND為輸入條件中的值。
        currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentDate.set(Calendar.MINUTE, minuteOfHour);
        currentDate.set(Calendar.SECOND, secondOfMinite);
        return currentDate;
    }

    public static void main(String[] args) throws Exception {
        DateScheduler test = new DateScheduler("job1");
        //獲取當前時間
        Calendar currentDate = Calendar.getInstance();
        long currentDateLong = currentDate.getTime().getTime();
        System.out.println("Current Date = " + currentDate.getTime().toString());
        //計算滿足條件的最近一次執行時間
        Calendar earliestDate = test.getEarliestDate(currentDate, 3, 16, 38, 10);
        long earliestDateLong = earliestDate.getTime().getTime();
        System.out.println("Earliest Date = " + earliestDate.getTime().toString());
        //計算從當前時間到最近一次執行時間的時間間隔
        long delay = earliestDateLong - currentDateLong;
        //計算執行週期為一星期
        long period = 7 * 24 * 60 * 60 * 1000;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        //從現在開始delay毫秒之後，每隔一星期執行一次job1
        service.scheduleAtFixedRate(test, delay, period, TimeUnit.MILLISECONDS);
    }
}