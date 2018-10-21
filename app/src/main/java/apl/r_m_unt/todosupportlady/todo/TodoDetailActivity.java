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

            int todoId = getIntent().getIntExtra(TodoDetailFragment.SELECT_TODO_ID, -1);

            // 明細画面に渡すリスト選択情報を設定
            Bundle arguments = new Bundle();
            arguments.putInt(TodoDetailFragment.SELECT_TODO_ID, todoId);

            // 新規登録以外の場合はTODO情報をセットする
            if (todoId != -1) {

                arguments.putString(TodoDetailFragment.SELECT_TODO_TITLE, getIntent().getStringExtra(TodoDetailFragment.SELECT_TODO_TITLE));
                arguments.putString(TodoDetailFragment.SELECT_TODO_DETAIL, getIntent().getStringExtra(TodoDetailFragment.SELECT_TODO_DETAIL));
                arguments.putInt(TodoDetailFragment.SELECT_TODO_LIMIT_YEAR, getIntent().getIntExtra(TodoDetailFragment.SELECT_TODO_LIMIT_YEAR, 9999));
                arguments.putInt(TodoDetailFragment.SELECT_TODO_LIMIT_MONTH, getIntent().getIntExtra(TodoDetailFragment.SELECT_TODO_LIMIT_MONTH, 99));
                arguments.putInt(TodoDetailFragment.SELECT_TODO_LIMIT_DAY, getIntent().getIntExtra(TodoDetailFragment.SELECT_TODO_LIMIT_DAY, 99));
            }


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
