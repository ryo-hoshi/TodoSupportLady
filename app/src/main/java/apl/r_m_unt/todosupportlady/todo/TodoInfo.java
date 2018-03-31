package apl.r_m_unt.todosupportlady.todo;

/**
 * Created by ryota on 2017/04/06.
 */
public class TodoInfo {

    public static final int INCOMPLETE = 0;
    public static final int COMPLETE = 1;

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


//    private String id;
//    private int hour;
//    private int minute;
//    private String memo;
//    private boolean todoSwitch;
//
//    public TodoInfo(String id, int hour, int minute, String memo, boolean todoSwitch) {
//        this.id = id;
//        this.hour = hour;
//        this.minute = minute;
//        this.memo = memo;
//        this.todoSwitch = todoSwitch;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getTodoTime() {
//        return hour + ":" + minute;
//    }
//
//    public String getMemo() {
//        return memo;
//    }
//
//    public boolean getTodoSwitch() {
//        return todoSwitch;
//    }
}
