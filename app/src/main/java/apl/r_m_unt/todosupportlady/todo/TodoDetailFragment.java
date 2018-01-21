package apl.r_m_unt.todosupportlady.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.preferences.TodoSetting;
import apl.r_m_unt.todosupportlady.preferences.TodoSettingInfo;


/**
 * TODO明細画面のフラグメント
 * Created by ryota on 2017/04/09.
 */
public class TodoDetailFragment extends Fragment {

    public static final String TODO_SELECT_NO = "TODO_SELECT_NO";

    private static final String TAG = "TodoDetailFragment";

    private int listIndex;
    private TodoSetting todoSetting;
    private TextView textViewTodoNo;

    private Spinner spinnerTodoMsg;
    NumberPicker numberPickerHour;
    NumberPicker numberPickerMinute;
    private EditText editTextMemo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        return rootView;
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 各画面要素を取得
        spinnerTodoMsg = (Spinner)getView().findViewById(R.id.spinner_message);
        numberPickerHour = (NumberPicker)getView().findViewById(R.id.numberPicker_hour);
        numberPickerHour.setMaxValue(23);
        numberPickerHour.setMinValue(0);
        numberPickerHour.setValue(0);
        numberPickerMinute = (NumberPicker)getView().findViewById(R.id.numberPicker_minute);
        numberPickerMinute.setMaxValue(59);
        numberPickerMinute.setMinValue(0);
        numberPickerMinute.setValue(0);
        editTextMemo = (EditText)getView().findViewById(R.id.editText_memo);
        textViewTodoNo = (TextView)getView().findViewById(R.id.textView_todo_no);

        // TODOメッセージ種類の設定
        // ArrayAdapterインスタンスをResourceXMLから取得してTODOメッセージの選択内容を作成
        ArrayAdapter<CharSequence> adapterTodoMsg = ArrayAdapter.createFromResource(
                getActivity(), R.array.todo_msg,
                android.R.layout.simple_spinner_item);
        adapterTodoMsg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTodoMsg.setAdapter(adapterTodoMsg);

        //----------------------------
        // リスト画面から取得したインデックスを取得
        Bundle arguments =  getArguments();
        listIndex = arguments.getInt(TodoDetailFragment.TODO_SELECT_NO);
        Log.d(TAG, "onViewCreated listIndexの値を更新" + listIndex);
        // TODO設定情報を取得
        todoSetting = TodoSetting.getInstance(getActivity().getApplicationContext());
        // 表示するTODO1件分の情報を取得
        TodoSettingInfo todoInfo = todoSetting.getTodoSettingInfoList().get(listIndex);
        setScreenValue(todoInfo);

        // 保存ボタン押下時の処理
        view.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 画面入力情報を取得
                TodoSettingInfo currentTodoSetInfo = getScreenValue(TodoSettingInfo.TODO_SWITCH_ON);

                // 画面設定情報をTODO設定情報に反映
                todoSetting.setTodoSettingInfo(listIndex, currentTodoSetInfo);
                // TODO設定を保存
                todoSetting.saveInstance(getActivity().getApplicationContext());

                // TODO実行の設定
                TodoController todoController = new TodoController(getActivity());
                todoController.setTodo(currentTodoSetInfo);
//                // TODO情報の変更内容を保存
//                todoSetting.saveInstance(getActivity().getApplicationContext());

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });

        // 削除ボタン押下時の処理
        view.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listIndex < 0 || todoSetting.getTodoSetSize() <= listIndex) {
                    return;
                }

                new AlertDialog.Builder(getActivity())
                    .setTitle(getActivity().getString(R.string.confirm_todo_delete))
                    .setMessage(editTextMemo.getText())
                    .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK ボタン押下時
                                Log.d(TAG, "TODO削除ダイアログのOKボタンを押下しました。" + "whichButton:" + whichButton);
                                // TODO情報を削除
                                todoSetting.removedTodoSettingInfo(listIndex);
                                // 変更内容の保存
                                todoSetting.saveInstance(getActivity().getApplicationContext());

                                // TODO削除情報の取得
                                TodoSettingInfo todoDeleteInfo = getScreenValue(TodoSettingInfo.TODO_SWITCH_OFF);

                                // TODO実行のキャンセル
                                TodoController todoController = new TodoController(getActivity());
                                todoController.setTodo(todoDeleteInfo);

                                // 当画面のActivityを終了する
                                getActivity().finish();
                            }
                        })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
            }
        });

        // キャンセルボタン押下時の処理
        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 画面表示内容設定
     * @param todoInfo
     */
    private void setScreenValue(TodoSettingInfo todoInfo) {
        // ----- 設定情報を画面に表示 ---- /

        // インデックス
        Log.i(TAG, "セットするtodoNo:" + todoInfo.getTodoNo());
        textViewTodoNo.setText(String.valueOf(todoInfo.getTodoNo()));

        //メモ
        editTextMemo.setText(todoInfo.getMemo());

        numberPickerHour.setValue(todoInfo.getHour());
        numberPickerMinute.setValue(todoInfo.getMinute());

        // TODOメッセージ
        spinnerTodoMsg.setSelection(todoInfo.getTodoMsgNo());
    }


    /**
     * 画面入力情報を取得
     * @return
     */
    private TodoSettingInfo getScreenValue(boolean switchFlg) {

        int todoNo = Integer.parseInt(textViewTodoNo.getText().toString());
        // 時間
        NumberPicker numberPickerHour = (NumberPicker)getView().findViewById(R.id.numberPicker_hour);
        NumberPicker numberPickerMinute = (NumberPicker)getView().findViewById(R.id.numberPicker_minute);

        int hour = numberPickerHour.getValue();
        int minute = numberPickerMinute.getValue();

        // TODO設定内容を返却（TODOスイッチは固定でONを設定）
        return new TodoSettingInfo(todoNo,
                numberPickerHour.getValue(),
                numberPickerMinute.getValue(),
                switchFlg,
                spinnerTodoMsg.getSelectedItemPosition(),
                editTextMemo.getText().toString());
    }
}
