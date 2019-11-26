package apl.r_m_unt.todosupportlady.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import apl.r_m_unt.todosupportlady.R;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
