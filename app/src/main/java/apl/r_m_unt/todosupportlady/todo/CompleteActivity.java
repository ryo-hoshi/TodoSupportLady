package apl.r_m_unt.todosupportlady.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import apl.r_m_unt.todosupportlady.R;

public class CompleteActivity extends AppCompatActivity {

    private static final String TAG = "SoundActivity";
    private static CompleteController soundController;
    //private MediaPlayer mediaPlayer;
//    private Button buttonStop;
//    private Button buttonStopAndPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        // TODOの音量調整を可能にする
        //setVolumeControlStream(AudioManager.STREAM_TODO);

        // TODOとメッセージ再生情報の準備（準備完了後、TODO音を再生）
//        if (soundController == null) {
//            soundController = new SoundController(getApplicationContext(), R.raw.effect, R.raw.effect2);
//        }

        //soundController.setMediaPlayer(this, R.raw.effect);
        // TODO音をループ再生
        //soundController.play(true);
        //soundController.play(SoundController.SoundType.TODO);
/*
        mediaPlayer = MediaPlayer.create(this, R.raw.effect);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
*/
//        // 停止ボタン押下時の処理
//        findViewById(R.id.button_stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 再生中の音を終了
//                soundController.stop();
////                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
////                    mediaPlayer.stop();
////                    Log.i(TAG, "SoundController mediaPlayer STOP");
////                    mediaPlayer.reset();
////                    Log.i(TAG, "SoundController mediaPlayer RESET");
////                    mediaPlayer.release();
////                    Log.i(TAG, "SoundController mediaPlayer RELEASE");
////                }
//            }
//        });

//        // ボイス再生ボタン押下時の処理
//        findViewById(R.id.button_stop_and_play).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                soundController.messagePlay();
////                // 再生中の音を終了
////                soundController.stop();
////
////                // ボイス再生用に再作成
////                soundController = new SoundController();
////                // 再生する音を設定
////                soundController.setMediaPlayer(getApplicationContext(), R.raw.effect2);
////                // 1回のみ再生
////                soundController.play(false);
//////                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//////                    mediaPlayer.stop();
//////                    Log.i(TAG, "SoundController mediaPlayer STOP");
//////                    mediaPlayer.reset();
//////                    Log.i(TAG, "SoundController mediaPlayer RESET");
//////                    mediaPlayer.release();
//////                    Log.i(TAG, "SoundController mediaPlayer RELEASE");
//////                }
//////                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.effect2);
//////                mediaPlayer.start();
//            }
//        });
    }
}
