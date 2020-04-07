package apl.r_m_unt.todosupportlady.todo;

import android.util.Log;

import apl.r_m_unt.todosupportlady.common.TodoCommonFunction;

/**
 * Created by ryota on 2017/04/06.
 */
public class TodoInfo {

    private static final String TAG = "TodoInfo";

    /** TODO時間デミリタ */
    private static final String TIME_DELIMITER = ":";

    private int id;
    private TodoLimit todoLimit;
    private String title;
    private String detail;
    private int isComplete;

    public TodoInfo(int id,
                    TodoLimit todoLimit,
                    String title,
                    String detail,
                    int isComplete) {
        // 自動保存時のタイトル未設定時の対応
        if (title == null || title.isEmpty()) {
            title = "タイトル未設定";
        }

        this.id = id;
        this.todoLimit = todoLimit;
        this.title = title;
        this.detail = detail;
        this.isComplete = isComplete;
    }

    public int getId() { return id;}
    public TodoLimit getTodoLimit() { return todoLimit;}
    public String getTitle() {
        return title;
    }
    public String getDetail() {
        return detail;
    }
    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {this.isComplete = isComplete;}

    /**
     * TODO情報が変更されたかどうかチェックする
     * @param anothorTodoInfo
     * @return 比較結果
     */
    public boolean isChangeTodoInfo(TodoInfo anothorTodoInfo) {

        Log.d(TAG, "今の期限（年）：" + this.todoLimit.getYear());
        Log.d(TAG, "今の期限（月）：" + this.todoLimit.getMonth());
        Log.d(TAG, "今の期限（日）：" + this.todoLimit.getDay());
        Log.d(TAG, "今のタイトル：" + this.title);
        Log.d(TAG, "今の詳細：" + this.detail);

        Log.d(TAG, "最初の期限（年）：" + anothorTodoInfo.getTodoLimit().getYear());
        Log.d(TAG, "最初の期限（月）：" + anothorTodoInfo.getTodoLimit().getMonth());
        Log.d(TAG, "最初の期限（日）：" + anothorTodoInfo.getTodoLimit().getDay());
        Log.d(TAG, "最初のタイトル：" + anothorTodoInfo.getTitle());
        Log.d(TAG, "最初の詳細：" + anothorTodoInfo.getDetail());

        // 期限が一致かつタイトルが一致かつ詳細が一致の場合はfalse
        if (this.todoLimit.equalLimit(anothorTodoInfo.getTodoLimit())
            && this.title.equals(anothorTodoInfo.getTitle())
            && ((!TodoCommonFunction.isValidValue(this.detail) && !TodoCommonFunction.isValidValue(anothorTodoInfo.getDetail()))
                || (TodoCommonFunction.isValidValue(this.detail) && this.detail.equals(anothorTodoInfo.getDetail()))
        )) {

            return false;
        } else {
            return true;
        }
    }

    /**
     * TODO情報が入力されているかどうかチェックする
     * @return チェック結果
     */
    public boolean isInputTodoInfo() {

        Log.d(TAG, "今のタイトル：" + this.title);
        Log.d(TAG, "今の詳細：" + this.detail);

        // タイトルまたは詳細が入力されている場合はfalse
        if (TodoCommonFunction.isValidValue(this.title)
                || TodoCommonFunction.isValidValue(this.detail)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * 削除情報
     */
    public enum DeleteStatus {
        NotDelete(0),
        Delete(1),
        ;
        private final int id;

        private DeleteStatus(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }
    }

    /**
     * 完了情報
     */
    public enum CompleteStatus {
        NotComplete(0),
        Complete(1),
        ;
        private final int id;

        private CompleteStatus(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }
    }
}
