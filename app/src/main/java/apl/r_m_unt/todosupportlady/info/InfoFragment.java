package apl.r_m_unt.todosupportlady.info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/09.
 */
public class InfoFragment extends Fragment {

    private TextView textView_circleInfoRead;
    private Button buttonBack;
    CircleInfoAdapter circleInfoAdapter = null;
    CircleInfoSetting circleInfoSetting;
    List<CircleInfo> circleInfoList;

    private ListView circleInfoListView;

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_info, container, false);

    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_circleInfoRead = (TextView)getView().findViewById(R.id.textView_circleInfoRead);

        circleInfoSetting = CircleInfoSetting.getInstance(this.getActivity().getApplicationContext());
        circleInfoListView = (ListView)getView().findViewById(R.id.listView_circleInfo);
        // TODO ローカルファイルの読み込みは後で削除予定
        // 新着ニュース存在フラグをfalseに変更
        if (circleInfoSetting != null && circleInfoSetting.isExistNews()) {
            circleInfoSetting.setExistNews(false);
//            circleInfoSetting.saveInstance(getActivity());
        }

        // 戻るボタン押下時の処理
        buttonBack = (Button)getView().findViewById(R.id.button_info_back);
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

        textView_circleInfoRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(getString(R.string.circle_info_read_uri));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // サークル情報リストを取得
        circleInfoList = circleInfoSetting.getCircleInfoList();

        if (circleInfoList.isEmpty()) {
            circleInfoList.add(new CircleInfo("0","これはテストのラベルです","これはテストのテキストです"));
        }

        if (circleInfoList.size() > 0) {
            // サークル情報一覧に設定
            circleInfoAdapter = new CircleInfoAdapter(getActivity(), 0, circleInfoList);
            circleInfoListView.setAdapter(circleInfoAdapter);
        }
    }

    /**
     *
     */
    public class CircleInfoAdapter extends ArrayAdapter<CircleInfo> {

        // レイアウトxmlファイルからIDを指定してViewが使用可能
        private LayoutInflater mLayoutInflater;

        public CircleInfoAdapter(Context context, int resourceId, List<CircleInfo> objects) {
            super(context, resourceId, objects);
            // getLayoutInflater()メソッドはActivityじゃないと使えない
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // getView()メソッドは各行を表示しようとした時に呼ばれる
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            // 特定行(position)のデータを得る
            CircleInfo item = (CircleInfo)getItem(position);
            // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
            if (null == rowView) rowView = mLayoutInflater.inflate(R.layout.circle_info_item, null);

            // CircleInfoのデータをViewの各Widgetにセットする
            // サークル情報ラベル
            TextView textViewLabel = (TextView)rowView.findViewById(R.id.textView_circleInfoLabel);
            textViewLabel.setText(item.getLabel());
            // サークル情報本文
            TextView textViewMemo = (TextView)rowView.findViewById(R.id.textView_circleInfoText);
            textViewMemo.setText(item.getText());

            return rowView;
        }
    }
}
