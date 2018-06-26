package apl.r_m_unt.todosupportlady.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ryota on 2018/05/30.
 */

public class ConfigModel {

    // KEY
    private static final String CONFIG_DATA = "CONFIG_DATA";
    private static final String CONFIG_IS_SHOW_LADY_IMAGE = "CONFIG_IS_SHOW_LADY_IMAGE";
    private static final String CONFIG_IS_SHOW_COMPLETED = "CONFIG_IS_SHOW_LADY_IMAGE";

    /**
     * サポート画像表示有無を設定
     * @param context
     * @param isShowLadyImage 表示有無
     */
    public void setShowLadyImage(Context context, boolean isShowLadyImage) {

        // サポート画像表示有無を設定
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CONFIG_IS_SHOW_LADY_IMAGE, isShowLadyImage);
        editor.apply();
    }

    /**
     * TODO一覧の完了済表示を設定
     * @param context
     * @param isShowCompleted 表示有無
     */
    public void setShowCompleted(Context context, boolean isShowCompleted) {

        // TODO一覧の完了済表示を設定
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CONFIG_IS_SHOW_COMPLETED, isShowCompleted);
        editor.apply();
    }

    /**
     * サポート画像表示有無を取得
     * @param context
     * @return 表示有無
     */
    public boolean isShowLadyImage(Context context) {

        // サポート画像表示有無を取得（初期値は表示）
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        boolean isShowLadyImage = prefs.getBoolean(CONFIG_IS_SHOW_LADY_IMAGE, true);
        return isShowLadyImage;
    }

    /**
     * TODO一覧の完了済表示設定を取得
     * @param context
     * @return 表示有無
     */
    public boolean isShowCompleted(Context context) {

        // TODO一覧の完了済表示を取得（初期値は非表示）
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        boolean isShowCompleted = prefs.getBoolean(CONFIG_IS_SHOW_COMPLETED, false);
        return isShowCompleted;
    }
}
