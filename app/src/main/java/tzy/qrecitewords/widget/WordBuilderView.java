package tzy.qrecitewords.widget;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/5/18.
 */
public class WordBuilderView  extends LinearLayout{

    EditText wordEditor;

    EditText wordParaphraseEditor;

    EditText wordPhonogramEditor;


    public WordBuilderView(Context context) {
        this(context, null);
    }

    public WordBuilderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WordBuilderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    void initView(Context context){
         LayoutInflater.from(context).inflate(R.layout.view_word_builder, this, true);
         wordEditor = (EditText) findViewById(R.id.word_input);
         wordParaphraseEditor = (EditText) findViewById(R.id.word_paraphrase_input);
         wordPhonogramEditor = (EditText) findViewById(R.id.word_phonogram_input);
         TextInputLayout word_editorWrapper = (TextInputLayout) findViewById(R.id.word_editorWrapper);
         TextInputLayout word_paraphrase_editorWrapper = (TextInputLayout) findViewById(R.id.word_paraphrase_editorWrapper);
         TextInputLayout word_phonogram_editorWrapper = (TextInputLayout) findViewById(R.id.word_phonogram_editorWrapper);
         word_editorWrapper.setHint("请输入单词");
         word_paraphrase_editorWrapper.setHint("请输入注释");
         word_phonogram_editorWrapper.setHint("请输入音标(选填)");
    }

    public String  getWord(){
        return wordEditor.getText().toString();
    }

    public String  getParaphrase(){
       return wordParaphraseEditor.getText().toString();
    }

    public String getPhonogram(){return wordPhonogramEditor.getText().toString();}
}
