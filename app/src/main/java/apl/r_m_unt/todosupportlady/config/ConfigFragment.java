package apl.r_m_unt.todosupportlady.config;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryota on 2017/04/09.
 */
public class ConfigFragment extends ListFragment {

    private static final String TAG = "ConfigFragment";

    private List<String> mList = new ArrayList<>();

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        // アダプターの設定
        setListAdapter(adapter);


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
