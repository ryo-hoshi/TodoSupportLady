package apl.r_m_unt.todosupportlady.todo;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import apl.r_m_unt.todosupportlady.preferences.TodoSettingInfo;

public class TodoController {

    private Context context;

    private static final String TAG = "TodoController";

    public TodoController(Context context) {
        this.context = context;

    }

    /**
     * TODO情報の設定
     * @param todoSettingInfo
     */
    public void setTodo(TodoSettingInfo todoSettingInfo) {

        PendingIntent todoNoIntent = getTodoPendingIntent(todoSettingInfo.getTodoNo());

        // TODOをONに設定した場合
        if (todoSettingInfo.getTodoSwitch()) {



            // TODOをOFFに設定した場合
        } else {

        }
    }


    /**
     *
     * @param index
     * @return
     */
    private PendingIntent getTodoPendingIntent(int index) {
        Intent intent = new Intent(context, TodoBroadcastReceiver.class);
        // TODONoを設定して別のTODOであることを識別させる
        intent.setType(String.valueOf(index));
        intent.putExtra("todoIndex", index);

        // 予備TODOの場合
        if(index == 0) {
            intent.putExtra("before", 1);
        } else {
            intent.putExtra("before", 0);
        }

        // 第1引数：コンテキスト
        // 第2引数：リクエストコード
        // 第3引数：Intent
        // 第4引数：同じIntentがBroadCastされた場合更新する
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
