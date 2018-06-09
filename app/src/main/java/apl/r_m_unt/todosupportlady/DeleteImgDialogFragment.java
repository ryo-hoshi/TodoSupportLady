package apl.r_m_unt.todosupportlady;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ryota on 2017/05/01.
 */
public class DeleteImgDialogFragment extends DialogFragment{

    private AlertDialog dialog;
    private AlertDialog.Builder alert;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("TODOを削除しました");

        // カスタムレイアウトの生成
        View alertView = getActivity().getLayoutInflater().inflate(R.layout.delete_dialog_layout, null);

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
