package apl.r_m_unt.todosupportlady.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import apl.r_m_unt.todosupportlady.dialog.CompleteImgDialogFragment;
import apl.r_m_unt.todosupportlady.dialog.DeleteConfirmDialogFragment;
import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.TodoConstant;
import apl.r_m_unt.todosupportlady.model.SharedPreferenceDataHelper;
import apl.r_m_unt.todosupportlady.model.TodoDao;

import static apl.r_m_unt.todosupportlady.common.TodoCommonFunction.formatLimitString;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_DETAIL;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_ID;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_IS_COMPLETE;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_DAY;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_MONTH;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_YEAR;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_TITLE;

/**
 * TODOリスト画面のフラグメント
 */
public class TodoListFragment extends ListFragment {

    private static final String TAG = "TodoListFragment";
    TodoInfoAdapter todoInfoAdapter = null;

    private ListView todoListView;
    private Button buttonAdd;
    private Button buttonBack;
    private FragmentManager fragmentManager;
    private DialogFragment dialogFragment;
    private List<TodoInfo> todoInfoList;
    private Fragment myFragment;
    private Switch switchCompleteShow;

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoListView = getListView();
        myFragment = this;

        // 追加ボタン押下時の処理
        buttonAdd = (Button)getView().findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO詳細画面へ遷移
                Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
                todoDetailIntent.putExtra(SELECT_TODO_ID, -1);
                startActivity(todoDetailIntent);
            }
        });

        // 戻るボタン押下時の処理
        buttonBack = (Button)getView().findViewById(R.id.button_todo_list_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // 当画面のActivityを終了する
            getActivity().finish();
            }
        });

        /**
         * リストアイテムのロングタップ時
         */
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Listのロングタップ");

                fragmentManager = getActivity().getSupportFragmentManager();
                dialogFragment = new DeleteConfirmDialogFragment();

                TodoInfo todoInfo = todoInfoList.get(position);
                Bundle args = new Bundle();
                // 削除対象のTODOのIDを設定
                args.putInt(DeleteConfirmDialogFragment.TODO_ID, todoInfo.getId());
                // 削除対象のTODOのタイトルを設定
                args.putString(DeleteConfirmDialogFragment.TODO_TITLE, todoInfo.getTitle());
                dialogFragment.setArguments(args);
                // ダイアログに呼び出し元のFragmentオブジェクトを設定
                dialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoList.getInt());

                dialogFragment.show(fragmentManager, "delete");

                // 残りのイベントの処理はしない
                return true;
            }
        });

        // 完了済表示設定の保存値を読み込んで設定
        SharedPreferenceDataHelper sharedPreferenceData = new SharedPreferenceDataHelper();
        switchCompleteShow = (Switch)getView().findViewById(R.id.switch_complete_show);
        switchCompleteShow.setChecked(sharedPreferenceData.isShowCompleted(getActivity()));

        /**
         * TODO完了表示 切替スイッチをタップ時の処理
         */
        switchCompleteShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 完了済表示設定を保存
                SharedPreferenceDataHelper sharedPreferenceData = new SharedPreferenceDataHelper();
                sharedPreferenceData.setShowCompleted(getActivity(), isChecked);
                // 一覧を再表示
                setTodoInfoList();
            }
        });
    }

    // ダイアログの戻り値取得時の処理
    public void onReturnValue(String value) {
        Log.d(TAG, "ダイアログの戻り値:"+"["+ value +"]");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "TodoListFragment onResume起動");

        // TODO一覧表示
        setTodoInfoList();
    }

    /**
     * TODO一覧の設定
     */
    public void setTodoInfoList() {
        // 完了済の表示フラグをスイッチの状態から取得してTODO一覧を取得する
        TodoDao todoDao = new TodoDao(getActivity());
        todoInfoList = todoDao.getTodoInfo(isCompleteShow());

        todoInfoAdapter = new TodoInfoAdapter(getActivity(), 0, todoInfoList);
        setListAdapter(todoInfoAdapter);

    }

    /**
     * ダイアログからのコールバックに使用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TodoConstant.RequestCode.TodoList.getInt()) {

            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            Toast.makeText(getActivity(), getResources().getString(R.string.message_todo_delete), Toast.LENGTH_SHORT).show();

            // TODO一覧の再設定
            setTodoInfoList();

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * アイテムのクリック(TODO明細へ遷移させる)
     *
     * @see
     * android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView
     * , android.view.View, int, long)
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick position => " + position + " : id => " + id);

        // TODO情報一覧更新時に保持していた一覧からTODOを取得
        TodoInfo todoInfo = todoInfoList.get(position);

        Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SELECT_TODO_ID, todoInfo.getId());
        bundle.putString(SELECT_TODO_TITLE, todoInfo.getTitle());
        bundle.putString(SELECT_TODO_DETAIL, todoInfo.getDetail());

        TodoLimit todoLimit = todoInfo.getTodoLimit();
        bundle.putInt(SELECT_TODO_LIMIT_YEAR, todoLimit.getYear());
        bundle.putInt(SELECT_TODO_LIMIT_MONTH, todoLimit.getMonth());
        bundle.putInt(SELECT_TODO_LIMIT_DAY, todoLimit.getDay());

        Log.d(TAG, "■■完了フラグ確認1(List)■■" + todoInfo.getIsComplete());
        bundle.putInt(SELECT_TODO_IS_COMPLETE, todoInfo.getIsComplete());
        Log.d(TAG, "■■完了フラグ確認2(List)■■" + todoInfo.getIsComplete());
        todoDetailIntent.putExtras(bundle);
        startActivity(todoDetailIntent);
    }


    /**
     * TODO一覧表示用
     */
    public class TodoInfoAdapter extends ArrayAdapter<TodoInfo> {

        // 期限切れのチェックのために現在日時を取得する
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // レイアウトxmlファイルからIDを指定してViewが使用可能
        private LayoutInflater mLayoutInflater;

        public TodoInfoAdapter(Context context, int resourceId, List<TodoInfo> objects) {
            super(context, resourceId, objects);
            // getLayoutInflater()メソッドはActivityじゃないと使えない
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // getView()メソッドは各行を表示しようとした時に呼ばれる
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 各行のデータ
            View rowView = convertView;

            // 特定行(position)のデータを得る
            TodoInfo item = (TodoInfo)getItem(position);
            // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
            if (null == rowView) rowView = mLayoutInflater.inflate(R.layout.list_item, null);

            // TodoInfoのデータをViewの各Widgetにセットする
            // TODO情報インデックス（非表示）
            TextView textViewListIndex = (TextView)rowView.findViewById(R.id.textView_listTodoIndex);
            Log.i(TAG, "textViewListIndexの値："+textViewListIndex);
            Log.i(TAG, "itemの値："+item);
            textViewListIndex.setText(String.valueOf(item.getId()));

            // TODO期限
            TextView textViewListTodoTime = (TextView)rowView.findViewById(R.id.textView_listTodoLimit);
            TodoLimit todoLimit = item.getTodoLimit();
            textViewListTodoTime.setText(formatLimitString(todoLimit));

            // 期限のラベルを取得
            TextView limitLabel = (TextView)rowView.findViewById(R.id.textView_listTodoLimitLabel);

            // TODO完了済の場合は期限のテキストを黒色にする
            if (item.getIsComplete() == TodoInfo.CompleteStatus.Complete.getInt()){
                textViewListTodoTime.setTextColor(Color.BLACK);
                limitLabel.setTextColor(Color.BLACK);

                // 上記以外
            } else {
                // 期限切れの場合は期限のテキストの色を赤くする
                if (todoLimit.getYear() < year
                        || (todoLimit.getYear() == year && todoLimit.getMonth() < month)
                        || (todoLimit.getYear() == year && todoLimit.getMonth() == month && todoLimit.getDay() < day)) {
                    Log.i(TAG, "年：" + todoLimit.getYear() + "現在日時（年）" + year);
                    Log.i(TAG, "月：" + todoLimit.getMonth() + "現在日時（月）" + month);
                    Log.i(TAG, "日：" + todoLimit.getDay() + "現在日時（日）" + day);
                    textViewListTodoTime.setTextColor(Color.RED);
                    limitLabel.setTextColor(Color.RED);

                    // 上記以外は期限のテキストの色を青色にする
                } else {
                    textViewListTodoTime.setTextColor(Color.BLUE);
                    limitLabel.setTextColor(Color.BLUE);
                }
            }

            // TODOタイトル
            TextView textViewTodoTitle = (TextView)rowView.findViewById(R.id.textView_listTodoTitle);
            textViewTodoTitle.setText(item.getTitle());

            // 完了ボタン
            Button buttonComplete = (Button)rowView.findViewById(R.id.button_listTodoComplete);
            // 完了済のTODOはボタンを非活性にし、ラベルを完了済にする
            if (item.getIsComplete() == TodoInfo.CompleteStatus.Complete.getInt()){
                buttonComplete.setEnabled(false);
                buttonComplete.setText(getString(R.string.todo_list_complete_done));

                // 上記以外
            } else {
                buttonComplete.setEnabled(true);
                buttonComplete.setText(getString(R.string.todo_list_complete));
            }
            // ---------- 初回のみイベント登録 ----------
            // 完了ボタン押下時の処理
            if (buttonComplete.getTag() == null) {
                buttonComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 最新のpositionを取得
                        int position = (int)v.getTag();
                        // 上記positionからデータを取得
                        TodoInfo todoInfo = todoInfoAdapter.getItem(position);
                        Log.d(TAG, "imageButtonDelete onClick呼ばれた" + "position:" + position);

                        // TODOを完了する
                        // TODOモデルの取得
                        TodoDao todoDao = new TodoDao(getActivity());
                        // 完了を設定
                        long rtn = todoDao.complete(todoInfo.getId());
                        if (rtn == -1) {
                            Log.d(TAG, "todoDao update結果：TODOの完了に失敗しました");
                        } else {
                            Log.d(TAG, "todoDao update結果：TODOを完了しました");
                        }

                        // 完了時の画像ダイアログを表示する
                        fragmentManager = getActivity().getSupportFragmentManager();
                        dialogFragment = new CompleteImgDialogFragment();
                        dialogFragment.show(fragmentManager, "complete");

                        setTodoInfoList();
                    }
                });
            }

            // 最新のpositionをOnクリックイベントで使用するために保存する
            buttonComplete.setTag(position);

            return rowView;
        }
    }

    private boolean isCompleteShow() {
        return switchCompleteShow.isChecked();
    }
}