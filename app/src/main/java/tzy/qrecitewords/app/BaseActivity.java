package tzy.qrecitewords.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/4/11.
 */
public abstract class BaseActivity extends Activity {

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = LayoutInflater.from(this).inflate(getLayout(),null);
        setContentView(rootView);
    }

    public abstract int getLayout();
}
