package apl.r_m_unt.todosupportlady.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ryota on 2018/05/30.
 */

public class SharedPreferenceDataHelper {

    // KEY
    private static final String CONFIG_DATA = "CONFIG_DATA";
    private static final String TODO_LIST_DATA = "TODO_LIST_DATA";
    private static final String CONFIG_IS_SHOW_LADY_IMAGE = "CONFIG_IS_SHOW_LADY_IMAGE";
    private static final String CONFIG_IS_AUTO_SAVE = "CONFIG_IS_AUTO_SAVE";
    private static final String CONFIG_NAME = "CONFIG_NAME";
    private static final String TODO_LIST_IS_SHOW_COMPLETED = "TODO_LIST_IS_SHOW_COMPLETED";

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
     * 自動保存するかどうかを設定
     * @param context
     * @param isAutoSave 自動保存するかどうか
     */
    public void setAutoSave(Context context, boolean isAutoSave) {

        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CONFIG_IS_AUTO_SAVE, isAutoSave);
        editor.apply();
    }

    /**
     * メイドからの呼ばれ方を設定
     * @param context
     * @param name メイドからの呼ばれ方
     */
    public void setName(Context context, String name) {

        // メイドからの呼ばれ方を設定
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CONFIG_NAME, name);
        editor.apply();
    }

    /**
     * TODO一覧の完了済表示を設定
     * @param context
     * @param isShowCompleted 表示有無
     */
    public void setShowCompleted(Context context, boolean isShowCompleted) {

        // TODO一覧の完了済表示を設定
        SharedPreferences prefs = context.getSharedPreferences(TODO_LIST_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(TODO_LIST_IS_SHOW_COMPLETED, isShowCompleted);
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
     * 自動保存設定を取得
     * @param context
     * @return 自動保存
     */
    public boolean isAutoSave(Context context) {

        // 自動保存設定を取得（初期値は未設定）
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        boolean isAutoSave = prefs.getBoolean(CONFIG_IS_AUTO_SAVE, true);
        return isAutoSave;
    }

    /**
     * メイドからの呼ばれ方を取得
     * @param context
     * @return 呼ばれ方
     */
    public String getName(Context context) {

        // メイドからの呼ばれ方を取得
        SharedPreferences prefs = context.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        String name = prefs.getString(CONFIG_NAME, "ご主人");
        return name;
    }

    /**
     * TODO一覧の完了済表示設定を取得
     * @param context
     * @return 表示有無
     */
    public boolean isShowCompleted(Context context) {

        // TODO一覧の完了済表示を取得（初期値は非表示）
        SharedPreferences prefs = context.getSharedPreferences(TODO_LIST_DATA, Context.MODE_PRIVATE);
        boolean isShowCompleted = prefs.getBoolean(TODO_LIST_IS_SHOW_COMPLETED, false);
        return isShowCompleted;
    }
}
