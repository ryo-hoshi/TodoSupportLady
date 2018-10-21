package apl.r_m_unt.todosupportlady.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import apl.r_m_unt.todosupportlady.R;
import apl.r_m_unt.todosupportlady.model.TodoDao;
import apl.r_m_unt.todosupportlady.todo.TodoDetailFragment;
import apl.r_m_unt.todosupportlady.todo.TodoListFragment;

/**
 * Created by ryota on 2017/05/01.
 */
public class DeleteConfirmDialogFragment extends DialogFragment{

    private static final String TAG = "DelConfDialogFragment";
    public static final String TODO_ID = "TODO_ID";
    public static final String TODO_TITLE = "TODO_TITLE";

    /** 遷移元画面 */
    private int todoId;

    private TodoListFragment todoListFragment;
    private TodoDetailFragment todoDetailFragment;

    // ClassCastException対応
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 遷移元のFragmentをチェック
        final Fragment fragment = getTargetFragment();
        if (fragment != null) {
            if(fragment instanceof TodoListFragment) {
                todoListFragment = (TodoListFragment) fragment;
            } else if(fragment instanceof TodoDetailFragment) {
                todoDetailFragment = (TodoDetailFragment) fragment;
            }
        }
        if (todoListFragment == null && todoDetailFragment == null){
            throw new UnsupportedOperationException("遷移元Fragmentの取得に失敗");
        }
    }

    /**
     * リーク対策
     */
    @Override
    public void onDetach() {
        super.onDetach();
        todoListFragment = null;
        todoDetailFragment = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "DeleteDialog表示処理");

        // ダイアログの表示内容と呼び出しもとに値を返す設定
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.message_todo_delete_confirm));
        // TODO一覧で削除の場合
        if (todoListFragment != null ) {
            dialogBuilder.setMessage(getArguments().getString(TODO_TITLE));
        }

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "OK押下");
                todoId  = getArguments().getInt(TODO_ID);
                TodoDao todoDao = new TodoDao(getActivity());
                // TODO削除
                todoDao.delete(todoId);

                // TODO一覧で削除の場合
                if (todoListFragment != null ) {
                    // OKの結果のみ呼び出しもとに渡すのでIntentはnull
                    todoListFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                } else {
                    todoDetailFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                }
            }
        });
        dialogBuilder.setNeutralButton("CANCEL", null);
        return dialogBuilder.create();
    }
}
