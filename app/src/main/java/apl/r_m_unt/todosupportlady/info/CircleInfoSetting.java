package apl.r_m_unt.todosupportlady.info;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

//import com.google.gson.Gson;

/**
 * Created by ryota on 2017/05/17.
 */

public class CircleInfoSetting {
    private static final String CIRCLE_INFO_DATA = "CIRCLE_INFO_DATA";
    private static CircleInfoSetting instance;
    private List<CircleInfo> circleInfoList = new ArrayList<>();
    private boolean isExistNews = false;

    private CircleInfoSetting() {
        // singleton
    }

    // KEY
    private static final String CIRCLE_INFO_KEY = "CIRCLE_INFO_KEY";

    // 保存情報取得
    public static CircleInfoSetting getInstance(Context context) {

        // 初回の場合
        if (instance == null) {
//            // 保存情報を取得
//            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences prefs = context.getSharedPreferences(CIRCLE_INFO_DATA, Context.MODE_PRIVATE);
//            Gson gson = new Gson();
//            String circleInfoString = prefs.getString(CIRCLE_INFO_KEY, "");
//
//            // 保存したオブジェクトを取得
//            if ( !TextUtils.isEmpty(circleInfoString)) {
//                instance = gson.fromJson(circleInfoString, CircleInfoSetting.class);
//            } else {
//                // 何も保存されていない　初期時点は空のリストを入れる
//                instance = getDefaultInstance();
//            }
        }

        return instance;
    }

    // デフォルト値の入ったオブジェクトを返す
    private static CircleInfoSetting getDefaultInstance() {
        CircleInfoSetting instance = new CircleInfoSetting();

        return instance;
    }


    public int getCircleInfoSize() {
        return circleInfoList.size();
    }

    public List<CircleInfo> getCircleInfoList() {
        return circleInfoList;
    }

    public void setCircleInfo(int index, CircleInfo circleInfo) {
        circleInfoList.set(index, circleInfo);
    }

    public void removedCircleInfo(int index) {
        circleInfoList.remove(index);
    }


    public boolean isExistNews() {
        return isExistNews;
    }

    public void setExistNews(boolean existNews) {
        isExistNews = existNews;
    }

    // サークル情報の追加
    public void addCircleInfo(CircleInfo circleInfo) {
        circleInfoList.add(circleInfo);
    }

//    // 状態保存
//    public void saveInstance(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(CIRCLE_INFO_DATA, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        // 現在のインスタンスの状態を保存
//        prefs.edit().putString(CIRCLE_INFO_KEY, gson.toJson(this)).apply();
//    }
}
