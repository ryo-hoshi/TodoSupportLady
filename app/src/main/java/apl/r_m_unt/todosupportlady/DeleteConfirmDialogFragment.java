package apl.r_m_unt.todosupportlady;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import apl.r_m_unt.todosupportlady.todo.TodoDetailFragment;
import apl.r_m_unt.todosupportlady.todo.TodoInfo;
import apl.r_m_unt.todosupportlady.todo.TodoListFragment;
import apl.r_m_unt.todosupportlady.todo.TodoModel;

/**
 * Created by ryota on 2017/05/01.
 */
public class DeleteConfirmDialogFragment extends DialogFragment{

    private static final String TAG = "DelConfDialogFragment";
    public static final String TODO_ID = "TODO_ID";
    public static final String TRANSITION_SOURCE_CD = "TRANSITION_SOURCE_CD";
    public static final int REQUEST_DELETE_CONFIRM_DIALOG = 0;

    /** 遷移元画面 */
//    private int transitionSourceCd;
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
        dialogBuilder.setTitle("TODOを削除しますか？");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "OK押下");
                todoId  = getArguments().getInt(TODO_ID);
                TodoModel todoModel = new TodoModel(getActivity());
                // TODO削除
                todoModel.updateIsDelete(todoId, TodoInfo.DELETE);

//                // 遷移元情報確認
//                transitionSourceCd = getArguments().getInt(TRANSITION_SOURCE_CD);


                // TODO一覧で削除の場合
                if (todoListFragment != null ) {
                    // TODO一覧を再設定する
                    todoListFragment.setTodoInfoList();
                    // OKの結果のみ呼び出しもとに渡すのでIntentはnull
                    todoListFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                } else {
                    todoDetailFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                }

                // 削除時の画像ダイアログを表示する
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DialogFragment dialogFragment = new DeleteImgDialogFragment();
                dialogFragment.show(fragmentManager, "delete");
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        return dialogBuilder.create();
    }

    /**
     * 遷移元情報
     */
    public enum TransitionSource {
        TodoList(0),
        TodoDetail(1),
        ;
        private final int id;

        private TransitionSource(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }
    }
}
