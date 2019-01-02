package base.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static Date getDate(String dateString, String format) {
        if (!TextUtils.isEmpty(dateString)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new Date();
    }

    public static String getDateString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getDateString(Date date, SimpleDateFormat sf) {
        return sf.format(date);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getAge(Date birthday) {
        Calendar calendar = DateUtil.getCalendar(birthday);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date date = new Date();
        calendar = DateUtil.getCalendar(date);
        int age = calendar.get(Calendar.YEAR) - year;
        if (calendar.get(Calendar.MONTH) < month
                || (calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) < day)) {
            age--;
        }

        return age;
    }

    /**
     * 判断两个long时间是否属于同一天
     * @param ms1
     * @param ms2
     * @return
     */
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    public static String getMonthDayStr(String timeStr)
    {
        Date date = getDate(timeStr, "yyyy-MM-dd HH:mm:ss");
        if (null != date)
        {
            return getDateString(date, "M/d HH:mm");
        }
        return "";
    }
}
