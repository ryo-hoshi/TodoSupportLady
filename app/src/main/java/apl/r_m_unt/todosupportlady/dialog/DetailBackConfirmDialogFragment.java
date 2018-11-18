package apl.r_m_unt.todosupportlady.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import apl.r_m_unt.todosupportlady.todo.TodoDetailFragment;

public class DetailBackConfirmDialogFragment extends DialogFragment {

    private TodoDetailFragment todoDetailFragment;
    private static final String TAG = "backConfDialogFragment";
    private static final String ARG_TITLE = "title";
//    private static final String ARG_MESSAGE = "message";

    private int mTitle;
//    private int mMessage;

    /**
     * ダイアログfragment情報を返却
     * @param title
//     * @param message
     * @return
     */
    public static DetailBackConfirmDialogFragment newInstance(int title) {
//    public static DetailBackConfirmDialogFragment newInstance(int title, int message) {
        DetailBackConfirmDialogFragment fragment = new DetailBackConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
//        args.putInt(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    // ClassCastException対応
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "■DetailBackConfirmDialogFragment　onAttach");
        // 遷移元のFragmentをチェック
        final Fragment fragment = getTargetFragment();
        if (fragment != null) {
            if(fragment instanceof TodoDetailFragment) {
                todoDetailFragment = (TodoDetailFragment) fragment;
            }
        }
        if (todoDetailFragment == null){
            throw new UnsupportedOperationException("遷移元Fragmentの取得に失敗");
        }
    }


    /**
     * リーク対策
     */
    @Override
    public void onDetach() {
        super.onDetach();
        todoDetailFragment = null;
    }

    /**
     * ダイアログfragmentがshowされた時の処理
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "■DetailBackConfirmDialogFragment　onCreateDialog");
        if (getArguments() != null) {
            mTitle = getArguments().getInt(ARG_TITLE);
//            mMessage = getArguments().getInt(ARG_MESSAGE);
        }
        // ダイアログを表示
        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
//                .setMessage(mMessage)
                // CANCEL押下時の処理
                .setNeutralButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do nothing
                            }
                        }
                )
                // YES押下時の処理
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // TODO更新画面のActivity終了を呼び出す
                                todoDetailFragment.screenFinish();
                            }
                        }
                )
                .create();
    }
}
