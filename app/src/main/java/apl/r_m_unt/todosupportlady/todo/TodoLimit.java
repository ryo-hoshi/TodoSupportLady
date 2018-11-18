package apl.r_m_unt.todosupportlady.todo;

/**
 * Created by ryota on 2018/07/21.
 */

public class TodoLimit {
    private int year;
    private int month;
    private int day;

    public TodoLimit(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /**
     * TODOの期限が同じかどうかチェック
     * @param anothorTodoLimit
     * @return
     */
    public boolean equalLimit(TodoLimit anothorTodoLimit) {
        if (this.year == anothorTodoLimit.getYear()
                && this.month == anothorTodoLimit.getMonth()
                && this.day == anothorTodoLimit.getDay()) {
            return true;
        } else {
            return false;
        }
    }
}
