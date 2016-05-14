package tzy.qrecitewords;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.net.CustomerPresenter;
import tzy.qrecitewords.net.IView;
import tzy.qrecitewords.utils.IntentManager;
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

    private Presenter presenter;

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
                int index = Word.Alphabet.indexOf(s.charAt(0));
                int position = adapter.getPositionForSection(index);
                if (position != -1) {
                    listView.setSelection(position);
                }

            }
        });

        presenter = new Presenter(this);
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
        presenter.loadData();
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

    public void showData(Cursor cursor){
        if(adapter == null){
            adapter = new SortAdapter(this,cursor,true);
            listView.setAdapter(adapter);
        }else{
            adapter.changeCursor(cursor);
            adapter.notifyDataSetChanged();
        }
    }
    /*-------------------------内部类----------------------------------*/
    public static class Presenter extends CustomerPresenter<Cursor>{

        WeakReference<LibraryWordsActivity> reference ;

        public Presenter(LibraryWordsActivity activity) {
            super();
            reference = new WeakReference<LibraryWordsActivity>(activity);
        }

        @Override
        public void requestData(final ResultListener resultListener) {
            Library library = LibrarySerivce.getSelectedLbrary();
            WordSerivce.queryWordsFromLibrary(library, new QueryTransaction.QueryResultCallback<Word>() {
                @Override
                public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Word> tResult) {
                    resultListener.onSuccess(tResult.getCursor());
                }
            });
        }

        @Override
        public void showData(Cursor data) {
             LibraryWordsActivity activity = null;
             if(reference != null && (activity = reference.get()) != null){
                 activity.showData(data);
             }
        }

        public void loadData() {

            requestData(new ResultListener<Cursor>() {
                @Override
                public void onSuccess(Cursor result) {
                    showData(result);
                }

                @Override
                public void onFail(Error error) {

                }
            });
        }
    }
}
