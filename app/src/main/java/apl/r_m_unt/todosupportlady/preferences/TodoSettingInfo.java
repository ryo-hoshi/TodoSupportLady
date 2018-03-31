package apl.r_m_unt.todosupportlady.preferences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * TODO設定情報
 * 設定したTODO1件分の情報を管理するクラス
 *
 * Created by ryota on 2017/04/22.
 */
public class TodoSettingInfo {
// 使わない予定
    /** TODO時間デミリタ */
    private static final String TIME_DELIMITER = ":";

    private int todoNo;
    private DateTime todoLimit;
    private boolean isComplete;
    private int todoMsgNo;
    private String title;
    private String memo;

    public TodoSettingInfo(int todoNo, int todoIndex) {
        this.todoNo = todoNo;
        this.isComplete = false;
        this.todoMsgNo = 0;
        this.title = "タイトル";
        this.memo = "TODO" + (todoIndex + 1 );
    }

    public TodoSettingInfo(int todoNo,
                           DateTime todoLimit,
                           boolean isComplete,
                           int todoMsgNo,
                           String title,
                           String memo) {
        this.todoNo = todoNo;
        this.todoLimit = todoLimit;
        this.isComplete = isComplete;
        this.todoMsgNo = todoMsgNo;
        this.title = title;
        this.memo = memo;
    }

    public String getTodoLimitDisplay() {
//        return String.format("%02d", hour) + TIME_DELIMITER + String.format("%02d", minute);
        return "期限："+DateTimeFormat.forPattern("yyyy/MM/dd").print(todoLimit);
        //return todoLimit;
    }

    public String getTitle() {
        return title;
    }
    public String getMemo() {
        return memo;
    }

    public boolean getIsComplete() {
        return isComplete;
    }
    public void setIsComplete(boolean isComplete) {this.isComplete = isComplete;}

    public int getTodoMsgNo() { return todoMsgNo; }

    public int getTodoNo() { return todoNo;}
}
