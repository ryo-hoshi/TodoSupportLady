package apl.r_m_unt.todosupportlady.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by ryota on 2018/03/21.
 */

public class TodoDataBaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "todo.db";
    private static final int DBVERSION = 2;
    public static final String TABLE_TODO = "todo";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIMIT = "todo_limit";
    public static final String COLUMN_TITLE = "todo_title";
    public static final String COLUMN_DETAIL = "todo_detail";
    public static final String COLUMN_IS_COMPLETE = "is_complete";
    public static final String COLUMN_IS_DELETE = "is_delete";

    private static final String COMMA = ", ";
    public static final String EQUAL = " = ";
    public static final String AND = " AND ";
//    private static final String PLACEHOLDER = "?";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_TODO + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY"+COMMA
            + COLUMN_LIMIT + " TEXT NOT NULL"+COMMA
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

    public String selectInCompleteTodoSql(){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ")
                .append(COLUMN_ID).append(COMMA)
                .append(COLUMN_LIMIT).append(COMMA)
                .append(COLUMN_TITLE).append(COMMA)
                .append(COLUMN_DETAIL).append(COMMA)
                .append(COLUMN_IS_COMPLETE)
                .append(" FROM ")
                .append(TABLE_TODO)
                .append(" WHERE ")
                .append(COLUMN_IS_COMPLETE).append(EQUAL).append(TodoInfo.CompleteStatus.NotComplete.getInt())
                .append(AND).append(COLUMN_IS_DELETE).append(EQUAL).append(TodoInfo.DeleteStatus.NotDelete.getInt());
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
