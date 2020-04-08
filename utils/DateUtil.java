package com.chaojianok.java.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 日期时间工具类 . <br>
 */
public class DateUtil {

    /**
     * 线程安全的日期格式对象-包含时分秒
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            // 完整日期
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    };

    /**
     * 线程安全的日期格式对象-年月日
     */
    private static final ThreadLocal<DateFormat> YMD = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            // 年月日
            return new SimpleDateFormat("yyyy-MM-dd");
        }

    };

    /**
     * 线程安全的日期格式对象-年月日
     */
    private static final ThreadLocal<DateFormat> MDHHmm = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            // 年月日
            return new SimpleDateFormat("MM-dd HH:mm");
        }

    };

    /**
     * 私有构造函数
     */
    private DateUtil() {
        super();
    }

    /**
     * 格式化完整日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.get().format(date);
    }

    /**
     * 格式化年月日
     *
     * @param date
     * @return yyyy-MM-dd格式的字符串
     */
    public static String formatYMD(Date date) {
        return YMD.get().format(date);
    }

    /**
     * 格式化年月日
     *
     * @param date
     * @return yyyy-MM-dd格式的字符串
     */
    public static String formatMDHs(Date date) {
        return MDHHmm.get().format(date);
    }

    /**
     * 获取指定日期的0点的字符串
     *
     * @param date
     * @return
     */
    public static String getZeroPointStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return formatDate(calendar.getTime());
    }

    /**
     * yyyy-MM-dd HH:mm格式
     * 当前时间
     *
     * @return
     */
    public static Date parseAuditDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String format = formatter.format(LocalDateTime.now());
        LocalDateTime parse = LocalDateTime.parse(format, formatter);
        return Date.from(parse.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的末点的字符串
     *
     * @param date
     * @return
     */
    public static String getLastPointStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取指定日期的0点的毫秒数
     *
     * @param date
     * @return
     */
    public static long getZeroPointMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 获取指定日期的末点的毫秒数
     *
     * @param date
     * @return
     */
    public static long getLastPointMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime().getTime();
    }

    public static Date getYearStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 0, 0, 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }

    public static Date getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        return cal.getTime();
    }

    public static Date getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return cal.getTime();
    }

    public static Date getDayStart() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DAY_OF_MONTH,-1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static List<String> getMonthBetween(Date begin, Date end, String pattern) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(begin);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(end);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(simpleDateFormat.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static List<String> getDayBetween(Date begin, Date end, String pattern) {
        return getDayBetween(begin, end, 0, pattern);
    }

    public static List<String> getDayBetween(Date begin, Date end, int plus, String pattern) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(begin);
        max.setTime(end);
        max.add(Calendar.DATE, plus);// 日期加1(包含结束)
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(simpleDateFormat.format(curr.getTime()));
            curr.add(Calendar.DAY_OF_YEAR, 1);
        }

        return result;
    }

    public static List<String> getHoursBetween(Date begin, Date end, String pattern) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(begin);
        max.setTime(end);
        max.add(Calendar.HOUR, 0);// 日期加1(包含结束)
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(simpleDateFormat.format(curr.getTime()));
            curr.add(Calendar.HOUR_OF_DAY, 1);
        }

        return result;
    }

    public static List<String> getWeeksBetween(Date begin, Date end, String pattern) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(begin);
        max.setTime(end);
        max.add(Calendar.DAY_OF_WEEK, +1);// 日期加1(包含结束)
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(simpleDateFormat.format(curr.getTime()));
            curr.add(Calendar.DAY_OF_WEEK, 1);
        }

        return result;
    }

    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

    private static final int PATTERN_CACHE_SIZE = 500;

    /**
     * localDateTime转换为格式化时间
     *
     * @param localDateTime localDateTime
     * @param pattern       格式
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * Date转换为格式化时间
     *
     * @param date    date
     * @param pattern 格式
     * @return
     */
    public static String format(Date date, String pattern) {
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

    /**
     * 格式化字符串转为Date
     *
     * @param time    格式化时间
     * @param pattern 格式
     * @return
     */
    public static Date parseDate(String time, String pattern) {
        return Date.from(parseLocalDateTime(time, pattern).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

    }

    /**
     * 格式化字符串转为LocalDate
     *
     * @param time    格式化时间
     * @param pattern 格式
     * @return
     */
    public static LocalDate parseLocalDateTime(String time, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        LocalDate parse = LocalDate.parse(time, formatter);
        return parse;
    }

    /**
     * 在缓存中创建DateTimeFormatter
     *
     * @param pattern 格式
     * @return
     */
    private static DateTimeFormatter createCacheFormatter(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if (formatter == null) {
            if (FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE) {
                formatter = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
                if (oldFormatter != null) {
                    formatter = oldFormatter;
                }
            }
        }

        return formatter;
    }
}
