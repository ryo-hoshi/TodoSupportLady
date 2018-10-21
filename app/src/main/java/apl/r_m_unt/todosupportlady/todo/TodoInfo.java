package apl.r_m_unt.todosupportlady.todo;

/**
 * Created by ryota on 2017/04/06.
 */
public class TodoInfo {

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
