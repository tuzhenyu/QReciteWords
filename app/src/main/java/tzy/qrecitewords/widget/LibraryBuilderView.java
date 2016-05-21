package tzy.qrecitewords.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/5/18.
 */
public class LibraryBuilderView extends LinearLayout {

    EditText ch_name_editor;

    EditText en_name_editor;


    public LibraryBuilderView(Context context) {
        this(context, null);
    }

    public LibraryBuilderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LibraryBuilderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_library_build, this, true);
        ch_name_editor = (EditText) findViewById(R.id.ed_library_zh_name_input);
        en_name_editor = (EditText) findViewById(R.id.ed_library_en_name_input);

    }

    public String  getCHName(){
        return ch_name_editor.getText().toString();
    }

    public String  getZNName(){
        return en_name_editor.getText().toString();
    }
}
