package apl.r_m_unt.todosupportlady.todo;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.CompleteImgDialogFragment;
import apl.r_m_unt.todosupportlady.DeleteConfirmDialogFragment;
import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.TodoConstant;
import apl.r_m_unt.todosupportlady.common.CommonFunction;
import apl.r_m_unt.todosupportlady.preferences.TodoController;

import static android.app.Activity.RESULT_OK;
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
    public static final String INTENT_KEY_REGISTER = "INTENT_KEY_REGISTER";
    public static final String INTENT_KEY_REGISTER_NEW = "INTENT_KEY_REGISTER_NEW";

    private static final String TAG = "TodoDetailFragment";

    private int todoId;
    private TodoController todoController;
    //private TextView textViewTodoId;

    private Spinner spinnerLimit;
    private DatePicker datePickerLimit;
    private EditText editTextTitle;
    private EditText editTextDetail;
    private Button buttonComplete;
    private Button buttonDelete;
    private EditText editTextLimit;
    private String appointLimitType = "日付を指定";
    private FragmentManager fragmentManager;
    private DialogFragment dialogFragment;
    private Button buttonSave;
    private Button buttonResetting;
    private Fragment myFragment;
//    private Fragment mainFragment;

//    // ClassCastException対応
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        // 遷移元のFragmentをチェック
//        final Fragment fragment = getTargetFragment();
//        if (fragment != null) {
//            if(fragment instanceof MainFragment) {
//                mainFragment = (MainFragment) fragment;
//            }
//        }
//    }
//
//    /**
//     * リーク対策
//     */
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mainFragment = null;
//    }

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
        editTextLimit = (EditText)getView().findViewById(R.id.editText_limit);
        buttonComplete = (Button)getView().findViewById(R.id.button_complete);
        buttonDelete = (Button)getView().findViewById(R.id.button_delete);
        buttonSave = (Button)getView().findViewById(R.id.button_save);
        buttonResetting = (Button)getView().findViewById(R.id.button_todo_resetting);
        myFragment = this;

        // スクロールバー表示を可能にする
        //editTextDetail.setMovementMethod(ScrollingMovementMethod.getInstance());

        // リスト画面から取得したインデックスを取得
        Bundle arguments =  getArguments();
        todoId = arguments.getInt(TodoDetailFragment.SELECT_TODO_ID);
        Log.d(TAG, "onViewCreated todoIdの値:" + todoId);

        // 新規登録時は完了ボタン、削除ボタン、再登録ボタンと期限（テキスト）は非表示
        if (todoId == -1) {
            buttonComplete.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonResetting.setVisibility(View.INVISIBLE);
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

        // ****************** 保存ボタン押下時の処理 ******************
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 画面入力情報を取得
                TodoInfo todoSetInfo = getScreenValue();

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

                // TODOモデルの取得
                TodoModel todoModel = new TodoModel(getActivity());

                // 新規登録時はINSERT
                if (todoId == -1) {
                    long rtn = todoModel.insertTodoInfo(todoSetInfo);

                    if (rtn == -1) {
                        Log.d(TAG, "todoModel insert結果：TODOの登録に失敗しました");
                    } else {
                        Log.d(TAG, "todoModel insert結果：TODOを登録しました");
                    }

                    // Main画面に戻った時に新規登録からの戻りかどうかの判断用に設定
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_KEY_REGISTER, INTENT_KEY_REGISTER_NEW);
                    getActivity().setResult(RESULT_OK, intent);

                    // 編集時はUPDATE
                } else {
                    long rtn = todoModel.updateTodoInfo(todoSetInfo);

                    if (rtn == -1) {
                        Log.d(TAG, "todoModel update結果：TODOの更新に失敗しました");
                    } else {
                        Log.d(TAG, "todoModel update結果：TODOを更新しました");
                    }
//                    // 当画面のActivityを終了する
//                    getActivity().finish();
                }

                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });

        // ****************** 削除ボタン押下時の処理 ******************
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // TODOモデルの取得
//                TodoModel todoModel = new TodoModel(getActivity());
//                // 削除を設定
//                long rtn = todoModel.updateIsDelete(todoId, TodoInfo.DELETE);
//                if (rtn == -1) {
//                    Log.d(TAG, "todoModel update結果：TODOの削除に失敗しました");
//                } else {
//                    Log.d(TAG, "todoModel update結果：TODOを削除しました");
//                }
                fragmentManager = getActivity().getSupportFragmentManager();
                dialogFragment = new DeleteConfirmDialogFragment();

                Bundle args = new Bundle();
                // 削除対象のTODO_ID
                args.putInt(DeleteConfirmDialogFragment.TODO_ID, todoId);
