package apl.r_m_unt.todosupportlady.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.common.TodoCommonFunction;
import apl.r_m_unt.todosupportlady.model.SharedPreferenceDataHelper;

/**
 * Created by ryota on 2017/04/09.
 */
public class ConfigFragment extends Fragment {

    private static final String TAG = "ConfigFragment";
    private Switch switchIsShowLadyImage;
    private EditText editTextName;

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
        final SharedPreferenceDataHelper sharedPreferenceData = new SharedPreferenceDataHelper();
        switchIsShowLadyImage = (Switch)getView().findViewById(R.id.switch_show_lady_image);
        switchIsShowLadyImage.setChecked(sharedPreferenceData.isShowLadyImage(getActivity()));

        // メイドからの呼ばれ方の保存値を画面に設定
        editTextName = (EditText) getView().findViewById(R.id.editText_name);
        editTextName.setText(sharedPreferenceData.getName(getActivity()));

        // サポート画像表示有無の画面設定値を変更したときにローカルファイルに保存
        switchIsShowLadyImage.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    sharedPreferenceData.setShowLadyImage(getActivity(), isChecked);
                }
            }
        );


        // 戻るボタン押下時の処理
        buttonBack = (Button)getView().findViewById(R.id.button_config_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextName.getText().toString();
                if (!TodoCommonFunction.isValidValue(name)) {
                    Toast.makeText(getActivity(), "呼び方を入力してください", Toast.LENGTH_SHORT).show();
                    return;
                }
                // メイドからの呼ばれ方を保存
                sharedPreferenceData.setName(getActivity(), editTextName.getText().toString());

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
