package tzy.qrecitewords;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;
import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.net.CustomerPresenter;
import tzy.qrecitewords.net.IView;
import tzy.qrecitewords.utils.IntentManager;
import tzy.qrecitewords.utils.TextChecker;
import tzy.qrecitewords.widget.SideBar;
import tzy.qrecitewords.adapter.SortAdapter;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.widget.WordBuilderView;

/**
 * 词库列表界面
 * Created by tzy on 2016/2/24.
 */
public class LibraryWordsActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener {

    private SideBar sideBar;

    private TextView dialog;

    private Toolbar mToolbar;// toolbar

    private ListView listView;

    private SortAdapter adapter;

    private Presenter presenter;

    SearchView searchView;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lirary_words);
       // initToolBar("词库");
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listview_words);
        floatingActionButton.attachToListView(listView);

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

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return false;
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
            //adapter.notifyDataSetChanged();
        }
    }

    public ColorDialog createWordViewDialog(){
        ColorDialog colorDialog = new ColorDialog(this);
        colorDialog.setAnimationEnable(true);
        colorDialog.setTitle(" 新增单词");
        return colorDialog;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                presenter.addWord();
                break;
        }
    }

    /*-------------------------内部类----------------------------------*/
    public static class Presenter extends CustomerPresenter<Cursor>{

        WeakReference<LibraryWordsActivity> reference ;

        Cursor cursor;

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
             cursor = data;
             LibraryWordsActivity activity = null;
             if(reference != null && (activity = reference.get()) != null){
                 activity.showData(data);
             }
        }

        public void require(){
            if(cursor != null && cursor.isClosed()){
                loadData();
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

        public void addWord(){

            ColorDialog dialog = reference.get().createWordViewDialog();
            final WordBuilderView wordBuilderView = new WordBuilderView(reference.get());
            dialog.setnContentView(wordBuilderView);
            dialog.setNegativeListener(R.string.cancle, new ColorDialog.OnNegativeListener() {
                @Override
                public void onClick(ColorDialog dialog) {
                    dialog.dismiss();
                }
            });

            dialog.setPositiveListener(R.string.sure, new ColorDialog.OnPositiveListener() {
                @Override
                public void onClick(ColorDialog dialog) {
                    String word = wordBuilderView.getWord();
                    String paraphrase = wordBuilderView.getParaphrase();
                    String phonogram = wordBuilderView.getPhonogram();

                    if(TextUtils.isEmpty(word) || TextUtils.isEmpty(paraphrase)){
                        Toast.makeText(reference.get(),"单词或者注释不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextChecker.isChineseChar(word)){
                        Toast.makeText(reference.get(),"单词不能中文",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Word nWord = new Word();
                    nWord.setWord(word);
                    nWord.setPhonogram(phonogram);
                    nWord.setParaphrase(paraphrase);
                     Library library = reference.get().getIntent().getParcelableExtra(IntentManager.PARA_LIBRARY);
                    LibrarySerivce.addWord(library,nWord);
                    loadData();//刷新数据库
                    dialog.dismiss();
                    return;
                }
            });
            dialog.show();
        }
    }
}
