package apl.r_m_unt.todosupportlady.config;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/12.
 */
public class ConfigDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            // 明細画面に渡すリスト選択情報を設定
            Bundle arguments = new Bundle();
            arguments.putInt(ConfigDetailFragment.CONFIG_SELECT_NO,
                    getIntent().getIntExtra(ConfigDetailFragment.CONFIG_SELECT_NO, 0));

            // コンフィグ明細画面フラグメントを生成
            ConfigDetailFragment configDetailFragment = new ConfigDetailFragment();
            // リスト選択情報を設定
            configDetailFragment.setArguments(arguments);
            // コンフィグ明細画面フラグメントを追加
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.config_detail_container, configDetailFragment)
                    .commit();
        }
    }

}
