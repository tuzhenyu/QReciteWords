package tzy.qrecitewords;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.List;
import java.util.ListIterator;

import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.app.BaseActivity;
import tzy.qrecitewords.dataUtils.loader.WordsCursorLoader;
import tzy.qrecitewords.dataUtils.serivce.MissionService;
import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.Sentence;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.widget.ProgressBn;

/**
 * 背单词的Activity
 * Created by tzy on 2016/4/11.
 */
public class ReciteWordsActivity extends BaseActivity implements View.OnClickListener{

    TextView txWord;

    TextView txPhon;

    TextView txTranToFam;
    TextView txTranToNoFam;
    TextView txTranToNoKnown;

    TextView txPharse;
    TextView txNext;
    View transFamView;

    ProgressBn progressBn;

    Presenter presenter;

    public static final String PARA_LIBRARY = "PARA_LIBRARY";
    public static final String PARA_FAMLIITY = "PARA_FAMLIITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init view
        txWord = (TextView) findViewById(R.id.tx_word);
        txPhon = (TextView) findViewById(R.id.tx_phon);

        txTranToFam = (TextView) findViewById(R.id.txTranToFam);
        txTranToFam.setOnClickListener(this);

        txTranToNoFam = (TextView) findViewById(R.id.txTranToNoFam);
        txTranToNoFam.setOnClickListener(this);

        txTranToNoKnown = (TextView) findViewById(R.id.txTranToNoKnown);
        txTranToNoKnown.setOnClickListener(this);

        txPharse = (TextView) findViewById(R.id.tx_pharse);

        txNext = (TextView) findViewById(R.id.tx_next);
        txNext.setOnClickListener(this);

        transFamView = findViewById(R.id.bottom_chose_layout);
        progressBn = (ProgressBn) findViewById(R.id.progressbn);

