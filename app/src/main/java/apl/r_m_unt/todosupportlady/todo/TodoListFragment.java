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

import java.util.Calendar;
import java.util.List;

import apl.r_m_unt.todosupportlady.CompleteImgDialogFragment;
import apl.r_m_unt.todosupportlady.DeleteConfirmDialogFragment;
import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.TodoConstant;
import apl.r_m_unt.todosupportlady.config.SharedPreferenceData;

import static apl.r_m_unt.todosupportlady.common.TodoCommonFunction.formatLimitString;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_DETAIL;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_ID;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_IS_COMPLETE;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_DAY;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_MONTH;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_LIMIT_YEAR;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_TITLE;

//import android.app.DialogFragment;

/**
 * TODOリスト画面のフラグメント
 */
public class TodoListFragment extends ListFragment {

    private static final String TAG = "TodoListFragment";
    TodoInfoAdapter todoInfoAdapter = null;

//    private TodoController todoController;

//    private List<TodoSettingInfo> todoSettingInfoList;

    private int todoSelectIndex = 0;

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
//        todoController = TodoController.getInstance(getActivity().getApplicationContext());
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

//        // TODO一覧表示
//        setTodoInfoList();

////        Realm上手くいかない
//        // Realm全体の初期化
//        Realm.init(getActivity());
//
////        // Realmのカスタム設定の場合
////        RealmConfiguration realmConfig  = new RealmConfiguration.Builder(this).build();
////        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
////        Realm realm = Realm.getInstance(realmConfig);
//
//        // Realmのデフォルト設定の場合
//        Realm realm = Realm.getDefaultInstance();
//
//        // DBとアダプター、アダプターとビューの関連付けを行う
//        RealmResults<Task> tasks = realm.where(Task.class).findAll();
//        TaskAdapter todoAdapter = new TaskAdapter(getActivity(), tasks);
//        todoListView.setAdapter(todoAdapter);


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
                // 削除対象のTODO_IDを設定
                args.putInt(DeleteConfirmDialogFragment.TODO_ID, todoInfo.getId());
//                // TODO一覧からの遷移であることを設定してダイアログ呼び出し
//                args.putInt(DeleteDialogFragment.TRANSITION_SOURCE_CD, DeleteDialogFragment.TransitionSource.TodoDetail.getInt());
                dialogFragment.setArguments(args);
                // ダイアログに呼び出し元のFragmentオブジェクトを設定
                dialogFragment.setTargetFragment(myFragment, TodoConstant.RequestCode.TodoList.getInt());

                dialogFragment.show(fragmentManager, "delete");

                // 残りのイベントの処理はしない
                return true;
            }
        });

        // 完了済表示設定の保存値を読み込んで設定
        SharedPreferenceData sharedPreferenceData = new SharedPreferenceData();
        switchCompleteShow = (Switch)getView().findViewById(R.id.switch_complete_show);
        switchCompleteShow.setChecked(sharedPreferenceData.isShowCompleted(getActivity()));

        /**
         * TODO完了表示 切替スイッチをタップ時の処理
         */
        switchCompleteShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 完了済表示設定を保存
                SharedPreferenceData sharedPreferenceData = new SharedPreferenceData();
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

        // ここに日付変更フラグを入れて、日付が変わっていたらTODOの再読み込みを行う

