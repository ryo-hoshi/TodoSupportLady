package apl.r_m_unt.todosupportlady.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.TodoCommonFunction;
import apl.r_m_unt.todosupportlady.common.TodoConstant;
import apl.r_m_unt.todosupportlady.dialog.CalendarDialogFragment;
import apl.r_m_unt.todosupportlady.dialog.CompleteImgDialogFragment;
import apl.r_m_unt.todosupportlady.dialog.DeleteConfirmDialogFragment;
import apl.r_m_unt.todosupportlady.dialog.DetailBackConfirmDialogFragment;
import apl.r_m_unt.todosupportlady.model.SharedPreferenceDataHelper;
import apl.r_m_unt.todosupportlady.model.TodoDao;

import static android.app.Activity.RESULT_OK;
import static apl.r_m_unt.todosupportlady.common.TodoCommonFunction.formatLimitString;
import static apl.r_m_unt.todosupportlady.dialog.CalendarDialogFragment.TODO_LIMIT_KEY;


/**
 * TODO明細画面のフラグメント
 * Created by ryota on 2017/04/09.
 */
public class TodoDetailFragment extends Fragment {

    public static final String SELECT_TODO_ID = "SELECT_TODO_ID";
    public static final String SELECT_TODO_TITLE = "SELECT_TODO_TITLE";
    public static final String SELECT_TODO_DETAIL = "SELECT_TODO_DETAIL";
    public static final String SELECT_TODO_LIMIT_YEAR = "SELECT_TODO_LIMIT_YEAR";
    public static final String SELECT_TODO_LIMIT_MONTH = "SELECT_TODO_LIMIT_MONTH";
    public static final String SELECT_TODO_LIMIT_DAY = "SELECT_TODO_LIMIT_DAY";
    public static final String SELECT_TODO_IS_COMPLETE = "SELECT_TODO_IS_COMPLETE";
    public static final String INTENT_KEY_REGISTER = "INTENT_KEY_REGISTER";
    public static final String INTENT_KEY_REGISTER_NEW = "INTENT_KEY_REGISTER_NEW";

    private static final String TAG = "TodoDetailFragment";
    TodoDao todoDao;

    private int todoId;

    private Spinner spinnerLimit;
    private EditText editTextTitle;
    private EditText editTextDetail;
    private Button buttonComplete;
    private Button buttonDelete;
    private TextView textViewLimit;
    private String LIMIT_TYPE_APPOINT = "日付を指定";
    private FragmentManager fragmentManager;
    private DialogFragment dialogFragment;
    private Button buttonSave;
    private Button buttonResetting;
    private TextView textViewTitle;
    private Fragment myFragment;
    private String limitSomeTimeString;
    private boolean isAutoSave = false;
    Resources res;
    TodoInfo beforeTodoInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferenceDataHelper sharedPreferenceData = new SharedPreferenceDataHelper();
        isAutoSave = sharedPreferenceData.isAutoSave(getActivity());

