package tzy.qrecitewords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tzy.qrecitewords.widget.SideBar;
import tzy.qrecitewords.adapter.SortAdapter;
import tzy.qrecitewords.javabean.Word;

/**
 * 词库列表界面
 * Created by tzy on 2016/2/24.
 */
public class LibraryWordsActivity  extends AppCompatActivity {

    private SideBar sideBar;

    private TextView dialog;

    private Toolbar mToolbar;// toolbar

    private ListView listView;

    private SortAdapter adapter;
    private String[] words = {"aa","aa","aa","aa","aa","aa","aa","aa","bb","bb","bb","bb","bb","bbb","cc","cc"
            ,"cc","cc","cc","cc","cc","cc","dd","dd","dd","dd","ee","ee","ee","ee"
            ,"ff","ff","ff","ff","ff","ff","ff","ff"
            ,"gg","gg","ggg","gg","ggg","gg","gg","hh","hh","hhh","hh","hh"
            ,"uu","uu","uu","uu","uu","uu","uu","uu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lirary_words);
       // initToolBar("词库");
        listView = (ListView) findViewById(R.id.listview_words);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }

            }
        });

        List<Word> wordss = new ArrayList<Word>(100);
        Word word;
        for(String s : words) {
            word = new Word();
            word.setWord(s);
            wordss.add(word);
        }

        adapter = new SortAdapter(this,wordss);
        listView.setAdapter(adapter);
    }
    /**
     * 初始化ToolBar
     */
    private void initToolBar(String titles)
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(titles);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
