package cn.itsite.abase.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuji on 2016/4/18.
 */
public class DateUtils {
    private static Calendar mCalendar = Calendar.getInstance();

    private static long ONE_SECOND = 1000 * 60;
    private static long ONE_HOUR = ONE_SECOND * 60;
    private static long ONE_DAY = ONE_HOUR * 24;
    private static long ONE_WEEK = ONE_DAY * 7;

    private static long EIGHT_HOUR = ONE_HOUR * 8;
    public static String DATE_FORMAT_YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_MMDD_HHMM = "MM-dd HH:mm";

    private static final String TAG = "DateUtils";

    /**
     * 获取昨天时间的最大值
     *
     * @return
     */
    public static long getYesterdayMaxTimeMillis() {
        long currTime = System.currentTimeMillis();
        mCalendar.setTime(new Date(currTime));

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        day -= 1;

        mCalendar.set(year, month, day, 23, 59, 59);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 判断是否是昨天之后
     *
     * @return
     */
    public static boolean isYesterdayAfter(long time) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);

        instance.setTimeInMillis(getYesterdayMaxTimeMillis());
        if (time <= getYesterdayMaxTimeMillis()) {
            return true;
        }
        return false;
    }

    public static String formatYYYYMMDD(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getStringBirth(long birth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(birth);
        //月份是从0开始的，所以要自加一
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String format(String create_at) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        try {
            Log.d(TAG, "create_at:" + create_at);
            Date startDate = df.parse(create_at);
            long timeMillis = startDate.getTime() + EIGHT_HOUR;
            return formatCommentDate(timeMillis);
        } catch (ParseException e) {
            e.printStackTrace();
            return formatCommentDate(Long.valueOf(create_at));
        }
    }

    public static String formatYYYYMMDD(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        Log.d(TAG, "date:" + date);
        Date startDate = null;
        try {
            startDate = df.parse(date);
            long timeMillis = startDate.getTime() + EIGHT_HOUR;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date(timeMillis));

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatToOtherFormat(String date, String currentFromat, String otherFormat) {
        DateFormat df = new SimpleDateFormat(currentFromat, Locale.CHINA);
        Date startDate;
        try {
            startDate = df.parse(date);
            long timeMillis = startDate.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat(otherFormat);
            return sdf.format(new Date(timeMillis));

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatCommentDate(String dateString) {
        return formatCommentDate(formatTime2Long(dateString));
    }

    public static String formatCommentDate(long timeMillis) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        long nowMillis = now.getTimeInMillis();

        if (timeMillis > now.getTimeInMillis()) {
            Logger.d(TAG, "大于多少：" + (timeMillis - now.getTimeInMillis()));
            return "刚刚";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        long diffMillis = nowMillis - timeMillis;

        Logger.d(TAG, "diffMillis:" + diffMillis);

        if (diffMillis < ONE_SECOND) {
            return (diffMillis / 1000) + "秒前";
        }
        if (diffMillis < ONE_HOUR) {
            return (diffMillis / ONE_SECOND) + "分钟前";
        }
        if (diffMillis < ONE_DAY && timeMillis > getYesterdayMaxTimeMillis()) {
            return (diffMillis / ONE_HOUR) + "小时前";
        }
        if (timeMillis < getYesterdayMaxTimeMillis() && diffMillis < ONE_DAY) {
            return "昨天";
        }
        if (diffMillis < ONE_WEEK) {
            return (diffMillis / ONE_DAY) + "天前";
        }
        return formatYYYYMMDD(timeMillis);
    }

    public static long formatTime2Long(String dateString) {
        return formatTime2Long("yyyy-MM-dd HH:mm:ss", dateString);
    }

    public static long formatTime2Long(String pattern, String dateString) {
        Date date = null;
        SimpleDateFormat formatTiem2long = new SimpleDateFormat(pattern);
        try {
            date = formatTiem2long.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String formatTimeStr(String pattern, String dateString) {
        Date date = null;
        SimpleDateFormat formatTiem2long = new SimpleDateFormat(pattern);
        try {
            date = formatTiem2long.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }
}
