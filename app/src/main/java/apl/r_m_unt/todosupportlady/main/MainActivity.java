package apl.r_m_unt.todosupportlady.main;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import apl.r_m_unt.todosupportlady.R;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    private SoundPool soundPool;
    private int soundId;
    private AudioAttributes audioAttributes;
    //CircleInfoSetting circleInfoSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 通知対応時
      //  FirebaseMessaging.getInstance().subscribeToTopic("circle_news");

        // If a notification message is tapped, any data accompanying the notification
// message is available in the intent extras. In this sample the launcher
// intent is fired when the notification is tapped, so any accompanying data would
// be handled here. If you want a different intent fired, set the click_action
// field of the notification message to the desired intent. The launcher intent
// is used when no click_action is specified.
//
// Handle possible data accompanying notification message.
// [START handle_data_extras]


//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
//        }


// [END handle_data_extras]


//        ImageButton subscribeButton = (ImageButton) findViewById(R.id.imageButton_info);
//        subscribeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // [START subscribe_topics]
//                FirebaseMessaging.getInstance().subscribeToTopic("news");
//                // [END subscribe_topics]
//
//
//                // Log and toast
//                String msg = getString(R.string.circle_info_msg_subscribed);
//                Log.d(TAG, msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//        Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
//        subscribeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // [START subscribe_topics]
//                FirebaseMessaging.getInstance().subscribeToTopic("circle_news");
//                // [END subscribe_topics]
//
//                // Log and toast
//                String msg = getString(R.string.circle_info_msg_subscribed);
//                Log.i(TAG, msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
//        logTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//
//
//                // Log and toast
//                String msg = getString(R.string.circle_msg_token_fmt, token);
//                Log.i(TAG, msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });

// テスト
        /*
        String id = getIntent().getStringExtra("id");
        String label =  getIntent().getStringExtra("label");
        String text =  getIntent().getStringExtra("text");
        if (id != null && label != null && text != null) {

            Toast.makeText(this, "バックグラウンドで通知を受信　id:" + id + ", label:" + label, Toast.LENGTH_LONG).show();
            Log.d(TAG, "id:" + id + ", label:" + label);

            //
            circleInfoSetting = CircleInfoSetting.getInstance(getApplicationContext());
            circleInfoSetting.addCircleInfo(new CircleInfo(id, label, text));
            circleInfoSetting.setExistNews(true);
            circleInfoSetting.saveInstance(getApplicationContext());
        }


        // 新着ニュースが存在する場合はインフォメーション画像を点滅させる
        if (circleInfoSetting == null) {circleInfoSetting = CircleInfoSetting.getInstance(getApplicationContext());}
        if (circleInfoSetting.isExistNews()) {

        }
        */
        //MediaPlayer mediaPlayer = new MediaPlayer();
        //Uri uri = Uri.parse("../../../../../res/raw/effect.wav");
//        try {
//            mediaPlayer.setDataSource(this, uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_TODO);
//     aPlayer.start();
//        String[] columns = new String[]{
//                MediaStore.Audio.Media._ID
//        };
//        ContentResolver cr = getApplicationContext().getContentResolver();
//        Cursor cursor = cr.query(
//                MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
//                columns,
//                null,
//                null,
//                null);
//        cursor.moveToFirst();
//        Log.d(TAG, "Cursorの値：" + cursor.toString());
//        Log.d(TAG, "columnsの値：" + columns[0]);

        //soundPool = new SoundPool(2, AudioManager.STREAM_TODO, 0);

//        audioAttributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_TODO)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                .build();
//
//        soundPool = new SoundPool.Builder()
//                .setAudioAttributes(audioAttributes)
//                .setMaxStreams(2)
//                .build();
//
//        soundId = soundPool.load(this, R.raw.effect, 1);

//        Button soundButton = (Button) findViewById(R.id.sound);
//        soundButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
//            }
//        });

// テスト
    }
}
