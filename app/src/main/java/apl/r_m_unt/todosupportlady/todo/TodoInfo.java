package apl.r_m_unt.todosupportlady.todo;

/**
 * Created by ryota on 2017/04/06.
 */
public class TodoInfo {
    private String id;
    private int hour;
    private int minute;
    private String memo;
    private boolean todoSwitch;

    public TodoInfo(String id, int hour, int minute, String memo, boolean todoSwitch) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.memo = memo;
        this.todoSwitch = todoSwitch;
    }

    public String getId() {
        return id;
    }

    public String getTodoTime() {
        return hour + ":" + minute;
    }

    public String getMemo() {
        return memo;
    }

    public boolean getTodoSwitch() {
        return todoSwitch;
    }
}
