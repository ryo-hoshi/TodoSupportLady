package apl.r_m_unt.todosupportlady.preferences;

/**
 * TODO設定情報
 * 設定したTODO1件分の情報を管理するクラス
 *
 * Created by ryota on 2017/04/22.
 */
public class TodoSettingInfo {

    /** TODO設定ON */
    public static final boolean TODO_SWITCH_ON = true;
    /** TODO設定OFF */
    public static final boolean TODO_SWITCH_OFF = false;
    /** TODO時間デミリタ */
    private static final String TIME_DELIMITER = ":";

    private int todoNo;
    private int hour;
    private int minute;
    private boolean todoSwitch;
    private int todoMsgNo;
    private String memo;

    TodoSettingInfo(int todoNo, int todoIndex) {
        this.todoNo = todoNo;
        this.hour = 0;
        this.minute = 0;
        this.todoSwitch = false;
        this.todoMsgNo = 0;
        this.memo = "TODO" + (todoIndex + 1 );
    }

    public TodoSettingInfo(int todoNo,
                           int hour,
                           int minute,
                           boolean todoSwitch,
                           int todoMsgNo,
                           String memo) {
        this.todoNo = todoNo;
        this.hour = hour;
        this.minute = minute;
        this.todoSwitch = todoSwitch;
        this.todoMsgNo = todoMsgNo;
        this.memo = memo;
    }

    public String getTodoTime() {
        return String.format("%02d", hour) + TIME_DELIMITER + String.format("%02d", minute);
    }

    public String getMemo() {
        return memo;
    }

    public boolean getTodoSwitch() {
        return todoSwitch;
    }
    public void setTodoSwitch(boolean todoSwitch) {this.todoSwitch = todoSwitch;}

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public int getTodoMsgNo() { return todoMsgNo; }

    public int getTodoNo() { return todoNo;}
}
