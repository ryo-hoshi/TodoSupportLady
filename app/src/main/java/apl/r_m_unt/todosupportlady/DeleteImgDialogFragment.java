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

/**
 * Created by ryota on 2017/05/01.
 */
public class DeleteImgDialogFragment extends DialogFragment{

    private AlertDialog dialog;
    private AlertDialog.Builder alert;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        alert = new AlertDialog.Builder(activity);

        // タイトルを設定
        TextView title = new TextView(activity);
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10,10);
        title.setText("TODOを削除しました");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        alert.setCustomTitle(title);

        // カスタムレイアウトの生成
        View alertView = activity.getLayoutInflater().inflate(R.layout.delete_dialog_layout, null);

        // delete_layout.xmlにあるボタンIDを使用して画像を設定
        ImageView imgv = (ImageView) alertView.findViewById(R.id.imageView_delete);
        imgv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Dialogを消す
                getDialog().dismiss();
            }
        });

        // ViewをdeleteDialog.Builderに追加
        alert.setView(alertView);

        // Dialogを生成
        dialog = alert.create();
        dialog.show();

        return dialog;
    }
}
