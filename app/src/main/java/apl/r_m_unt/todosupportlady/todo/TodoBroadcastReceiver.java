package apl.r_m_unt.todosupportlady.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.preferences.TodoController;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by ryota on 2017/05/08.
 */
public class TodoBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "TodoBroadcastReceiver";

    private TodoController todoController;
    CompleteController soundController;

    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "TodoBroadcastReceiver onReceive");

        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();

        // 予備TODOの場合はTODO起動をキャンセルする
        if (0 == intent.getIntExtra("before", 0)) {
            Log.d(TAG, "予備TODO動作起動");
            return;
        }

        Intent soundPlayIntent = new Intent(context, CompleteActivity.class);
        soundPlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //soundPlayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(soundPlayIntent);

        /*
        // TODO音を鳴らす
//        Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_todo));
//        ringtone.play();
//        SoundActivity.setMediaPlayer(context, R.raw.effect);
//        SoundActivity.loopPlay();
        soundController = SoundController.getInstance();
        soundController.setMediaPlayer(context, R.raw.effect);
        // ループ再生
        soundController.play(true);

        // intentIDを取得
        int notificationId = intent.getIntExtra("notification", 0);
        // 通知をタップしたときに起動するActivityの指定
        Intent nextIntent = new Intent(context, SoundActivity.class);
        //Intent nextIntent = new Intent(context, MainActivity.class);

        // TODO設定情報を取得
        PendingIntent nextPendingIntent = PendingIntent.getActivity(context, notificationId, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int todoIndex = intent.getIntExtra("todoIndex", 0);
        todoSetting = todoSetting.getInstance(context);
        // 表示するTODO1件分の情報を取得
        todoSettingInfo todoSettingInfo = todoSetting.gettodoSettingInfoList().get(todoIndex);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Toast.makeText(context, "todoBroadcastReceiver onReceive", Toast.LENGTH_SHORT);

        // TODO　TODO設定情報をセットする
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.laala)
                // ステータスバーに表示されるテキスト
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                // 開いたときに表示されるタイトル
                .setContentTitle("Testtodo")
                // 開いたときに表示されるサブタイトル
                .setContentText("時間になりました：" + todoSettingInfo.gettodoTime())
                //.setPriority(Notification.PRIORITY_DEFAULT)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .addAction(R.drawable.falulu, "止める", nextPendingIntent)
                // 通知方法
                //.setDefaults(Notification.DEFAULT_SOUND)
                // 通知をタップしたときに立ち上げるActivity
                //.setContentIntent(pushedIntent)
                // TODO音
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_todo))
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_todo))
                .build();

        // 古い通知を削除
//        notificationManager.cancelAll();

        // 通知
        notificationManager.notify(0, notification);


//        mediaPlayer = MediaPlayer.create(context, R.raw.effect);
//        mediaPlayer.start();
*/

        Intent nextIntent = new Intent(context, CompleteActivity.class);
        nextIntent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent nextPendingIntent = PendingIntent.getActivity(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 表示するTODO1件分の情報を取得
        int todoIndex = intent.getIntExtra("todoIndex", 0);
//        todoController = TodoController.getInstance(context);
//        TodoSettingInfo todoSettingInfo = todoController.getTodoSettingInfoList().get(todoIndex);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.laala)
                // ステータスバーに表示されるテキスト
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                // 開いたときに表示されるタイトル
                .setContentTitle("TestTodo")
//                // 開いたときに表示されるサブタイトル
//                .setContentText("時間になりました：" + todoSettingInfo.getTodoTime())
                //.setPriority(Notification.PRIORITY_DEFAULT)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                // 通知方法
                .setDefaults(Notification.DEFAULT_ALL)
                // 通知をタップしたときに立ち上げるActivity
                .setContentIntent(nextPendingIntent)
                .build();

        // 通知
        notificationManager.notify(0, notification);
    }
}
