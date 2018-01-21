package apl.r_m_unt.todosupportlady.push;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import apl.r_m_unt.todosupportlady.info.CircleInfo;
import apl.r_m_unt.todosupportlady.info.CircleInfoSetting;

//import com.firebase.jobdispatcher.Constraint;
//import com.firebase.jobdispatcher.FirebaseJobDispatcher;
//import com.firebase.jobdispatcher.GooglePlayDriver;
//import com.firebase.jobdispatcher.Job;

/**
 * Created by ryota on 2017/05/12.
 */

public class TodoFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "TodoFirebaseMsgingSvc";


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
// [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                //scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        // プッシュメッセージのdataに含めた値を取得
        String id = "";
        String label = "";
        String text = "";
        Map<String, String> data = remoteMessage.getData();
        if (data.containsKey("id")){ id = data.get("id"); }
        if (data.containsKey("label") ){ label = data.get("label");  }
        if (data.containsKey("text") ){ text = data.get("text");  }
        // 受信したお知らせ情報を保存する
        CircleInfoSetting circleInfoSetting = CircleInfoSetting.getInstance(getApplicationContext());
        circleInfoSetting.addCircleInfo(new CircleInfo(id, label, text));
        circleInfoSetting.setExistNews(true);
        circleInfoSetting.saveInstance(getApplicationContext());

        // 別スレッドでToast使うための考慮
        Looper.myLooper().prepare();
        Toast.makeText(this, "最新情報を受信しました。インフォメーションをご確認ください。", Toast.LENGTH_LONG).show();
        Looper.myLooper().loop();
        Looper.myLooper().quit();

//        // Notificationを生成
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentTitle(getString(R.string.app_name));
//        //builder.setContentText(remoteMessage.getNotification().getBody());
//        builder.setContentText(label);
//        builder.setDefaults(Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS);
//        builder.setAutoCancel(true);
////
//        // タップ時に呼ばれるIntentを生成
//        Intent intent = new Intent(this, PushReceiveActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//        // Notification表示
//        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//        manager.notify(Integer.parseInt(id), builder.build());
    }
// [END receive_message]


//    /**
//     * Schedule a job using FirebaseJobDispatcher.
//     */
//    private void scheduleJob() {
//        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
//        // [END dispatch_job]
//    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.i(TAG, "Short lived task is done.");
    }

//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.laala)
//                .setContentTitle("FCM Message")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }

}
