package apl.r_m_unt.todosupportlady.common;

import android.util.Log;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Locale;

import apl.r_m_unt.todosupportlady.todo.TodoLimit;

/**
 * Created by ryota on 2018/03/08.
 */

public class TodoCommonFunction {

    private static final String TAG = "CommonFunction";

    private static final String TODAY = "今日";
    private static final String TOMORROW = "明日";
    private static final String THIS_WEEK = "今週";
    private static final String NEXT_WEEK = "来週";
    private static final String THIS_MONTH = "今月";
    private static final String THIS_YEAR = "今年";
    private static final String SOME_TIME = "未定";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);

    /**
     * 画面入力値から期限情報を取得する
     * @param limitType
     * @return
     */
    public static TodoLimit selectedToTodoLimit(String limitType) {

        DateTime now;
        switch (limitType) {
            case TODAY:
                now = new DateTime();
                return dateToTodoLimit(now);

            case TOMORROW:
                now = new DateTime();
                return dateToTodoLimit(now.plusDays(1));

            case THIS_WEEK:
                now = new DateTime();
                return dateToTodoLimit(now.dayOfWeek().withMaximumValue());

            case NEXT_WEEK:
                now = new DateTime();
                DateTime weekEnd = now.dayOfWeek().withMaximumValue();
                return dateToTodoLimit(weekEnd.plusDays(7));

            case THIS_MONTH:
                now = new DateTime();
                return dateToTodoLimit(now.dayOfMonth().withMaximumValue());

            case THIS_YEAR:
                now = new DateTime();
                return dateToTodoLimit(now.dayOfYear().withMaximumValue());

            case SOME_TIME:
                return new TodoLimit(9999, 99, 99);

            default:
                return null;
        }
    }

    /**
     *
     * @param dateTime 日付
     * @return String型の日付
     */
    public static TodoLimit dateToTodoLimit(DateTime dateTime) {

        return new TodoLimit(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
//        Date date = null;
//        try {
//            date = sdf.parse(dateStr);
//        } catch (ParseException e) {
//            Log.e(TAG, "日付の型変換エラー："+e.getMessage());
//        }
//        return date;
    }

//    /**
//     *
//     * @param dateStr 文字列型の日付
//     * @return Date型の日付
//     */
//    public static Date formatDateFromString(String dateStr) {
//
//        Date date = null;
//        try {
//            date = sdf.parse(dateStr);
//        } catch (ParseException e) {
//            Log.e(TAG, "日付の型変換エラー："+e.getMessage());
//        }
//        return date;
//    }

    /**
     * 年月日の文字列を取得
     * @param year 年
     * @param month 月
     * @param day 日
     * @return String型の日付
     */
    public static String formatLimitString(int year, int month, int day) {

        StringBuilder sb = new StringBuilder();
        if (year == 9999){
            sb.append(SOME_TIME);
        } else {
            sb.append(year).append("年").append(month).append("月").append(day).append("日");
        }

        return sb.toString();
    }

    /**
     * 年月日の文字列を取得
     * @param todoLimit 期限情報
     * @return String型の日付
     */
    public static String formatLimitString(TodoLimit todoLimit) {

        return formatLimitString(todoLimit.getYear(), todoLimit.getMonth(), todoLimit.getDay());
    }

    /**
     * 年月日のテキスト入力から期限情報を取得
     * @param limitStr 期限
     * @return 期限情報
     */
    public static TodoLimit textToTodoLimit(String limitStr) {

        TodoLimit todoLimit = null;

        // 未定の場合
        if (SOME_TIME.equals(limitStr)) {
            todoLimit = new TodoLimit(9999, 99, 99);

            // 日付入力済の場合
        } else if (limitStr != null && limitStr.length() > 0) {
            try{
                String[] yearMonths = limitStr.split("年");
                String[] monthDayss = yearMonths[1].split("月");
                String[] days = monthDayss[1].split("日");
                int year = Integer.parseInt(yearMonths[0]);
                int month = Integer.parseInt(monthDayss[0]);
                int day = Integer.parseInt(days[0]);
                todoLimit = new TodoLimit(year, month, day);
            } catch (Exception e) {
                Log.d(TAG, "■■■期限の文字列から年、月、日の値取得に失敗：" + e.getMessage());
                Log.d(TAG, "■■■todoLimit[" + limitStr + "]");
            }
        }

        return todoLimit;
    }
}
