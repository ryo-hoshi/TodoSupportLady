package apl.r_m_unt.todosupportlady.todo;

/**
 * Created by ryota on 2017/04/06.
 */
public class TodoInfo {

    /** TODO時間デミリタ */
    private static final String TIME_DELIMITER = ":";

    private int id;
    private String limit;
    private String title;
    private String detail;
    private int isComplete;

//    public TodoInfo(int id) {
//        this.id = id;
//        this.limit = "";
//        this.isComplete = 0;
//        this.title = "タイトル";
//        this.detail = "TODO" + (id + 1 );
//    }

    public TodoInfo(int id,
                    String limit,
                    String title,
                    String detail,
                    int isComplete) {
        this.id = id;
        this.limit = limit;
        this.title = title;
        this.detail = detail;
        this.isComplete = isComplete;
    }

//    public String getTodoLimitDisplay() {
////        return String.format("%02d", hour) + TIME_DELIMITER + String.format("%02d", minute);
//        return "期限："+ DateTimeFormat.forPattern("yyyy/MM/dd").print(detail);
//        //return todoLimit;
//    }

    public int getId() { return id;}
    public String getLimit() { return limit;}
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
     * 遷移元情報
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
     * 遷移元情報
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
