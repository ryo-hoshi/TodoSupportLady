package apl.r_m_unt.todosupportlady.todo;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;


public class CompleteController {

    private static final String TAG = "CompleteController";
    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private Context context;
    private int alarmSoundId;
    private int messageSoundId;
    private AudioAttributes audioAttributes;
    private static final int POOL_SIZE = 2;

    public CompleteController(Context context, int alarmUri, int massageUri) {
        this.context = context;

    }

}
