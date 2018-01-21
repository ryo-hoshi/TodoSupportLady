package apl.r_m_unt.todosupportlady.todo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/12.
 */
public class TodoDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            // 明細画面に渡すリスト選択情報を設定
            Bundle arguments = new Bundle();
            arguments.putInt(TodoDetailFragment.TODO_SELECT_NO,
                    getIntent().getIntExtra(TodoDetailFragment.TODO_SELECT_NO, 0));

            // TODO明細画面フラグメントを生成
            TodoDetailFragment todoDetailFragment = new TodoDetailFragment();
            // リスト選択情報を設定
            todoDetailFragment.setArguments(arguments);
            // TODO明細画面フラグメントを追加
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.todo_detail_container, todoDetailFragment)
                    .commit();
        }
    }
}
