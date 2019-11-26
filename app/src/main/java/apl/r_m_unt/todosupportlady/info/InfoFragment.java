package apl.r_m_unt.todosupportlady.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/09.
 */
public class InfoFragment extends Fragment {

    private TextView textViewCircleInfoRead;
    private Button buttonBack;
    private TextView textViewPlayStore;

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

        textViewCircleInfoRead = (TextView)getView().findViewById(R.id.textView_circleInfoRead);
        textViewPlayStore = (TextView)getView().findViewById(R.id.textView_play_store);

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

        textViewCircleInfoRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(getString(R.string.app_info_read_uri));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        textViewPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = getString(R.string.app_info_store_id);

                try {
                    Uri storeUri = Uri.parse("market://details?id=" + id);
                    Intent intent = new Intent(Intent.ACTION_VIEW, storeUri);
                    startActivity(intent);
                } catch (Exception ex) {
                    Uri storeUri = Uri.parse("https://play.google.com/store/apps/details?id=" + id);
                    Intent intent = new Intent(Intent.ACTION_VIEW, storeUri);
                    startActivity(intent);
                }
            }
        });
    }
}
