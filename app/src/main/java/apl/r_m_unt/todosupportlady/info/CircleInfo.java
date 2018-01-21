package apl.r_m_unt.todosupportlady.info;

/**
 * Created by ryota on 2017/05/17.
 */

public class CircleInfo {

        private String id;
        private String label;
        private String text;

        public CircleInfo(String id, String label, String text) {
            this.id = id;
            this.label = label;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public String getText() {
            return text;
        }
}
