package apl.r_m_unt.todosupportlady.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import apl.r_m_unt.todosupportlady.main.MainActivity;

public class PushReceiveActivity extends AppCompatActivity {

    public static final String ARG_ID = "id";
    public static final String ARG_LABEL = "label";
    public static final String ARG_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_push_receive);

//        // お知らせ情報を保存しておく
//        CircleInfoSetting circleInfoSetting = CircleInfoSetting.getInstance(getApplicationContext());
//        //List<CircleInfo> circleInfoList = circleInfoSetting.getCircleInfoList();
//        circleInfoSetting.addCircleInfo(new CircleInfo(id, label, text));
//        //circleInfoList.add(new CircleInfo(id, label, text));
//        circleInfoSetting.saveInstance(getApplicationContext());

//        SharedPreferences prefs = getSharedPreferences(CIRCLE_INFO_DATA, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String circleInfoString = prefs.getString(CIRCLE_INFO_KEY, "");
//        // 保存したオブジェクトを取得
//        List<CircleInfo> circleInfoList;
//        if ( !TextUtils.isEmpty(circleInfoString)) {
//            circleInfo = gson.fromJson(circleInfoString, CircleInfo.class);
//        } else {
//            // 何も保存されていない　初期時点はデフォルト値を入れる
//            circleInfo =
//        }
//
//        prefs.edit()
//        String todoSettingString = prefs.getString(KEY_ID, "");

        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(ARG_ID, "dummy");
//        mainIntent.putExtra(ARG_LABEL, label);
//        mainIntent.putExtra(ARG_TEXT, text);
        //soundPlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
    }

}
