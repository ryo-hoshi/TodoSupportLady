package apl.r_m_unt.todosupportlady;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

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
        SharedPreferenceData sharedPreferenceData = new SharedPreferenceData();
        if (sharedPreferenceData.isShowLadyImage(getActivity())) {

            alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("TODOを完了しました");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.complete_dialog_layout, null);

            // complete_layout.xmlにあるボタンIDを使用して画像を設定
            ImageView imgv = (ImageView) alertView.findViewById(R.id.imageView_complete);
            imgv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                Log.d("complete dialog", "clicked");

//                alert.setMessage("image clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            // ViewをCompleteDialog.Builderに追加
            alert.setView(alertView);

        } else {
            alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("TODO完了");
            alert.setMessage("TODOを完了しました");
            alert.setPositiveButton("OK", null);
//            new AlertDialog.Builder(getActivity())
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
