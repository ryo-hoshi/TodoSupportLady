package apl.r_m_unt.todosupportlady;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import apl.r_m_unt.todosupportlady.common.TodoCommonFunction;
import apl.r_m_unt.todosupportlady.config.SharedPreferenceData;

//import android.app.DialogFragment;

/**
 * Created by ryota on 2018/03/10.
 */
public class CompleteImgDialogFragment extends DialogFragment{

    private AlertDialog dialog;
    private AlertDialog.Builder alert;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // TODO完了イメージ表示設定の場合は表示する。表示しない場合はメッセージのみ表示
        final SharedPreferenceData sharedPreferenceData = new SharedPreferenceData();
        Activity activity = getActivity();
        if (sharedPreferenceData.isShowLadyImage(activity)) {

            // アラートダイアログ作成
            alert = new AlertDialog.Builder(activity);

            // タイトルを設定
            TextView title = new TextView(activity);
            title.setGravity(Gravity.CENTER);
            title.setPadding(10, 10, 10,10);
            title.setText("TODOを完了しました");
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            alert.setCustomTitle(title);

            // カスタムレイアウトの生成
            View alertView = activity.getLayoutInflater().inflate(R.layout.complete_dialog_layout, null);

            // メッセージを設定
            String[] completeMsg = getResources().getStringArray(R.array.complete_msg_reika);
            Random r = new Random();
            int msgTarget = r.nextInt(completeMsg.length);
            TextView textViewComplete = (TextView)alertView.findViewById(R.id.textView_complete);
            String raikaMsg = completeMsg[msgTarget];
            if (msgTarget == 2) {
                // メッセージに呼ばれ方を設定
                String name = sharedPreferenceData.getName(getActivity());
                if (!TodoCommonFunction.isValidValue(name)) {
                    name = getResources().getString(R.string.default_name);
                }
                raikaMsg = raikaMsg + " " + name + getResources().getString(R.string.default_honorific);
            }
            textViewComplete.setText(raikaMsg);

            // 画像を設定
            ImageView imgv = (ImageView) alertView.findViewById(R.id.imageView_complete);
            int n = r.nextInt(2);
            if (n < 1) {
                imgv.setImageResource(R.drawable.complete_reika1);
            } else {
                imgv.setImageResource(R.drawable.complete_reika2);
            }
//            LinearLayout layout = (LinearLayout)imageView_layout

            // タップで削除する
            alertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    getDialog().dismiss();
                }
            });
//            imgv.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
////                Log.d("complete dialog", "clicked");
//
////                alert.setMessage("image clicked");
//                    // Dialogを消す
//                    getDialog().dismiss();
//                }
//            });

            // ViewをCompleteDialog.Builderに追加
            alert.setView(alertView);

        } else {
            alert = new AlertDialog.Builder(activity);
            alert.setTitle("TODOを完了しました");
            alert.setPositiveButton("OK", null);
//            new AlertDialog.Builder(activity)
//                    .setTitle("TODO完了")
//                    .setMessage("TODOを完了しました")
//                    .setPositiveButton("OK", null)
//                    .show();
        }

        // Dialogを生成
        dialog = alert.create();
        dialog.show();
        return dialog;
    }

//    private void setMessage(String message) {
//
//    }
}