        todoDao = new TodoDao(getActivity());
    }

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
        spinnerLimit = (Spinner) getView().findViewById(R.id.spinner_limit);
        editTextTitle = (EditText) getView().findViewById(R.id.editText_title);
        editTextDetail = (EditText) getView().findViewById(R.id.editText_detail);
        textViewLimit = (TextView) getView().findViewById(R.id.textView_limitSelect);
        buttonComplete = (Button) getView().findViewById(R.id.button_complete);
        buttonDelete = (Button) getView().findViewById(R.id.button_delete);
        buttonSave = (Button) getView().findViewById(R.id.button_save);
        buttonResetting = (Button) getView().findViewById(R.id.button_todo_resetting);
        textViewTitle = (TextView) getView().findViewById(R.id.textView_title);
        myFragment = this;


        // リスト画面から取得したインデックスを取得
        Intent intent = getActivity().getIntent();
        Bundle arguments = intent.getExtras();
        Log.d(TAG, "■■todoId確認(Detail arguments)■■" + arguments.getInt(TodoDetailFragment.SELECT_TODO_ID));
        Log.d(TAG, "■■完了フラグ確認1(Detail arguments)■■" + arguments.getInt(TodoDetailFragment.SELECT_TODO_IS_COMPLETE));
        todoId = arguments.getInt(TodoDetailFragment.SELECT_TODO_ID);
        Log.d(TAG, "onViewCreated todoIdの値:" + todoId);

        // TODO期限種類をスピナーに設定
        // ArrayAdapterインスタンスをResourceXMLから取得してTODOメッセージの選択内容を作成
        ArrayAdapter<CharSequence> adapterLimit = ArrayAdapter.createFromResource(
                getActivity(), R.array.todo_limit,
                android.R.layout.simple_spinner_item);
        adapterLimit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLimit.setAdapter(adapterLimit);

        TextView toolBar = (TextView) getActivity().findViewById(R.id.textView_todo_detail_toolbar);
        res = getResources();

        // 期限種別の文字列（未定）を取得（xmlに定義した期限種別の最後が「未定」）
        TypedArray ta = res.obtainTypedArray(R.array.todo_limit);
        limitSomeTimeString = ta.getString(ta.length() - 1);

        // ##### 新規登録画面 #####
        // 新規登録時は完了ボタン、削除ボタン、再登録ボタンと期限（テキスト）は非表示
        if (todoId == -1) {
            buttonComplete.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonResetting.setVisibility(View.INVISIBLE);
            textViewLimit.setVisibility(View.INVISIBLE);


            // 保存ボタンを活性化する
            buttonSave.setEnabled(true);

            // 各要素を編集可能にする
            editTextTitle.setEnabled(true);
            editTextDetail.setEnabled(true);
            spinnerLimit.setEnabled(true);
            textViewLimit.setEnabled(true);

            // ツールバーを変更
            toolBar.setText(res.getString(R.string.todo_detail_toolbar_register));

            // ****************** 戻るボタン押下時の処理 ******************
            view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentManager = getActivity().getSupportFragmentManager();

                    // 自動保存が無効かつtodoが入力されている場合は確認ダイアログを表示する
                    TodoInfo currentTodoInfo = getScreenValue();

                    if (!isAutoSave && currentTodoInfo.isInputTodoInfo()) {

                        DialogFragment backConfDialogFragment = DetailBackConfirmDialogFragment.newInstance(
                                R.string.todo_detail_back_confirm_title);

                        // ダイアログに呼び出し元のFragmentオブジェクトを設定
                        backConfDialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoDetail.getInt());
                        backConfDialogFragment.show(fragmentManager, "insertBack");
                    } else {
                        // 当画面のActivityを終了する
                        getActivity().finish();
                    }
                }
            });

            // ##### 更新画面 #####
            // 一覧から渡された情報をセット（期限種別が未定以外の場合は日付指定扱い）
        } else {
            // ボタンのラベルを変更
            buttonSave.setText(res.getString(R.string.todo_detail_update));
            // ツールバーを変更
            toolBar.setText(res.getString(R.string.todo_detail_toolbar_update));
            final String selectedTodoTitle = arguments.getString(TodoDetailFragment.SELECT_TODO_TITLE);
            Log.d(TAG, "SELECT_TODO_TITLEの値:" + selectedTodoTitle);
            editTextTitle.setText(selectedTodoTitle);
            final String selectedTodoDetail = arguments.getString(TodoDetailFragment.SELECT_TODO_DETAIL);
            editTextDetail.setText(selectedTodoDetail);
            int selectedTodoLimitYear = arguments.getInt(TodoDetailFragment.SELECT_TODO_LIMIT_YEAR);
            int selectedTodoLimitMonth = arguments.getInt(TodoDetailFragment.SELECT_TODO_LIMIT_MONTH);
            int selectedTodoLimitDay = arguments.getInt(TodoDetailFragment.SELECT_TODO_LIMIT_DAY);
            String limitStr = formatLimitString(selectedTodoLimitYear, selectedTodoLimitMonth, selectedTodoLimitDay);
            textViewLimit.setText(limitStr);

//            TypedArray ta = res.obtainTypedArray(R.array.todo_limit);
//            String limitSomeTimeString = ta.getString(ta.length() - 1);
            // 期限種別が未定かどうかチェック
            // 未定の場合
            if (limitSomeTimeString.equals(limitStr)) {
                spinnerLimit.setSelection(adapterLimit.getPosition(limitSomeTimeString));
            } else {
                Log.d(TAG, "★★★spinnerLimitに「日付指定」を設定する★★★");
                Log.d(TAG, "★★★textViewLimitのフォーカス状況：" + textViewLimit.hasFocus() + "★★★");
                Log.d(TAG, "★★★textViewTitleのフォーカス状況：" + textViewTitle.hasFocus() + "★★★");
                Log.d(TAG, "★★★spinnerLimitのフォーカス状況：" + spinnerLimit.hasFocus() + "★★★");
                spinnerLimit.setOnItemSelectedListener(null);
                // 日付指定を設定
                // 第2引数にfalseを設定して、onItemSelectedが呼ばれるのを回避
                spinnerLimit.setSelection(adapterLimit.getPosition(LIMIT_TYPE_APPOINT), false);
                Log.d(TAG, "★★★spinnerLimitにsetSelectionを設定する★★★");
            }

            // 完了済TODOの遷移の場合は入力項目や保存ボタンなどを非活性にする
            if (TodoInfo.CompleteStatus.Complete.getInt() == arguments.getInt(TodoDetailFragment.SELECT_TODO_IS_COMPLETE)) {
                Log.d(TAG, "■■完了TODOの場合■■");
                buttonSave.setEnabled(false);
                buttonComplete.setEnabled(false);
                editTextTitle.setEnabled(false);
                editTextDetail.setEnabled(false);
                spinnerLimit.setEnabled(false);
                textViewLimit.setEnabled(false);
                textViewLimit.setTextColor(Color.parseColor("gray"));
                // 再登録と削除は活性化
                buttonDelete.setEnabled(true);
                buttonResetting.setEnabled(true);
                Log.d(TAG, "■■完了TODOの場合の活性・非活性を設定■■");
            }
            // 画面表示後の入力内容を取得
            beforeTodoInfo = getScreenValue();

            // ****************** 戻るボタン押下時の処理 ******************
            view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentManager = getActivity().getSupportFragmentManager();

                    // 自動保存が無効かつtodoが更新画面表示時から変更されている場合は確認ダイアログを表示する
                    TodoInfo currentTodoInfo = getScreenValue();
                    if (!isAutoSave && currentTodoInfo.isChangeTodoInfo(beforeTodoInfo)) {

                        DialogFragment backConfDialogFragment = DetailBackConfirmDialogFragment.newInstance(
                                R.string.todo_detail_back_confirm_title);
//                                R.string.wifi_confirm_dialog_title, R.string.wifi_confirm_dialog_message);

                        // ダイアログに呼び出し元のFragmentオブジェクトを設定
                        backConfDialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoDetail.getInt());
                        backConfDialogFragment.show(fragmentManager, "detailBack");
                    } else {
                        // 当画面のActivityを終了する
                        getActivity().finish();
                    }
                }
            });

            // ****************** 削除ボタン押下時の処理 ******************
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "★★★削除ボタンのリスナー設定★★★");

                    fragmentManager = getActivity().getSupportFragmentManager();
                    dialogFragment = new DeleteConfirmDialogFragment();

                    Bundle args = new Bundle();
                    // 削除対象のTODOのID
                    args.putInt(DeleteConfirmDialogFragment.TODO_ID, todoId);
                    dialogFragment.setArguments(args);
                    // ダイアログに呼び出し元のFragmentオブジェクトを設定
                    dialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoDetail.getInt());

                    dialogFragment.show(fragmentManager, "delete");

                }
            });

            // ****************** 完了ボタン押下時の処理 ******************
            buttonComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "★★★完了ボタンのリスナー設定★★★");

                    new AlertDialog.Builder(getActivity())
                            .setTitle(res.getString(R.string.message_todo_complete_confirm))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // 完了を設定
                                    long rtn = todoDao.complete(todoId);
                                    if (rtn == -1) {
                                        Log.d(TAG, "todoDao update結果：TODOの完了に失敗しました");
                                    } else {
                                        Log.d(TAG, "todoDao update結果：TODOを完了しました");
                                    }

                                    // TODO 完了時のダイアログ表示
                                    // 完了時のダイアログを表示する
                                    fragmentManager = getActivity().getSupportFragmentManager();
                                    dialogFragment = new CompleteImgDialogFragment();
                                    dialogFragment.show(fragmentManager, "complete");

                                    // 保存、完了ボタンを非活性にする
                                    buttonSave.setEnabled(false);
                                    buttonComplete.setEnabled(false);
                                    // 入力項目を非活性にする
                                    editTextTitle.setEnabled(false);
                                    editTextDetail.setEnabled(false);
                                    spinnerLimit.setEnabled(false);
                                    textViewLimit.setEnabled(false);
                                    // 再登録ボタンを活性化する
                                    buttonResetting.setEnabled(true);
                                    // カラムが無効とわかる文字色に変更
                                    textViewLimit.setTextColor(Color.parseColor("gray"));

                                }
                            })
                            .setNeutralButton("CANCEL", null)
                            .show();
                }
            });

            // ****************** 再登録ボタン押下時の処理 ******************
            buttonResetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "★★★再登録ボタンのリスナー設定★★★");
                    // TODOを再登録
                    long rtn = todoDao.reRegister(todoId);
                    if (rtn == -1) {
                        Log.d(TAG, "todoDao update結果：TODOの再登録に失敗しました");
                        Toast.makeText(getActivity(), res.getString(R.string.message_register_fail), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "todoDao update結果：TODOを再登録しました");
                        Toast.makeText(getActivity(), res.getString(R.string.message_register_success), Toast.LENGTH_SHORT).show();
                    }

                    // 保存、完了、削除ボタンを活性化する
                    buttonSave.setEnabled(true);
                    buttonComplete.setEnabled(true);
                    buttonDelete.setEnabled(true);
                    // 再登録ボタンを非活性にする
                    buttonResetting.setEnabled(false);

                    // 各要素を編集可能にする（TODO一覧の完了済を再登録したとき用）
                    editTextTitle.setEnabled(true);
                    editTextDetail.setEnabled(true);
                    spinnerLimit.setEnabled(true);
                    textViewLimit.setEnabled(true);
                    textViewLimit.setTextColor(Color.parseColor("black"));
                }
            });
        }
        // これ以降は新規と更新で共通のリスナー
        // ****************** 期限種別のスピナー変更時の動作 ******************
        spinnerLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // アイテムが選択された時の動作
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                Log.d(TAG, "★★★spinnerLimitのリスナー設定★★★");
                Log.d(TAG, "★★★textViewLimitのフォーカス状況：" + textViewLimit.hasFocus() + "★★★");
                Log.d(TAG, "★★★textViewTitleのフォーカス状況：" + textViewTitle.hasFocus() + "★★★");
                Log.d(TAG, "★★★spinnerLimitのフォーカス状況：" + spinnerLimit.hasFocus() + "★★★");
                // Spinnerを取得
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムのテキスト
                String selectedLimit = spinner.getSelectedItem().toString();

                // スピナーの選択値が「日付を指定」の場合
                if (LIMIT_TYPE_APPOINT.equals(selectedLimit)) {
                    Log.d(TAG, "==日付指定を選択==");

                    String limitString = textViewLimit.getText().toString();
                    Log.d(TAG, "★★★textViewLimitの値を確認！！！：" + textViewLimit.getText() + "★★★");
                    // 現在の期限の設定情報がない(新規登録)または期限が「未定」の場合はカレンダーダイアログ表示
                    if (!TodoCommonFunction.isValidValue(limitString) || limitSomeTimeString.equals(limitString)) {
                        showCalendarDialog();
                    }
                    textViewLimit.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "==日付指定以外を選択==");
                    textViewLimit.setVisibility(View.GONE);
                }
            }

            // 何も選択されたかった時の動作
            public void onNothingSelected(AdapterView parent) {
            }
        });

        // ****************** 期限テキスト押下時の処理 ******************
        textViewLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "★★★textViewLimitのリスナー設定★★★");
                Log.d(TAG, "★★★textViewLimitのフォーカス状況：" + textViewLimit.hasFocus() + "★★★");
                Log.d(TAG, "★★★textViewTitleのフォーカス状況：" + textViewTitle.hasFocus() + "★★★");
                // カレンダーダイアログ表示
                showCalendarDialog();
            }
        });

        // ****************** 保存ボタン押下時の処理 ******************
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 画面入力情報を取得
                TodoInfo todoSetInfo = getScreenValue();
                TodoLimit todoLimit = todoSetInfo.getTodoLimit();

                Log.d(TAG, "getScreenValue取得結果⇒"
                        + "id: " + todoSetInfo.getId()
                        + ", title: " + todoSetInfo.getTitle()
                        + ", detail: " + todoSetInfo.getDetail()
                        + ", isComplete: " + todoSetInfo.getIsComplete());
                if (todoLimit == null) {
                    Log.d(TAG, "getScreenValue(TODO期限)取得結果⇒null");
                } else {
                    Log.d(TAG, "getScreenValue(TODO期限)取得結果⇒"
                            + "year:[" + todoLimit.getYear() + "]"
                            + "month:[" + todoLimit.getMonth() + "]"
                            + "day:[" + todoLimit.getDay() + "]"
                    );
                }

                // 入力値チェック
                String result = checkInputVal(todoSetInfo);
                if (result != null) {
                    // 入力チェックエラー内容を出力
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 新規登録時はINSERT
                if (todoId == -1) {
                    long rtn = todoDao.insertTodoInfo(todoSetInfo);

                    if (rtn == -1) {
                        Log.d(TAG, "todoDao insert結果：TODOの登録に失敗しました");
                    } else {
                        Log.d(TAG, "todoDao insert結果：TODOを登録しました");
                    }

                    // Main画面に戻った時に新規登録からの戻りかどうかの判断用に設定
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_KEY_REGISTER, INTENT_KEY_REGISTER_NEW);
                    getActivity().setResult(RESULT_OK, intent);

                    // 編集時はUPDATE
                } else {
                    long rtn = todoDao.updateTodoInfo(todoSetInfo);

                    if (rtn == -1) {
                        Log.d(TAG, "todoDao update結果：TODOの更新に失敗しました");
                    } else {
                        Log.d(TAG, "todoDao update結果：TODOを更新しました");
                    }
                }

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });

