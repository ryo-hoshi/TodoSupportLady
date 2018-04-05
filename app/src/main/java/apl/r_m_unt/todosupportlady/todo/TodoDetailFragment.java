package apl.r_m_unt.todosupportlady.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.CompleteDialogFragment;
import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.CommonFunction;
import apl.r_m_unt.todosupportlady.preferences.TodoController;

import static apl.r_m_unt.todosupportlady.R.id.datePicker_limit;


/**
 * TODO明細画面のフラグメント
 * Created by ryota on 2017/04/09.
 */
public class TodoDetailFragment extends Fragment {

    public static final String SELECT_TODO_ID = "SELECT_TODO_ID";
    public static final String SELECT_TODO_TITLE = "SELECT_TODO_TITLE";
    public static final String SELECT_TODO_DETAIL = "SELECT_TODO_DETAIL";
    public static final String SELECT_TODO_LIMIT = "SELECT_TODO_LIMIT";
    public static final String SELECT_TODO_IS_COMPLETE = "SELECT_TODO_IS_COMPLETE";

    private static final String TAG = "TodoDetailFragment";

    private int todoId;
    private TodoController todoController;
    private TextView textViewTodoId;

    private Spinner spinnerLimit;
    private DatePicker datePickerLimit;
    private EditText editTextTitle;
    private EditText editTextDetail;
    private Button buttonComplete;
    private EditText editTextLimit;
    private String appointLimitType = "日付を指定";
    private FragmentManager fragmentManager;
    private DialogFragment dialogFragment;

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
        spinnerLimit = (Spinner)getView().findViewById(R.id.spinner_limit);
        datePickerLimit = (DatePicker)getView().findViewById(datePicker_limit);
        editTextTitle = (EditText)getView().findViewById(R.id.editText_title);
        editTextDetail = (EditText)getView().findViewById(R.id.editText_detail);
        textViewTodoId = (TextView)getView().findViewById(R.id.textView_todo_id);
        buttonComplete = (Button)getView().findViewById(R.id.button_complete);
        editTextLimit = (EditText)getView().findViewById(R.id.editText_limit);

        // スクロールバー表示を可能にする
        //editTextDetail.setMovementMethod(ScrollingMovementMethod.getInstance());

        // リスト画面から取得したインデックスを取得
        Bundle arguments =  getArguments();
        todoId = arguments.getInt(TodoDetailFragment.SELECT_TODO_ID);
        Log.d(TAG, "onViewCreated todoIdの値:" + todoId);

        // 新規登録時は完了ボタンと期限（テキスト）は非表示
        if (todoId == -1) {
            buttonComplete.setVisibility(View.INVISIBLE);
            editTextLimit.setVisibility(View.INVISIBLE);

            // TODO期限種類の設定
            // ArrayAdapterインスタンスをResourceXMLから取得してTODOメッセージの選択内容を作成
            ArrayAdapter<CharSequence> adapterLimit = ArrayAdapter.createFromResource(
                    getActivity(), R.array.todo_limit,
                    android.R.layout.simple_spinner_item);
            adapterLimit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLimit.setAdapter(adapterLimit);

            // スピナーの動作を設定
            spinnerLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                // アイテムが選択された時の動作
                @Override
                public void onItemSelected(AdapterView parent, View view, int position, long id){
                    // Spinnerを取得
                    Spinner spinner = (Spinner) parent;
                    // 選択されたアイテムのテキスト
                    String str = spinner.getSelectedItem().toString();

                    // スピナーの最後の値「日付を指定」を設定した場合は日付入力領域を表示する
                    if (appointLimitType.equals(str)) {
                        datePickerLimit.setVisibility(View.VISIBLE);
                    } else {
                        datePickerLimit.setVisibility(View.GONE);
                    }
                }

                // 何も選択されたかった時の動作
                public void onNothingSelected(AdapterView parent){
                }
            });

            // 編集時は期限種別とdatepickerは非表示
            // 一覧から渡された情報をセット
        } else {
            spinnerLimit.setVisibility(View.GONE);
            datePickerLimit.setVisibility(View.GONE);

            Log.d(TAG, "SELECT_TODO_TITLEの値:" + arguments.getString(TodoDetailFragment.SELECT_TODO_TITLE));
            editTextTitle.setText(arguments.getString(TodoDetailFragment.SELECT_TODO_TITLE));
            editTextDetail.setText(arguments.getString(TodoDetailFragment.SELECT_TODO_DETAIL));
            editTextLimit.setText(arguments.getString(TodoDetailFragment.SELECT_TODO_LIMIT));
            //setScreenValue();
        }


        //----------------------------


        // TODO設定情報を取得
//        todoController = TodoController.getInstance(getActivity().getApplicationContext());
        // 表示するTODO1件分の情報を取得
