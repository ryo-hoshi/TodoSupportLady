package apl.r_m_unt.todosupportlady.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO設定クラス
 * TODO情報の管理を行う
 *
 */
public class TodoSetting {

    private static final String TAG = "TodoSetting";
    private static final String todo_setTING_DATA = "todo_setTING_DATA";
    private static TodoSetting instance;
    private List<TodoSettingInfo> todoSettingInfoList = new ArrayList<>();

    private TodoSetting() {
        // singleton
    }

    // KEY
    private static final String todo_setTING_KEY = "todo_setTING";

    // 保存情報取得
    public static TodoSetting getInstance(Context context) {

        // 初回の場合
        if (instance == null) {
            // 保存情報を取得
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences prefs = context.getSharedPreferences(todo_setTING_DATA, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String todoSettingString = prefs.getString(todo_setTING_KEY, "");

            // 保存したオブジェクトを取得
            if ( !TextUtils.isEmpty(todoSettingString)) {
                instance = gson.fromJson(todoSettingString, TodoSetting.class);
            } else {
                // 何も保存されていない　初期時点はデフォルト値を入れる
                instance = getDefaultInstance();
            }
        }

        return instance;
    }

    // デフォルト値の入ったオブジェクトを返す
    private static TodoSetting getDefaultInstance() {
        TodoSetting instance = new TodoSetting();
        //instance.todoSettingInfoList = new ArrayList<>();
        // TODO情報のインデックスに0を指定
        instance.todoSettingInfoList.add(new TodoSettingInfo(0, 0));

        return instance;
    }

    public int getTodoSetSize() {
        return todoSettingInfoList.size();
    }

    public List<TodoSettingInfo> getTodoSettingInfoList() {
        return todoSettingInfoList;
    }

    public void setTodoSettingInfo(int index, TodoSettingInfo todoSettingInfo) {
        todoSettingInfoList.set(index, todoSettingInfo);
    }

    public int addTodoSettingInfo() {
        int todoNo = this.getTodoNo();
        int todoIndex = todoSettingInfoList.size();
        Log.d(TAG, "生成したTODONo：" + todoNo);
        todoSettingInfoList.add(new TodoSettingInfo(todoNo, todoIndex));
        return todoNo;
    }

    public void removedTodoSettingInfo(int index) {
        todoSettingInfoList.remove(index);
    }

    // 状態保存
    public void saveInstance(Context context) {
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences prefs = context.getSharedPreferences(todo_setTING_DATA, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        // 現在のインスタンスの状態を保存
        prefs.edit().putString(todo_setTING_KEY, gson.toJson(this)).apply();
    }

    /**
     * TODO情報インデックスを取得
     * @return
     */
    private int getTodoNo() {

        // TODO情報が存在しない場合は無条件で0
        if (todoSettingInfoList.isEmpty()) {
            return 0;
        }

        // 既存のTODONoとかぶらない値を取得
        // 候補のTODONoを取得
        int todoNo = todoSettingInfoList.size();
        int maxIndex = 0;
        boolean isIndexExist = false;

        for (TodoSettingInfo todoSettingInfo : todoSettingInfoList) {
            if (todoNo == todoSettingInfo.getTodoNo()) {
                isIndexExist = true;
            }
            if (maxIndex < todoNo) {
                maxIndex = todoNo;
            }
        }

        // 候補のTODONoが使われていたら最大値＋１を取得
        // intの上限まではいかない想定
        if (isIndexExist) {
            return maxIndex + 1;

            // 使われていなければ候補をそのまま取得
        } else {
            return todoNo;
        }
    }
}