//                // TODO詳細からの遷移であることを設定してダイアログ呼び出し
//                args.putInt(DeleteConfirmDialogFragment.TRANSITION_SOURCE_CD, DeleteConfirmDialogFragment.TransitionSource.TodoList.getInt());
                dialogFragment.setArguments(args);
                // ダイアログに呼び出し元のFragmentオブジェクトを設定
                dialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoDetail.getInt());

                dialogFragment.show(fragmentManager, "delete");

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

        // ****************** 完了ボタン押下時の処理 ******************
        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODOモデルの取得
                TodoModel todoModel = new TodoModel(getActivity());
                // 完了を設定
                long rtn = todoModel.complete(todoId);
                if (rtn == -1) {
                    Log.d(TAG, "todoModel update結果：TODOの完了に失敗しました");
                } else {
                    Log.d(TAG, "todoModel update結果：TODOを完了しました");
                }

                // TODO 完了時のダイアログ表示
                // 完了時のダイアログを表示する
                fragmentManager = getActivity().getSupportFragmentManager();
                dialogFragment = new CompleteImgDialogFragment();
                dialogFragment.show(fragmentManager, "complete");

                // 保存ボタンを非活性にする
                buttonSave.setEnabled(false);
                // 完了ボタンを非活性
                buttonComplete.setEnabled(false);
                // 削除ボタンを非活性
                buttonDelete.setEnabled(false);
                // 再登録ボタンを表示する
                buttonResetting.setVisibility(View.VISIBLE);

                // 当画面のActivityを終了する
                //getActivity().finish();
            }
        });

        // ****************** 再登録ボタン押下時の処理 ******************
        buttonResetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODOモデルの取得
                TodoModel todoModel = new TodoModel(getActivity());
                // TODOを再登録
                long rtn = todoModel.reRegister(todoId);
                if (rtn == -1) {
                    Log.d(TAG, "todoModel update結果：TODOの再登録に失敗しました");
                } else {
                    Log.d(TAG, "todoModel update結果：TODOを再登録しました");
                }

                // 保存ボタンを活性化する
                buttonSave.setEnabled(true);
                // 完了ボタンを活性化する
                buttonComplete.setEnabled(true);
                // 削除ボタンを活性化する
                buttonDelete.setEnabled(true);
                // 再登録ボタンを非表示にする
                buttonResetting.setVisibility(View.GONE);
            }
        });
    }

    /**
     * ダイアログからのコールバックに使用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TodoConstant.RequestCode.TodoDetail.getInt()) {
            if (resultCode != RESULT_OK) { return; }

            // 保存ボタンを非活性にする
            buttonSave.setEnabled(false);
            // 完了ボタンを非活性
            buttonComplete.setEnabled(false);
            // 削除ボタンを非活性
            buttonDelete.setEnabled(false);
            // 再登録ボタンを表示する
            buttonResetting.setVisibility(View.VISIBLE);

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    private TodoInfo getScreenValue() {

        // 期限
        String todoLimit;
        Object limitType = spinnerLimit.getSelectedItem();
        // 新規登録でスピナーの入力値が「日付入力」以外の場合は取得した日付タイプから日付を取得
        // 編集のとき、または新規登録でスピナーの入力値が「日付入力」の場合はeditTextから取得
        if (limitType == null || limitType.toString().equals(appointLimitType)) {
            // 日付入力値を取得
            todoLimit = editTextLimit.getText().toString();
        } else {
            // スピナーで選択した日付種別から期限を取得する
            todoLimit = CommonFunction.getTodoLimit(limitType.toString());
        }

//        String limitType = spinnerLimit.getSelectedItem().toString();
//        // 編集モードの場合または日付入力を選択の場合は入力値をeditTextから取得
//        if (selectTodoId != -1 || appointLimitType.equals(limitType)){
//            todoLimit = editTextLimit.getText().toString();
//
//            if (todoLimit == null || todoLimit.isEmpty()) {
//                // 後でeditTextLimitから取得に集約する
//                int year = datePickerLimit.getYear();
//                int month = datePickerLimit.getMonth();
//                int day = datePickerLimit.getDayOfMonth();
//                //todoLimit = CommonFunction.formatDateFromString(year, month, day);
//                todoLimit = CommonFunction.formatDateString(year, month, day);
//            }
//
//        } else {
//            // スピナーの選択種別から期限を取得する
//            todoLimit = CommonFunction.getTodoLimit(limitType);
//        }

        // TODO設定内容を返却（TODOスイッチは固定でONを設定）
        return new TodoInfo(todoId,
                todoLimit,
                editTextTitle.getText().toString(),
                editTextDetail.getText().toString(),
                TodoInfo.CompleteStatus.NotComplete.getInt()
        );
    }


    /**
     * 画面情報を設定
     */
    private void setScreenValue(TodoInfo todoInfo) {

        //textViewTodoId.setText(todoInfo.getId());
        editTextTitle.setText(todoInfo.getTitle());
        editTextDetail.setText(todoInfo.getDetail());
        editTextLimit.setText(todoInfo.getLimit());
    }
}
