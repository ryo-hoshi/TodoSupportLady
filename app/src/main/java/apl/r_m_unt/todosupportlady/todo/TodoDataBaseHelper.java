package apl.r_m_unt.todosupportlady.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by ryota on 2018/03/21.
 */

public class TodoDataBaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "todo.db";
    private static final int DBVERSION = 4;
    public static final String TABLE_TODO = "todo";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIMIT_YEAR = "todo_limit_year";
    public static final String COLUMN_LIMIT_MONTH = "todo_limit_month";
    public static final String COLUMN_LIMIT_DAY = "todo_limit_day";
    public static final String COLUMN_TITLE = "todo_title";
    public static final String COLUMN_DETAIL = "todo_detail";
    public static final String COLUMN_IS_COMPLETE = "is_complete";
    public static final String COLUMN_IS_DELETE = "is_delete";

    private static final String SELECT = "SELECT ";
    private static final String COMMA = ", ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String ORDER_BY = " ORDER BY ";
    public static final String EQUAL = " = ";
    public static final String AND = " AND ";
    public static final String ASC = " ASC";

    private static final String PLACEHOLDER = "?";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_TODO + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY"+COMMA
            + COLUMN_LIMIT_YEAR + " INTEGER NOT NULL"+COMMA
            + COLUMN_LIMIT_MONTH + " INTEGER NOT NULL"+COMMA
            + COLUMN_LIMIT_DAY + " INTEGER NOT NULL"+COMMA
            + COLUMN_TITLE + " TEXT NOT NULL"+COMMA
            + COLUMN_DETAIL + " TEXT NOT NULL"+COMMA
            + COLUMN_IS_COMPLETE + " INTEGER NOT NULL"+COMMA
            + COLUMN_IS_DELETE + " INTEGER NOT NULL)";

//    public String selectTodoSql(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("SELECT ")
//                .append(COLUMN_LIMIT).append(COMMA)
//                .append(COLUMN_TITLE).append(COMMA)
//                .append(COLUMN_DETAIL).append(COMMA)
//                .append(COLUMN_IS_COMPLETE)
//                .append(" FROM ")
//                .append(TABLE_TODO)
//                .append(" WHERE ")
//                .append(COLUMN_ID).append(EQUAL).append(PLACEHOLDER);
//        return sb.toString();
//    }

    public String selectNotCompleteTodoSql() {
        StringBuilder sb = new StringBuilder();

        sb.append(SELECT)
                .append(COLUMN_ID).append(COMMA)
                .append(COLUMN_LIMIT_YEAR).append(COMMA)
                .append(COLUMN_LIMIT_MONTH).append(COMMA)
                .append(COLUMN_LIMIT_DAY).append(COMMA)
                .append(COLUMN_TITLE).append(COMMA)
                .append(COLUMN_DETAIL).append(COMMA)
                .append(COLUMN_IS_COMPLETE)
                .append(FROM)
                .append(TABLE_TODO)
                .append(WHERE)
                .append(COLUMN_IS_COMPLETE).append(EQUAL).append(TodoInfo.CompleteStatus.NotComplete.getInt())
                .append(AND).append(COLUMN_IS_DELETE).append(EQUAL).append(TodoInfo.DeleteStatus.NotDelete.getInt())
                .append(ORDER_BY)
                .append(COLUMN_LIMIT_YEAR).append(ASC)
                .append(COMMA).append(COLUMN_LIMIT_MONTH).append(ASC)
                .append(COMMA).append(COLUMN_LIMIT_DAY).append(ASC)
                .append(COMMA).append(COLUMN_ID).append(ASC);
        return sb.toString();
    }

    public String selectAllTodoSql() {
        StringBuilder sb = new StringBuilder();

        sb.append(SELECT)
                .append(COLUMN_ID).append(COMMA)
                .append(COLUMN_LIMIT_YEAR).append(COMMA)
                .append(COLUMN_LIMIT_MONTH).append(COMMA)
                .append(COLUMN_LIMIT_DAY).append(COMMA)
                .append(COLUMN_TITLE).append(COMMA)
                .append(COLUMN_DETAIL).append(COMMA)
                .append(COLUMN_IS_COMPLETE)
                .append(FROM)
                .append(TABLE_TODO)
                .append(WHERE)
                .append(COLUMN_IS_DELETE).append(EQUAL).append(TodoInfo.DeleteStatus.NotDelete.getInt())
                .append(ORDER_BY)
                .append(COLUMN_LIMIT_YEAR).append(ASC)
                .append(COMMA).append(COLUMN_LIMIT_MONTH).append(ASC)
                .append(COMMA).append(COLUMN_LIMIT_DAY).append(ASC)
                .append(COMMA).append(COLUMN_ID).append(ASC);
        return sb.toString();
    }

    public TodoDataBaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
