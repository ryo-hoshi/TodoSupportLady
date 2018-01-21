package apl.r_m_unt.todosupportlady.todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/12.
 */
public class TodoListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // 追加ボタンをクリックした時の処理
        // ListFragmentの要素ではないのでここで設定
        findViewById(R.id.button_todo_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フラグメントの処理を呼び出す
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_todo_list);
                if (fragment != null && fragment instanceof TodoListFragment) {
                    ((TodoListFragment) fragment).addTodoInfo();
                }
            }
        });

        // 完了ボタンをクリックした時の処理
        findViewById(R.id.button_todo_comp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当アクティビティを終了する
                finish();
            }
        });

//        // TODOON／OFFを切り替えた時の処理
//        findViewById(R.id.switch_listtodoTime).setOnClickListener(new View.OnClickListener() {
//        //switchtodo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // フラグメントの処理を呼び出す
//                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_todo_list);
//                if (fragment != null && fragment instanceof todoListFragment) {
//                    ((todoListFragment) fragment).settodoSetting();
//                }
//            }
//        });
    }



//        public class todoInfoAdapter extends ArrayAdapter<todoSettingInfo> {
//
//            // レイアウトxmlファイルからIDを指定してViewが使用可能
//            private LayoutInflater mLayoutInflater;
//
//            //public todoInfoAdapter(Context context, int resourceId, List<todoInfo> objects) {
//            public todoInfoAdapter(Context context, int resourceId, List<todoSettingInfo> objects) {
//                super(context, resourceId, objects);
//                // getLayoutInflater()メソッドはActivityじゃないと使えない
//                mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            }
//
//            // getView()メソッドは各行を表示しようとした時に呼ばれる
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                // 特定行(position)のデータを得る
//                //todoInfo item = (todoInfo)getItem(position);
//                todoSettingInfo item = (todoSettingInfo)getItem(position);
//                // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
//                if (null == convertView) convertView = mLayoutInflater.inflate(R.layout.list_item, null);
//
//                // todoInfoのデータをViewの各Widgetにセットする
//                TextView textViewListtodoTime = (TextView)convertView.findViewById(R.id.textView_listtodoTime);
//                textViewListtodoTime.setText(item.gettodoTime());
//
//                TextView textViewListMemo = (TextView)convertView.findViewById(R.id.textView_listMemo);
//                textViewListMemo.setText(item.getMemo());
//
//                Switch switchListtodoTime = (Switch)convertView.findViewById(R.id.switch_listtodoTime);
//                switchListtodoTime.setChecked(item.gettodoSwitch());
//
//                switchListtodoTime.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // フラグメントの処理を呼び出す
//                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_todo_list);
//                    if (fragment != null && fragment instanceof todoListFragment) {
//                        ((todoListFragment) fragment).settodoSetting();
//                    }
//                }
//            });
//
//            return convertView;
//        }
//    }
}
