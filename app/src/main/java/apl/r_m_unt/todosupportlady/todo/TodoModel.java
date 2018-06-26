package apl.r_m_unt.todosupportlady.todo;

//import io.realm.Realm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TodoModel {

    TodoDataBaseHelper todoDataBaseHelper;

    private TodoModel() {

    }

    public TodoModel(Context context) {
        todoDataBaseHelper = new TodoDataBaseHelper(context);
    }

    public List<TodoInfo> getTodoInfo(int completeStatus){

        List<TodoInfo> todoInfoList = new ArrayList<>();

        // 読み込み用としてDBを取得
        SQLiteDatabase db = todoDataBaseHelper.getReadableDatabase();
        try {
                Cursor cursor;
            if (TodoInfo.CompleteStatus.NotComplete.getInt() == completeStatus) {
                cursor = db.rawQuery(todoDataBaseHelper.selectNotCompleteTodoSql(), new String[]{});
            } else {
                cursor = db.rawQuery(todoDataBaseHelper.selectAllTodoSql(), new String[]{});
            }

            // 参照先を一番始めに
            boolean isEof = cursor.moveToFirst();
            while(isEof) {

                todoInfoList.add(new TodoInfo(
                        cursor.getInt(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_LIMIT)),
                        cursor.getString(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_DETAIL)),
                        cursor.getInt(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_IS_COMPLETE))
                ));

                isEof = cursor.moveToNext();
            }
            cursor.close();
        } finally {
            db.close();
        }

        return todoInfoList;
    }

    /**
     * TODO情報の登録
     * @param todoInfo TODO情報
     */
    public long insertTodoInfo(TodoInfo todoInfo) {
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDataBaseHelper.COLUMN_LIMIT, todoInfo.getLimit());
        values.put(TodoDataBaseHelper.COLUMN_TITLE, todoInfo.getTitle());
        values.put(TodoDataBaseHelper.COLUMN_DETAIL, todoInfo.getDetail());
        values.put(TodoDataBaseHelper.COLUMN_IS_COMPLETE, todoInfo.getIsComplete());
        values.put(TodoDataBaseHelper.COLUMN_IS_DELETE, TodoInfo.DeleteStatus.NotDelete.getInt());

        return db.insert(TodoDataBaseHelper.TABLE_TODO, null, values);
    }

    /**
     * TODO完了
     */
    public long complete(int todoId) {
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDataBaseHelper.COLUMN_IS_COMPLETE, TodoInfo.CompleteStatus.Complete.getInt());

        String where = TodoDataBaseHelper.COLUMN_ID + TodoDataBaseHelper.EQUAL + todoId;

        return db.update(TodoDataBaseHelper.TABLE_TODO, values, where, null);
    }

    /**
     * TODO削除
     */
    public long delete(int todoId) {
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDataBaseHelper.COLUMN_IS_DELETE, TodoInfo.DeleteStatus.Delete.getInt());

        String where = TodoDataBaseHelper.COLUMN_ID + TodoDataBaseHelper.EQUAL + todoId;

        return db.update(TodoDataBaseHelper.TABLE_TODO, values, where, null);
    }

    /**
     * TODO再登録
     * @param todoId TODOID
     */
    public long reRegister(int todoId) {
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDataBaseHelper.COLUMN_IS_COMPLETE, TodoInfo.CompleteStatus.NotComplete.getInt());
        values.put(TodoDataBaseHelper.COLUMN_IS_DELETE, TodoInfo.DeleteStatus.NotDelete.getInt());

        String where = TodoDataBaseHelper.COLUMN_ID + TodoDataBaseHelper.EQUAL + todoId;

        return db.update(TodoDataBaseHelper.TABLE_TODO, values, where, null);
    }

    /**
     * TODO情報の更新
     * @param todoInfo TODO情報
     */
    public long updateTodoInfo(TodoInfo todoInfo) {
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDataBaseHelper.COLUMN_LIMIT, todoInfo.getLimit());
        values.put(TodoDataBaseHelper.COLUMN_TITLE, todoInfo.getTitle());
        values.put(TodoDataBaseHelper.COLUMN_DETAIL, todoInfo.getDetail());
        values.put(TodoDataBaseHelper.COLUMN_IS_COMPLETE, todoInfo.getIsComplete());

        String where = TodoDataBaseHelper.COLUMN_ID + TodoDataBaseHelper.EQUAL + todoInfo.getId();

        return db.update(TodoDataBaseHelper.TABLE_TODO, values, where, null);
    }
}
