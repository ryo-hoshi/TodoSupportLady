package apl.r_m_unt.todosupportlady.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.todo.TodoListActivity;
import apl.r_m_unt.todosupportlady.config.ConfigActivity;
import apl.r_m_unt.todosupportlady.info.CircleInfo;
import apl.r_m_unt.todosupportlady.info.CircleInfoSetting;
import apl.r_m_unt.todosupportlady.info.InfoActivity;

/**
 * Created by ryota on 2017/04/09.
 */
public class MainFragment extends Fragment {

   // private TextView mTextView;

    private static final String TAG = "MainFragment";
    CircleInfoSetting circleInfoSetting;

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
//        // TextViewをひも付けます
//        mTextView = (TextView) view.findViewById(R.id.textView_main);
        // TODOのイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_todo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent itemListIntent = new Intent(getActivity(), ItemListActivity.class);
//                startActivity(itemListIntent);
                Intent todoListIntent = new Intent(getActivity(), TodoListActivity.class);
                startActivity(todoListIntent);
            }
        });

        // サークル情報のイメージをクリックした時の処理
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

//        // PUSH通知用
//        Button logTokenButton = (Button) getActivity().findViewById(R.id.logTokenButton);
//        logTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//
//                // Log and toast
//                String msg = getString(R.string.circle_msg_token_fmt, token);
//                Log.d(TAG, msg);
//                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button subscribeButton = (Button) getActivity().findViewById(R.id.subscribeButton);
//        subscribeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // [START subscribe_topics]
//                FirebaseMessaging.getInstance().subscribeToTopic("news");
//                // [END subscribe_topics]
//
//
//                // Log and toast
//                String msg = getString(R.string.circle_info_msg_subscribed);
//                Log.d(TAG, msg);
//                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        String id = getActivity().getIntent().getStringExtra("id");
//        String label =  getActivity().getIntent().getStringExtra("label");
//        String text =  getActivity().getIntent().getStringExtra("text");
//        if (id != null && label != null && text != null) {
//
//            Toast.makeText(getActivity(), "バックグラウンドで通知を受信　id:" + id + ", label:" + label, Toast.LENGTH_LONG).show();
//            Log.d(TAG, "id:" + id + ", label:" + label);
//
//            //
//            circleInfoSetting = CircleInfoSetting.getInstance(getActivity());
//            circleInfoSetting.addCircleInfo(new CircleInfo(id, label, text));
//            circleInfoSetting.setExistNews(true);
//            circleInfoSetting.saveInstance(getActivity());
//        }
//
//        // 新着ニュースが存在する場合はインフォメーション画像を差し替える
//        if (circleInfoSetting == null) {circleInfoSetting = CircleInfoSetting.getInstance(getActivity());}
//        if (circleInfoSetting.isExistNews()) {
//            ImageButton imageButtonInfo = (ImageButton)getActivity().findViewById(R.id.imageButton_info);
//            imageButtonInfo.setImageResource(R.drawable.falulu2);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String id = getActivity().getIntent().getStringExtra("id");
        String label =  getActivity().getIntent().getStringExtra("label");
        String text =  getActivity().getIntent().getStringExtra("text");
        if (id != null && label != null && text != null) {

            Toast.makeText(getActivity(), "バックグラウンドで通知を受信　id:" + id + ", label:" + label, Toast.LENGTH_LONG).show();
            Log.d(TAG, "id:" + id + ", label:" + label);

            //
//            circleInfoSetting = CircleInfoSetting.getInstance(getActivity());
//            circleInfoSetting.addCircleInfo(new CircleInfo(id, label, text));
//            circleInfoSetting.setExistNews(true);
//            circleInfoSetting.saveInstance(getActivity());
        }

        // 新着ニュースが存在する場合はインフォメーション画像を差し替える
//        if (circleInfoSetting == null) {circleInfoSetting = CircleInfoSetting.getInstance(getActivity());}
//        if (circleInfoSetting.isExistNews()) {
//            ImageButton imageButtonInfo = (ImageButton)getActivity().findViewById(R.id.imageButton_info);
//            imageButtonInfo.setImageResource(R.drawable.falulu2);
//        }

    }
}
