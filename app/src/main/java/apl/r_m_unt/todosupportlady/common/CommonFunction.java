package apl.r_m_unt.todosupportlady.common;

import android.util.Log;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ryota on 2018/03/08.
 */

public class CommonFunction {

    private static final String TAG = "CommonFunction";

    private static final String TODAY = "今日";
    private static final String TOMORROW = "明日";
    private static final String THIS_WEEK = "今週";
    private static final String NEXT_WEEK = "来週";
    private static final String THIS_MONTH = "今月";
    private static final String THIS_YEAR = "今年";
    private static final String SOME_TIME = "いつか";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);

    public static String getTodoLimit(String limitType) {

        DateTime now;
        switch (limitType) {
            case TODAY:
                now = new DateTime();
                return formatStrFromDateTime(now);

            case TOMORROW:
                now = new DateTime();
                return formatStrFromDateTime(now.plusDays(1));

            case THIS_WEEK:
                now = new DateTime();
                return formatStrFromDateTime(now.dayOfWeek().withMaximumValue());

            case NEXT_WEEK:
                now = new DateTime();
                return formatStrFromDateTime(now.dayOfWeek().withMaximumValue().plus(7));

            case THIS_MONTH:
                now = new DateTime();
                return formatStrFromDateTime(now.dayOfMonth().withMaximumValue());

            case THIS_YEAR:
                now = new DateTime();
                return formatStrFromDateTime(now.dayOfYear().withMaximumValue());

            case SOME_TIME:
                return SOME_TIME;

            default:
                return null;
        }
    }

    /**
     *
     * @param dateTime 日付
     * @return String型の日付
     */
    public static String formatStrFromDateTime(DateTime dateTime) {

        return formatDateString(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
//        Date date = null;
//        try {
//            date = sdf.parse(dateStr);
//        } catch (ParseException e) {
//            Log.e(TAG, "日付の型変換エラー："+e.getMessage());
//        }
//        return date;
    }

    /**
     *
     * @param dateStr 文字列型の日付
     * @return Date型の日付
     */
    public static Date formatDateFromString(String dateStr) {

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            Log.e(TAG, "日付の型変換エラー："+e.getMessage());
        }
        return date;
    }

    /**
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return Date型の日付
     */
    public static String formatDateString(int year, int month, int day){

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("年").append(month).append("月").append(day).append("日");

        return sb.toString();
    }
}
