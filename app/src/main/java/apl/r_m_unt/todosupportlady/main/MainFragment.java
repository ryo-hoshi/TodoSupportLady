package apl.r_m_unt.todosupportlady.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.TodoCommonFunction;
import apl.r_m_unt.todosupportlady.common.TodoConstant;
import apl.r_m_unt.todosupportlady.config.ConfigActivity;
import apl.r_m_unt.todosupportlady.model.SharedPreferenceDataHelper;
import apl.r_m_unt.todosupportlady.info.InfoActivity;
import apl.r_m_unt.todosupportlady.todo.TodoDetailActivity;
import apl.r_m_unt.todosupportlady.todo.TodoListActivity;

import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.INTENT_KEY_REGISTER;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.INTENT_KEY_REGISTER_NEW;
import static apl.r_m_unt.todosupportlady.todo.TodoDetailFragment.SELECT_TODO_ID;

/**
 * Created by ryota on 2017/04/09.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO登録のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_todo_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO詳細画面へ遷移
                Intent todoDetailIntent = new Intent(getActivity(), TodoDetailActivity.class);
                todoDetailIntent.putExtra(SELECT_TODO_ID, -1);
                //startActivity(todoDetailIntent);
                // 返却値を受け取るモードでActivityを起動
                startActivityForResult(todoDetailIntent, TodoConstant.RequestCode.Main.getInt());
            }
        });

        // TODO一覧のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_todo_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoListIntent = new Intent(getActivity(), TodoListActivity.class);
                startActivity(todoListIntent);
            }
        });

        // アプリ情報のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getActivity(), InfoActivity.class);
                startActivity(infoIntent);
            }
        });

        // 設定のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(getActivity(), ConfigActivity.class);
                startActivity(configIntent);
            }
        });
    }


    /**
     * 呼び出し先からのコールバックを受け取る
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TodoConstant.RequestCode.Main.getInt()) {

            // TODO登録画面からの戻り時に新規登録の設定がされていた場合はTODO一覧を表示する
            if (resultCode == Activity.RESULT_OK && INTENT_KEY_REGISTER_NEW.equals(data.getStringExtra(INTENT_KEY_REGISTER))) {
                Intent todoListIntent = new Intent(getActivity(), TodoListActivity.class);
                startActivity(todoListIntent);
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // タイトルに呼ばれ方を設定
        TextView mainToolbar = (TextView)getActivity().findViewById(R.id.textView_main_toolbar);
        SharedPreferenceDataHelper sharedPreferenceData = new SharedPreferenceDataHelper();
        String name = sharedPreferenceData.getName(getActivity());
        if (!TodoCommonFunction.isValidValue(name)) {
            name = getResources().getString(R.string.default_name);
        }
        mainToolbar.setText(name + getResources().getString(R.string.default_honorific) + " " + getResources().getString(R.string.main_toolbar));

        String id = getActivity().getIntent().getStringExtra("id");
        String label =  getActivity().getIntent().getStringExtra("label");
        String text =  getActivity().getIntent().getStringExtra("text");
        if (id != null && label != null && text != null) {

            Toast.makeText(getActivity(), "バックグラウンドで通知を受信　id:" + id + ", label:" + label, Toast.LENGTH_LONG).show();
            Log.d(TAG, "id:" + id + ", label:" + label);

        }

    }
}
