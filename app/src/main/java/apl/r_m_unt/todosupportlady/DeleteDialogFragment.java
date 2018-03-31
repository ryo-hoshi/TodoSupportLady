package apl.r_m_unt.todosupportlady;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import apl.r_m_unt.todosupportlady.todo.TodoDetailFragment;
import apl.r_m_unt.todosupportlady.todo.TodoListFragment;
import apl.r_m_unt.todosupportlady.todo.TodoModel;

/**
 * Created by ryota on 2017/05/01.
 */
public class DeleteDialogFragment extends DialogFragment{

    private static final String TAG = "DeleteDialogFragment";
    public static final String TODO_ID = "TODO_ID";
    public static final String FROM_LIST_DETAIL_CD = "FROM_LIST_DETAIL_CD";
    public static final int FROM_LIST = 0;
    public static final int FROM_DETAIL = 1;
    private int fromListDetailCd;
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
//        final DialogInterface.OnClickListener listener = (dialog, which) -> {
//            dismiss();
//            Intent intent = new Intent();
//            if (getArguments() != null) {
//                intent.putExtras(getArguments());
//            }
//            getParentFragment().onActivityResult(getTargetRequestCode(), which, intent);
//        };
//        // ダイアログ表示はこれで可能
//            return new AlertDialog.Builder(getActivity())
//                    .setTitle("TODOを削除しますか？")
//                    .setPositiveButton("OK", null)
//                    .setNegativeButton("Cancel", null)
//                    .create();

        fromListDetailCd = getArguments().getInt(FROM_LIST_DETAIL_CD);

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
                todoModel.deleteTodoInfo(todoId);

                // TODO一覧を更新
                if (fromListDetailCd == FROM_LIST) {
                    todoListFragment.setTodoInfoList();

                } else {

                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        return dialogBuilder.create();
    }
}