//        // 戻るボタン押下時の処理
//        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 当画面のActivityを終了する
//                getActivity().finish();
//            }
//        });
    }

    /**
     * ダイアログからのコールバックに使用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TodoConstant.RequestCode.TodoDetail.getInt()) {
            if (resultCode != RESULT_OK) {
                return;
            }

            Toast.makeText(getActivity(), res.getString(R.string.message_todo_delete), Toast.LENGTH_SHORT).show();
            // 当画面のActivityを終了する
            getActivity().finish();

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();

        // 自動保存設定の場合は入力データを保存する
        if (!isAutoSave) {
            return;
        }

        String title = editTextTitle.getText().toString();
        String detail = editTextDetail.getText().toString();
        if ((title.isEmpty()) && detail.isEmpty()) {
            return;
        }

        // 画面入力情報を取得
        TodoInfo todoSetInfo = getScreenValue();

        if (todoId == -1) {
            todoDao.insertTodoInfo(todoSetInfo);

            // Main画面に戻った時に新規登録からの戻りかどうかの判断用に設定
            Intent intent = new Intent();
            intent.putExtra(INTENT_KEY_REGISTER, INTENT_KEY_REGISTER_NEW);
            getActivity().setResult(RESULT_OK, intent);

        } else {
            todoDao.updateTodoInfo(todoSetInfo);
        }
    }

    /**
     * 画面入力値のチェック
     *
     * @param todoInfo
     * @return
     */
    private String checkInputVal(TodoInfo todoInfo) {
        String result = null;

        // ##### 必須入力項目チェック #####
        // 自動保存対応でタイトル未設定が設定されるのでタイトルチェックは実質行わない
        if (!TodoCommonFunction.isValidValue(todoInfo.getTitle())) {
            result = res.getString(R.string.message_validate_title_required);
        } else if (todoInfo.getTodoLimit() == null) {
            result = res.getString(R.string.message_validate_limit_required);
        }
        if (result != null) {
            return result;
        }

        // ##### 期限妥当性チェック #####
        // TODO期限と現在日付を比較
        TodoLimit todoLimit = todoInfo.getTodoLimit();
        final Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        if (todoLimit.getYear() < currentYear
                || (todoLimit.getYear() == currentYear && todoLimit.getMonth() < currentMonth)
                || (todoLimit.getYear() == currentYear && todoLimit.getMonth() == currentMonth && todoLimit.getDay() < currentDay)) {
            result = res.getString(R.string.message_validate_limit_past);
        }

        return result;
    }

    /**
     * 画面入力情報を取得
     *
     * @return
     */
    private TodoInfo getScreenValue() {

        // 期限
        TodoLimit todoLimit;
        Object limitType = spinnerLimit.getSelectedItem();
        // 新規登録でスピナーの入力値が「日付入力」以外の場合は取得した日付タイプから日付を取得
        // 編集のとき、または新規登録でスピナーの入力値が「日付入力」の場合は日付のeditTextから取得
        if (limitType == null || limitType.toString().equals(LIMIT_TYPE_APPOINT)) {
            // 日付のテキスト情報から日付入力値を取得
            todoLimit = TodoCommonFunction.textToTodoLimit(textViewLimit.getText().toString());
        } else {
            // スピナーで選択した日付種別から期限を取得する
            todoLimit = TodoCommonFunction.selectedToTodoLimit(limitType.toString());
            Log.d(TAG, "スピナーの値：" + limitType.toString());
            Log.d(TAG, "スピナーから取得した日付⇒：" + todoLimit.getYear() + "年" + todoLimit.getMonth() + "月" + todoLimit.getDay() + "日");
        }

        // TODO設定内容を返却（TODOスイッチは固定でONを設定）
        return new TodoInfo(todoId,
                todoLimit,
                editTextTitle.getText().toString(),
                editTextDetail.getText().toString(),
                TodoInfo.CompleteStatus.NotComplete.getInt()
        );
    }

    // カレンダーダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setCalendar(String value) {
        //TextView textView = (TextView) findViewById(R.id.text);
        textViewLimit.setText(value);
    }

    /**
     * カレンダーダイアログを表示する
     */
    private void showCalendarDialog() {
        fragmentManager = getActivity().getSupportFragmentManager();
        dialogFragment = new CalendarDialogFragment();

        // カレンダーダイアログに期限の入力値を渡す
        String todoLimit = textViewLimit.getText().toString();
        // 期限が空の場合は今日日付を期限に設定する
        if (todoLimit == null) {
            final Calendar calendar = Calendar.getInstance();
            todoLimit = TodoCommonFunction.formatLimitString(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            textViewLimit.setText(todoLimit);
        }

        Bundle args = new Bundle();
        args.putString(TODO_LIMIT_KEY, todoLimit);
        dialogFragment.setArguments(args);

        // ダイアログに呼び出し元のFragmentオブジェクトを設定
        dialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoDetail.getInt());
        dialogFragment.show(fragmentManager, "calendar");
    }

    /**
     * 画面情報を設定
     */
    private void setScreenValue(TodoInfo todoInfo) {

        editTextTitle.setText(todoInfo.getTitle());
        editTextDetail.setText(todoInfo.getDetail());
        textViewLimit.setText(formatLimitString(todoInfo.getTodoLimit()));
    }

    /**
     * 少しwaitして当画面のActivityを終了する
     */
    public void screenFinish() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 300);
    }
}