//        TodoSettingInfo todoSettingInfo = todoController.getTodoSettingInfoList().get(listIndex);
//        setScreenValue(todoSettingInfo);

        // 保存ボタン押下時の処理
        view.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 画面入力情報を取得
                TodoInfo todoSetInfo = getScreenValue(todoId);

                Log.d(TAG, "getScreenValue取得結果:"
                        + "id" + todoSetInfo.getId()
                        + ", title" + todoSetInfo.getTitle()
                        + ", detail" + todoSetInfo.getDetail()
                        + ", limit" + todoSetInfo.getLimit()
                        + ", iscomplete" + todoSetInfo.getIsComplete());

                // 入力値チェック
                String result = checkInputVal(todoSetInfo);
                if (result != null) {
                    // 入力チェックエラー内容を出力
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                    return;
                }

                // TODOの登録
                TodoModel todoModel = new TodoModel(getActivity());

                long rtn = todoModel.insertTodoInfo(todoSetInfo);

                if (rtn == -1) {
                    Log.d(TAG, "todoModel insert結果：TODOの登録に失敗しました");
                } else {
                    Log.d(TAG, "todoModel insert結果：TODOを登録しました");
                }

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });

        // 削除ボタン押下時の処理
        view.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (listIndex < 0 || todoController.getTodoSetSize() <= listIndex) {
//                    return;
//                }

                // TODO 削除時のダイアログ表示
                // 削除時のダイアログを表示する

                new AlertDialog.Builder(getActivity())
                    .setTitle(getActivity().getString(R.string.confirm_todo_delete))
                    .setMessage(editTextDetail.getText())
                    .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK ボタン押下時
                                Log.d(TAG, "TODO削除ダイアログのOKボタンを押下しました。" + "whichButton:" + whichButton);
                                // TODO情報を削除
//                                todoController.removedTodoSettingInfo(listIndex);
                                // 変更内容の保存
//                                todoController.saveInstance(getActivity().getApplicationContext());

                                // 画面入力情報の取得
                                TodoInfo todoDeleteInfo = getScreenValue(todoId);

                                // TODOの削除
//                                TodoDAO todoDAO = new TodoDAO(getActivity());
//                                todoDAO.setTodo(todoDeleteInfo);

                                // 当画面のActivityを終了する
                                getActivity().finish();
                            }
                        })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
            }
        });

        // 戻るボタン押下時の処理
        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });


        // 完了ボタン押下時の処理
        view.findViewById(R.id.button_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 画面入力情報を取得
                TodoInfo todoSetInfo = getScreenValue(todoId);


                // 画面設定情報をTODO設定情報に反映
//                todoController.setTodoSettingInfo(listIndex, currentTodoSetInfo);
                // TODO設定を保存
//                todoController.saveInstance(getActivity().getApplicationContext());

                // TODO実行の設定
//                TodoDAO todoDAO = new TodoDAO(getActivity());
//                todoDAO.setTodo(currentTodoSetInfo);

                // TODO 完了時のダイアログ表示
                // 完了時のダイアログを表示する
                fragmentManager = getActivity().getSupportFragmentManager();
                dialogFragment = new CompleteDialogFragment();
                dialogFragment.show(fragmentManager, "complete");

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    private String checkInputVal(TodoInfo todoInfo) {
        String result = null;

        if (todoInfo.getTitle() == null || todoInfo.getTitle().isEmpty()) {
            result = "タイトルは入力必須の項目です";
        } else if (todoInfo.getLimit() == null || todoInfo.getLimit().isEmpty()) {
            result = "期限は入力必須の項目です";
        }
        return result;
    }

//    /**
//     * 画面表示内容設定
//     * @param todoInfo
//     */
//    private void setScreenValue(TodoInfo todoInfo) {
//        // ----- 設定情報を画面に表示 ---- /
//
//        // インデックス
//        Log.i(TAG, "セットするtodoNo:" + todoInfo.getTodoNo());
//        textViewTodoId.setText(String.valueOf(todoInfo.getTodoNo()));
//
//        //メモ
//        editTextDetail.setText(todoInfo.getMemo());
//
//
//        // TODOメッセージ
//        spinnerLimit.setSelection(todoInfo.getTodoMsgNo());
//    }


    /**
     * 画面入力情報を取得
     * @return
     */
    private TodoInfo getScreenValue(int selectTodoId) {

        int todoId = selectTodoId == -1 ? selectTodoId : Integer.parseInt(textViewTodoId.getText().toString());

        // 期限
        String todoLimit;
        String limitType = spinnerLimit.getSelectedItem().toString();
        // 編集モードの場合または日付入力を選択の場合は入力値をdatePickerから取得
        if (selectTodoId != -1 || appointLimitType.equals(limitType)){
            int year = datePickerLimit.getYear();
            int month = datePickerLimit.getMonth();
            int day = datePickerLimit.getDayOfMonth();
            //todoLimit = CommonFunction.formatDateFromString(year, month, day);
            todoLimit = CommonFunction.formatDateString(year, month, day);
        } else {
            // スピナーの選択種別から期限を取得する
            todoLimit = CommonFunction.getTodoLimit(limitType);
        }

        // TODO設定内容を返却（TODOスイッチは固定でONを設定）
        return new TodoInfo(todoId,
                todoLimit,
                editTextTitle.getText().toString(),
                editTextDetail.getText().toString(),
                TodoInfo.INCOMPLETE
        );
    }


    /**
     * 画面情報を設定
     */
    private void setScreenValue(TodoInfo todoInfo) {

        textViewTodoId.setText(todoInfo.getId());
        editTextTitle.setText(todoInfo.getTitle());
        editTextDetail.setText(todoInfo.getDetail());
        editTextLimit.setText(todoInfo.getLimit());
    }
}
