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

    public List<TodoInfo> getTodoInfo(){

        List<TodoInfo> todoInfoList = new ArrayList<>();

        // 読み込み用としてDBを取得
        SQLiteDatabase db = todoDataBaseHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(todoDataBaseHelper.selectInCompleteTodoSql(), new String[]{});

            // 参照先を一番始めに
            boolean isEof = cursor.moveToFirst();
            while(isEof) {

                todoInfoList.add(new TodoInfo(
                        cursor.getInt(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_ID)),
//                        CommonFunction.formatDateFromString(cursor.getString(cursor.getColumnIndex(TodoDataBaseHelper.COLUMN_LIMIT))),
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

//        ContentValues nullColumnHack = new ContentValues();
//        nullColumnHack.put("Name", "yan");
//        nullColumnHack.put("Tel", "0000-1234-5678");
//        nullColumnHack.put("Age", 18);

        return db.insert(TodoDataBaseHelper.TABLE_TODO, null, values);
    }

    public long deleteTodoInfo(int todoId){
        SQLiteDatabase db = todoDataBaseHelper.getWritableDatabase();

        return db.delete(TodoDataBaseHelper.TABLE_TODO, TodoDataBaseHelper.COLUMN_ID + " = " + String.valueOf(todoId), null);
    }
//
//    private Context context;
//    // Realmのカスタム設定の場合
////        RealmConfiguration realmConfig  = new RealmConfiguration.Builder(this).build();
////        Realm realm = Realm.getInstance(realmConfig);
//
//    // Realmのデフォルト設定の場合
//    Realm realm = Realm.getDefaultInstance();
//
//    private static final String TAG = "TodoDAO";
//
//    public TodoDAO(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * TODO情報の設定
//     * @param todoSettingInfo
//     */
//    public void setTodo(TodoSettingInfo todoSettingInfo) {
//
//
//    }
//
//    /**
//     * TODO情報の設定
//     * @param todoSettingInfo
//     */
//    public void addTodo(TodoSettingInfo todoSettingInfo) {
//
//
//    }
//

}
