package apl.r_m_unt.todosupportlady;

/**
 * Created by ryota on 2017/04/29.
 */
public class TodoConstant {
    private TodoConstant() {

    }

    public static final int todo_set_MAX_SIZE = 10;

    /**
     * リクエストコード情報
     */
    public enum RequestCode {
        Main(0),
        TodoList(1),
        TodoDetail(2),
        ;
        private final int id;

        private RequestCode(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }
    }
}
