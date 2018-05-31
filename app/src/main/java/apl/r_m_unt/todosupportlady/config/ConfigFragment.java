package apl.r_m_unt.todosupportlady.config;

//import android.app.ListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import apl.r_m_unt.todosupportlady.R;

/**
 * Created by ryota on 2017/04/09.
 */
//public class ConfigFragment extends ListFragment {
public class ConfigFragment extends Fragment {

    private static final String TAG = "ConfigFragment";
    private Switch switchIsShowLadyImage;

//    private List<String> mList = new ArrayList<>();

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

        // サポート画像表示有無の保存値を画面に設定
        ConfigModel configModel = new ConfigModel();
        switchIsShowLadyImage = (Switch)getView().findViewById(R.id.switch_show_lady_image);
        switchIsShowLadyImage.setChecked(configModel.isShowLadyImage(getActivity()));

        // サポート画像表示有無の画面設定値を保存
        switchIsShowLadyImage.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    ConfigModel configModel = new ConfigModel();
                    configModel.setShowLadyImage(getActivity(), isChecked);
                }
            }
        );


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
    }



}