        presenter = new Presenter(this);
        presenter.loadWords();
    }

    @Override
    public void preView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void postInitView() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_recited_words;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

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

    public void initPorgress(int max){
        progressBn.setMaxProgress(max);
        progressBn.setProgress(0);
        progressBn.setProgressText(progressBn.getProgress() + "/" + progressBn.getMax());
    }

    public void increPorgress(int diff){
        progressBn.incrementProgressBy(diff);
        progressBn.setProgressText(progressBn.getProgress() + "/" + progressBn.getMax());
    }

    public void showNextWord(Word word){
        txWord.setText(word.getWord());
        txPhon.setText(getString(R.string.word_phon,word.getPhonogram()));
        txPharse.setText(word.getParaphrase());
    }

    public void showEmptyError(){
        PromptDialog dialog = new PromptDialog(this);
        dialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
        dialog.setTitleShow(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentText("该词组为空！");
        dialog.setAnimationEnable(true);
        dialog.setPositiveListener(R.string.sure, new PromptDialog.OnPositiveListener() {
            @Override
            public void onClick(PromptDialog dialog) {
                dialog.dismiss();
                ReciteWordsActivity.this.finish();
            }
        });
        dialog.show();
    }

    public void showLearnFinished(){
        PromptDialog dialog = new PromptDialog(this);
        dialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS);
        dialog.setTitleShow(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setAnimationEnable(true);
        dialog.setContentText("您已完成本次学习任务！");
        dialog.setPositiveListener(R.string.sure, new PromptDialog.OnPositiveListener() {
            @Override
            public void onClick(PromptDialog dialog) {
                dialog.dismiss();
                ReciteWordsActivity.this.finish();
            }
        });
        dialog.show();
    }

    public void showPreNextView(boolean isShow){
        if(isShow){
            transFamView.setVisibility(View.GONE);
            txPharse.setVisibility(View.VISIBLE);
            txNext.setVisibility(View.VISIBLE);
        }else{
            txPharse.setVisibility(View.GONE);
            txNext.setVisibility(View.GONE);
            transFamView.setVisibility(View.VISIBLE);
        }
    }

    /*@Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        MissionOfDay missionOfDay = MissionService.queryTodayMission(new Date(System.currentTimeMillis()));
        int limit = missionOfDay.getTodayWords() - missionOfDay.getCountOfLearned();
        return new WordsCursorLoader(this,presenter.getFamility(this),limit,presenter.getLibrary(this));
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> datas) {
         presenter.dataComlplete(datas);
         presenter.nextWord();
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_next:
                presenter.nextWord();
                break;
            case R.id.txTranToFam:
                presenter.tranFamliarity(Library.Familiarity.familary);
                break;
            case R.id.txTranToNoFam:
                presenter.tranFamliarity(Library.Familiarity.nofamilary);
                break;
            case R.id.txTranToNoKnown:
                presenter.tranFamliarity(Library.Familiarity.noknown);
                break;
        }
    }


    public static class Presenter{

        WeakReference<ReciteWordsActivity> reference;

        ListIterator<Word> iterable;

        Word currentWord;

        public void loadWords(){
            ReciteWordsActivity activity = reference.get();
            activity.showActingPorgress(true,"");
            MissionOfDay missionOfDay = MissionService.queryTodayMission(new Date(System.currentTimeMillis()));//
            int limit = missionOfDay.getTodayWords() - missionOfDay.getCountOfLearned();
            WordSerivce.getWordsToRecited(getLibrary(reference.get()), getFamility(reference.get()), limit, new QueryTransaction.QueryResultListCallback<Word>() {
                @Override
                public void onListQueryResult(QueryTransaction queryTransaction, @Nullable List<Word> list) {
                    ReciteWordsActivity activity = reference.get();
                    if(activity == null){return;}
                    activity.showActingPorgress(false,"");
                    if(list == null || list.size() == 0){
                        activity.showEmptyError();
                        return;
                    }
                    dataComlplete(list);
                    nextWord();
                }
            });
        }

        public int getFamility(Activity activity){
            return activity.getIntent().getIntExtra(PARA_FAMLIITY,0);
        }

        public Library getLibrary(Activity activity){
            return activity.getIntent().getParcelableExtra(PARA_LIBRARY);
        }

        public Presenter(ReciteWordsActivity context ) {
            this.reference = new WeakReference<ReciteWordsActivity>(context);
        }

        public void dataComlplete(List<Word> words){
            ReciteWordsActivity activity = reference.get();
            if(activity == null){return;}
            activity.initPorgress(words.size());
            iterable = words.listIterator();
        }

        public void nextWord(){
            ReciteWordsActivity activity = reference.get();
            activity.showPreNextView(false);
            if(iterable.hasNext()){
              if(activity == null){return;}
                currentWord = iterable.next();
                activity.showNextWord(currentWord);
            }else if( !iterable.hasPrevious()){//如果往前往后都没有，说明是个空迭代器
                 activity.showEmptyError();
            }else{//迭代结束
                 activity.showLearnFinished();
            }
        }

        public void tranFamliarity(int newFam){
            if(currentWord == null){return;}
            ReciteWordsActivity activity = reference.get();
            activity.showActingPorgress(true,"");
            WordSerivce.tranFamOfWord(currentWord, newFam, new Transaction.Success() {
                @Override
                public void onSuccess(Transaction transaction) {
                    ReciteWordsActivity activity = reference.get();
                    if(activity == null){return;}
                    activity.showActingPorgress(false,"");
                    activity.increPorgress(1);
                    //
                    if(!iterable.hasNext()){//学习完成了
                        activity.showLearnFinished();
                        return;
                    }
                    activity.showPreNextView(true);
                }
            }, new Transaction.Error() {
                @Override
                public void onError(Transaction transaction, Throwable error) {
                    final ReciteWordsActivity activity = reference.get();
                    if(activity == null){return;}
                    activity.showActingPorgress(false,"");
                    activity.showPromoteDialog("", "数据库异常！", PromptDialog.DIALOG_TYPE_WRONG, new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {

                            dialog.dismiss();
                            activity.finish();
                        }
                    });
                }
            });
        }
    }

}