////        // TODO設定情報リストを取得
//        todoSettingInfoList = todoController.getTodoSettingInfoList();
//        // TODO設定一覧に設定
//        todoInfoAdapter = new TodoInfoAdapter(getActivity(), 0, todoSettingInfoList);
//        setListAdapter(todoInfoAdapter);

    }

    /**
     * TODO一覧の設定
     */
    public void setTodoInfoList() {
        // 完了済の表示フラグをスイッチの状態から取得してTODO一覧を取得する
        TodoModel todoModel = new TodoModel(getActivity());
        todoInfoList = todoModel.getTodoInfo(isCompleteShow());

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

            // TODO一覧の再設定
            setTodoInfoList();

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    /**
//     * TODO情報リストの更新
//     */
//    public void updateTodoInfoList() {
//        //todoSettingInfoList = todoController.getTodoSettingInfoList();
//        todoInfoAdapter = new TodoInfoAdapter(getActivity(), 0, todoSettingInfoList);
//        setListAdapter(todoInfoAdapter);
//
//        // TODO情報の保存
//        todoController.saveInstance(getActivity().getApplicationContext());
//    }

    /*
     * TODOの追加
     */
//    public void addTodoInfo() {
//
//        // TODO情報の追加
//        //todoSettingInfoList = todoSetting.getAddedTodoSettingInfoList();
//        int todoIndex = todoController.addTodoSettingInfo();
//        Toast.makeText(this.getActivity().getApplicationContext(),"生成したインデックス：" + String.valueOf(todoIndex), Toast.LENGTH_SHORT).show();
//        //updateTodoInfoList();
//        // 追加したTODOの明細画面に遷移
//        int position = todoController.getTodoSetSize() - 1;
//        Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
//        todoDetailIntent.putExtra(TODO_SELECT_NO, position);
//        startActivity(todoDetailIntent);
//    }

    /*
     * TODOの更新
     */
//    public void updateTodoInfo(int listIndex, TodoSettingInfo todoSettingInfo) {
//
//        if (todoSelectIndex < 0 || todoController.getTodoSetSize() <= todoSelectIndex) {
//            return;
//        }
//
//        // 画面設定情報をTODO設定情報に反映
//        todoController.setTodoSettingInfo(listIndex, todoSettingInfo);
//        // 変更内容の反映
//        updateTodoInfoList();
//
//        // TODO実行の更新
////        TodoModel todoModel = new TodoModel(getActivity());
////        todoModel.setTodo(todoSettingInfo);
//
//
//    }

    /*
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
//        todoDetailIntent.putExtra(SELECT_TODO_ID, todoInfo.getId());
//        todoDetailIntent.putExtra(SELECT_TODO_TITLE, todoInfo.getTitle());
//        todoDetailIntent.putExtra(SELECT_TODO_DETAIL, todoInfo.getDetail());
//        todoDetailIntent.putExtra(SELECT_TODO_LIMIT, todoInfo.getLimit());
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

            // 期限切れの場合は期限のテキストの色を赤くする
            if (todoLimit.getYear() < year
                    || (todoLimit.getYear() == year && todoLimit.getMonth() < month)
                    || (todoLimit.getYear() == year && todoLimit.getMonth() == month && todoLimit.getDay() < day)) {
                Log.i(TAG, "年：" + todoLimit.getYear() + "現在日時（年）" + year);
                Log.i(TAG, "月：" + todoLimit.getMonth() + "現在日時（月）" + month);
                Log.i(TAG, "日：" + todoLimit.getDay() + "現在日時（日）" + day);
                textViewListTodoTime.setTextColor(Color.RED);
                TextView limitLabel = (TextView)rowView.findViewById(R.id.textView_listTodoLimitLabel);
                limitLabel.setTextColor(Color.RED);

                // 上記以外は期限のテキストの色を青色にする
            } else {
                textViewListTodoTime.setTextColor(Color.BLUE);
                TextView limitLabel = (TextView)rowView.findViewById(R.id.textView_listTodoLimitLabel);
                limitLabel.setTextColor(Color.BLUE);
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
                        TodoModel todoModel = new TodoModel(getActivity());
                        // 完了を設定
                        long rtn = todoModel.complete(todoInfo.getId());
                        if (rtn == -1) {
                            Log.d(TAG, "todoModel update結果：TODOの完了に失敗しました");
                        } else {
                            Log.d(TAG, "todoModel update結果：TODOを完了しました");
                        }

//                        ConfigModel configModel = new ConfigModel();
//                        // TODO完了イメージ表示設定の場合は表示する。表示しない場合はメッセージのみ表示
//                        if (configModel.isShowLadyImage(getActivity())) {
                            // 完了時の画像ダイアログを表示する
                            fragmentManager = getActivity().getSupportFragmentManager();
                            dialogFragment = new CompleteImgDialogFragment();
                            dialogFragment.show(fragmentManager, "complete");
//                        } else {
//                            new AlertDialog.Builder(getActivity())
//                                    .setTitle("TODO完了")
//                                    .setMessage("TODOを完了しました")
//                                    .setPositiveButton("OK", null)
//                                    .show();
//                        }

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