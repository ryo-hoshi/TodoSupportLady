package apl.r_m_unt.todosupportlady.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.TodoConstant;
import apl.r_m_unt.todosupportlady.preferences.TodoSetting;
import apl.r_m_unt.todosupportlady.preferences.TodoSettingInfo;

import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.TODO_SELECT_NO;

/**
 * TODOリスト画面のフラグメント
 */
public class TodoListFragment extends ListFragment {

    private static final String TAG = "TodoListFragment";
    TodoInfoAdapter todoInfoAdapter = null;

    private TodoSetting todoSetting;

    private List<TodoSettingInfo> todoSettingInfoList;

    private int todoSelectIndex = 0;

    private ListView todoListView;

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

        todoSetting = TodoSetting.getInstance(this.getActivity().getApplicationContext());
        todoListView = getListView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "TodoListFragment onResume起動");

        // TODO設定情報リストを取得
        todoSettingInfoList = todoSetting.getTodoSettingInfoList();
        // TODO設定一覧に設定
        todoInfoAdapter = new TodoInfoAdapter(getActivity(), 0, todoSettingInfoList);
        setListAdapter(todoInfoAdapter);

    }

    /**
     * TODO情報リストの更新
     */
    public void updateTodoInfoList() {
        todoSettingInfoList = todoSetting.getTodoSettingInfoList();
        todoInfoAdapter = new TodoInfoAdapter(getActivity(), 0, todoSettingInfoList);
        setListAdapter(todoInfoAdapter);

        // TODO情報の保存
        todoSetting.saveInstance(getActivity().getApplicationContext());
    }

    /*
     * TODOの追加
     */
    public void addTodoInfo() {

        if (TodoConstant.todo_set_MAX_SIZE <= todoSetting.getTodoSetSize()) {
            // 10件すでに登録済みの場合はメッセージを出す
            new AlertDialog.Builder(getActivity())
                    .setTitle(String.format(getActivity().getString(R.string.over_todo_add_title), "10"))
                    .setMessage(R.string.over_todo_add_msg)
                    .setPositiveButton("OK", null)
                    .show();

            return;
        }

        // TODO情報の追加
//        todoSettingInfoList = todoSetting.getAddedTodoSettingInfoList();
        int todoIndex = todoSetting.addTodoSettingInfo();
        Toast.makeText(this.getActivity().getApplicationContext(),"生成したインデックス：" + String.valueOf(todoIndex), Toast.LENGTH_SHORT).show();
        //updateTodoInfoList();
        // 追加したTODOの明細画面に遷移
        int position = todoSetting.getTodoSetSize() - 1;
        Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
        todoDetailIntent.putExtra(TODO_SELECT_NO, position);
        startActivity(todoDetailIntent);
    }

    /*
     * TODOの更新
     */
    public void updateTodoInfo(int listIndex, TodoSettingInfo todoSettingInfo) {

        if (todoSelectIndex < 0 || todoSetting.getTodoSetSize() <= todoSelectIndex) {
            return;
        }

        // 画面設定情報をTODO設定情報に反映
        todoSetting.setTodoSettingInfo(listIndex, todoSettingInfo);
        // 変更内容の反映
        updateTodoInfoList();

        // TODO実行の更新
        TodoController todoController = new TodoController(getActivity());
        todoController.setTodo(todoSettingInfo);
    }

    /*
     * TODOの削除
     */
    private void removeTodoInfo(int listIndex) {

        if (listIndex < 0 || todoSetting.getTodoSetSize() <= listIndex) {
            return;
        }
        todoSelectIndex = listIndex;
        // TODO情報の削除
        // 縦固定なので、画面向き変更時のメモリリーク発生は考慮不要
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.confirm_todo_delete))
                .setMessage(todoSettingInfoList.get(todoSelectIndex).getMemo())
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK ボタン押下時
                                Log.d(TAG, "TODO削除ダイアログのOKボタンを押下しました。" + "whichButton:" + whichButton);
                                todoSetting.removedTodoSettingInfo(todoSelectIndex);
                                updateTodoInfoList();
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    /*
     * アイテムのクリック
     *
     * @see
     * android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView
     * , android.view.View, int, long)
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick position => " + position + " : id => " + id);

        Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
        todoDetailIntent.putExtra(TODO_SELECT_NO, position);
        startActivity(todoDetailIntent);
    }

    /**
     *
     */
    public class TodoInfoAdapter extends ArrayAdapter<TodoSettingInfo> {

        // レイアウトxmlファイルからIDを指定してViewが使用可能
        private LayoutInflater mLayoutInflater;

        //public TodoInfoAdapter(Context context, int resourceId, List<TodoInfo> objects) {
        public TodoInfoAdapter(Context context, int resourceId, List<TodoSettingInfo> objects) {
            super(context, resourceId, objects);
            // getLayoutInflater()メソッドはActivityじゃないと使えない
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // getView()メソッドは各行を表示しようとした時に呼ばれる
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            // 特定行(position)のデータを得る
            //TodoInfo item = (TodoInfo)getItem(position);
            TodoSettingInfo item = (TodoSettingInfo)getItem(position);
            // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
            if (null == rowView) rowView = mLayoutInflater.inflate(R.layout.list_item, null);

            // TodoInfoのデータをViewの各Widgetにセットする
            // TODO情報インデックス（非表示）
            TextView textViewListIndex = (TextView)rowView.findViewById(R.id.textView_listIndex);
            Log.i(TAG, "textViewListIndexの値："+textViewListIndex);
            Log.i(TAG, "itemの値："+item);
            textViewListIndex.setText(String.valueOf(item.getTodoNo()));

            // TODO設定時間
            TextView textViewListTodoTime = (TextView)rowView.findViewById(R.id.textView_listTodoTime);
            textViewListTodoTime.setText(item.getTodoTime());
            // TODOメモ
            TextView textViewListMemo = (TextView)rowView.findViewById(R.id.textView_listMemo);
            textViewListMemo.setText(item.getMemo());
            // TODOON/OFF
            Switch switchListTodoTime = (Switch)rowView.findViewById(R.id.switch_listTodoTime);
            switchListTodoTime.setChecked(item.getTodoSwitch());

            // 削除ボタン
            ImageButton imageButtonDelete = (ImageButton)rowView.findViewById(R.id.imageButton_delete);

            // ---------- 初回のみイベント登録 ----------
            // TODOスイッチ
            if (switchListTodoTime.getTag() == null) {
                switchListTodoTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 最新のpositionを取得
                        int switchPosition = (int)v.getTag();
                        // 上記positionからデータを取得
                        TodoSettingInfo todoSettingInfo = todoInfoAdapter.getItem(switchPosition);
                        Log.d(TAG, "switchListTodoTime onClick呼ばれた" + "switch:" + todoSettingInfo.getTodoSwitch() + "switchPosition:" + switchPosition);
                        // TODO設定ON/OFFを反転させる
                        todoSettingInfo.setTodoSwitch(!todoSettingInfo.getTodoSwitch());
                        // TODO設定を更新
                        updateTodoInfo(switchPosition, todoSettingInfo);
                    }
                });
            }
            // 削除ボタン
            if (imageButtonDelete.getTag() == null) {
                imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 最新のpositionを取得
                        int position = (int)v.getTag();
                        // 上記positionからデータを取得
                        TodoSettingInfo todoSettingInfo = todoInfoAdapter.getItem(position);
                        Log.d(TAG, "imageButtonDelete onClick呼ばれた" + "position:" + position);
                        // TODOを削除する
                        removeTodoInfo(position);
                    }
                });
            }

            // 最新のpositionをOnクリックイベントで使用するために保存する
            switchListTodoTime.setTag(position);
            imageButtonDelete.setTag(position);

            return rowView;
        }
    }
}