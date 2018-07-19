package apl.r_m_unt.todosupportlady;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

import apl.r_m_unt.todosupportlady.todo.TodoDetailFragment;


/**
 * Created by ryota on 2018/03/10.
 */
public class CalendarDialogFragment extends DialogFragment{

    public static final String TODO_LIMIT_KEY = "TODO_LIMIT";
    private TodoDetailFragment todoDetailFragment;
    private static final String TAG = "CalendarDialogFragment";

    // ClassCastException対応
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach１");
        super.onAttach(context);
        Log.d(TAG, "onAttach２");
        // 遷移元のFragmentをチェック
        final Fragment fragment = getTargetFragment();
        if (fragment != null && fragment instanceof TodoDetailFragment) {
            todoDetailFragment = (TodoDetailFragment) fragment;
            Log.d(TAG, "遷移元情報の取得に成功２");
        }
        if (todoDetailFragment == null){
            throw new UnsupportedOperationException("遷移元Fragmentの取得に失敗");
        }
    }

    /**
     * リーク対策
     */
    @Override
    public void onDetach() {
        super.onDetach();
        todoDetailFragment = null;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // カレンダーに設定する日付を取得
        int year = 0;
        int month = 0;
        int day = 0;
        String todoLimit  = getArguments().getString(TODO_LIMIT_KEY);
        if (todoLimit != null && todoLimit != "") {
            try{
                String[] yearMonths = todoLimit.split("年");
                String[] monthDayss = yearMonths[1].split("月");
                String[] days = monthDayss[1].split("日");
                year = Integer.parseInt(yearMonths[0]);
                month = Integer.parseInt(monthDayss[0]) - 1;
                day = Integer.parseInt(days[0]);
            } catch (Exception e) {
                Log.d(TAG, "■■■カレンダーダイアログの日付取得に失敗:" + e.getMessage());
            }
        }

        // 日付取得でできていない場合は今日日付を設定する
        if (year == 0 || month == 0 || day == 0){
            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }


        Log.d(TAG, "==onCreateDialog==");
        // ダイアログ生成 DatePickerDialogのBuilderクラスを指定してインスタンス化する
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 選択された年・月・日を整形
                        String dateStr = year + "年" + (month + 1) + "月" + dayOfMonth + "日";

                        //todoDetailFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, dateStr);
                        todoDetailFragment.setCalendar(dateStr);
                    }
                },
                year,
                month,
                day
        );

//        // ダイアログのサイズを調整
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int dialogWidth = (int) (metrics.widthPixels * 0.9);
//        int dialogHeight = (int) (metrics.heightPixels * 0.9);
//        WindowManager.LayoutParams lp = datePickerDialog.getWindow().getAttributes();
//        lp.width = dialogWidth;
//        lp.height = dialogHeight;
//        datePickerDialog.getWindow().setAttributes(lp);

        datePickerDialog.show();

        return datePickerDialog;
    }
}
