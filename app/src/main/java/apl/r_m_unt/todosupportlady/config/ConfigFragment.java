package apl.r_m_unt.todosupportlady.config;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/09.
 */
public class ConfigFragment extends ListFragment {

    private static final String TAG = "ConfigFragment";

    private List<String> mList = new ArrayList<>();
    private ListView configListView;
    private Button buttonBack;

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_config, container, false);

    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configListView = getListView();

        // 戻るボタン押下時の処理
        buttonBack = (Button)getView().findViewById(R.id.button_config_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当画面のActivityを終了する
                getActivity().finish();
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mList.add("このアプリについて");
        mList.add("使い方");
        mList.add("設定");

        //ListView listViewInfoConfig = (ListView) getView().findViewById(R.id.listView_infoConfig);

        // アダプターの生成
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, mList);

//        configListView = (ListView)getView().findViewById(R.id.listView_config);
        configListView.setAdapter(adapter);

//        // アダプターの設定
//        setListAdapter(adapter);
    }

// 必要なら使う
//    /*
//     * アイテムの追加
//     */
//    public void add(String message) {
//
//        mList.add(message);
//
//    }

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
        Intent configDetailIntent = new Intent(getActivity(), ConfigDetailActivity.class);
        configDetailIntent.putExtra(ConfigDetailFragment.CONFIG_SELECT_NO, position);
        startActivity(configDetailIntent);
    }


}
