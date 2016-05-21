package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.Model;

import java.lang.ref.WeakReference;
import java.sql.Date;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.dataUtils.loader.LibraryCursorLoader;
import tzy.qrecitewords.dataUtils.loader.MissionDailyCursorLoader;
import tzy.qrecitewords.dataUtils.loader.SentenceCursorLoader;
import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.dataUtils.serivce.MissionService;
import tzy.qrecitewords.dataUtils.serivce.SentenceService;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Library_Adapter;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.MissionOfDay_Adapter;
import tzy.qrecitewords.javabean.Sentence;
import tzy.qrecitewords.javabean.Sentence_Adapter;
import tzy.qrecitewords.utils.IntentManager;
import tzy.qrecitewords.widget.LibraryInfoView;
import tzy.qrecitewords.widget.WordLableView;

/**
 * Created by tzy on 2016/1/1.
 */
public class ReciteWordsFragments extends BaseFragment {

    public static final int LOAD_CODE_LIBRARY = 0;
    public static final int LOAD_CODE_SENTENCE = 1;
    public static final int LOAD_CODE_MISSIONDAILY = 2;

    public static final String TAG = ReciteWordsFragments.class.getSimpleName();

    LibraryInfoView libraryInfoView;

    WordLableView lableTodayWords;

    WordLableView lableCountOfLearned;

    TextView txStart;//view_start_read

    Presenter presenter;

    TextView txDailySencente;

    TextView txMoreMission;

    View view_share;

    View viewToAnalyze;//view_to_analyze

    public ReciteWordsFragments() {
        super();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if( !hidden){
          //  getLoaderManager().restartLoader(LOAD_CODE_LIBRARY, null, presenter.getLibraryCallBack());
          //  getLoaderManager().restartLoader(LOAD_CODE_MISSIONDAILY, null, presenter.getMissionCallBack());
          //  getLoaderManager().restartLoader(LOAD_CODE_SENTENCE, null, presenter.getSentenceCallBack());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txMoreMission = (TextView) view.findViewById(R.id.tx_moreMission);

        txStart = (TextView) view.findViewById(R.id.view_start_read);
        libraryInfoView = (LibraryInfoView) view.findViewById(R.id.library_info_view);

        lableTodayWords = (WordLableView) view.findViewById(R.id.view_todayWord_num);

        lableCountOfLearned = (WordLableView) view.findViewById(R.id.view_todayWord_num_completd);

        txDailySencente = (TextView) view.findViewById(R.id.tx_daily_read_content);

        view_share = view.findViewById(R.id.view_share );
        view_share.setOnClickListener(this);

        viewToAnalyze = view.findViewById(R.id.view_to_analyze );
        viewToAnalyze.setOnClickListener(this);

        presenter = new Presenter(this);
        getLoaderManager().initLoader(LOAD_CODE_LIBRARY, null, presenter.getLibraryCallBack());
        getLoaderManager().initLoader(LOAD_CODE_MISSIONDAILY, null, presenter.getMissionCallBack());
        getLoaderManager().initLoader(LOAD_CODE_SENTENCE, null, presenter.getSentenceCallBack());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recite_word;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
       // getLoaderManager().restartLoader(LOAD_CODE_LIBRARY, null, presenter.getLibraryCallBack());
        //getLoaderManager().restartLoader(LOAD_CODE_MISSIONDAILY, null, presenter.getMissionCallBack());
        //getLoaderManager().restartLoader(LOAD_CODE_SENTENCE, null, presenter.getSentenceCallBack());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void showSelectLibrary(Library library) {
        if(library == null){
            libraryInfoView.setTxLibraryNameNull();
        }else{
            libraryInfoView.setLibraryInfo(library);
        }
    }

    public void showSentence(Sentence sentence) {
        if (sentence != null) {
            txDailySencente.setText(sentence.getContent());
        }
    }

    public void showMissionDaily(MissionOfDay missionOfDay) {
        if(missionOfDay == null){
            lableTodayWords.setLNumText( "无");
            lableCountOfLearned.setLNumText( "无");
            txMoreMission.setVisibility(View.GONE);
            return;
        }

        if(missionOfDay.getCountOfLearned() == missionOfDay.getTodayWords()){
            txMoreMission.setVisibility(View.VISIBLE);
            setStartBnEnable(false);
            txMoreMission.setOnClickListener(this);
        }else{
            txMoreMission.setVisibility(View.GONE);
            setStartBnEnable(true);
            txMoreMission.setOnClickListener(null);
        }

        lableTodayWords.setLNumText(missionOfDay.getTodayWords() + "");
        lableCountOfLearned.setLNumText(missionOfDay.getCountOfLearned() + "");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ColorDialog showChooseDailog(Library selectedLibrary, View.OnClickListener actionLisenter,ColorDialog.OnPositiveListener listener) {

        LibraryInfoView infoView = new LibraryInfoView(this.getActivity());
        infoView.setPadding(8,0,8,8);
        infoView.hideTitle(true);
        infoView.setLibraryInfo(selectedLibrary);
        //
        infoView.setwlableFamListener(actionLisenter);
        infoView.setwlableNoreadListener(actionLisenter);
        infoView.setwlableNoknownListener(actionLisenter);
        infoView.setwlableNofamListener(actionLisenter);

        ColorDialog dialog = new ColorDialog(this.getActivity());
        dialog.setTitle("请选择词组：");
        dialog.setTitleTextColor(getResources().getColor(R.color.seagreen));
        dialog.setColor(getResources().getColor(R.color.white));
        dialog.setPositiveListener(R.string.cancle, listener);
        dialog.setAnimationEnable(true);
        dialog.show();
        dialog.setCcontentView(infoView);
        return dialog;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReciteWordsFragments newInstance(int sectionNumber) {
        ReciteWordsFragments fragment = new ReciteWordsFragments();
        Bundle args = new Bundle();
        args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_start_read:
                presenter.clickToLearn();
                break;
            case R.id.tx_moreMission:
                presenter.addTodayMission();
                break;
            case R.id.view_share:
                presenter.shareSentence();
                break;
            case R.id.view_to_analyze:
                presenter.toAnalyze();
        }
    }

    public void setStartBnEnable(boolean enable){
        if(enable){
            txStart.setBackground(getResources().getDrawable(R.drawable.shape_word_num,null));
            txStart.setOnClickListener(this);
        }else{
            txStart.setBackground(getResources().getDrawable(R.drawable.shape_round_unable,null));
            txStart.setOnClickListener(null);
        }
    }


    /*---------------------内部类--------------------------------*/

    public static class Presenter {

        WeakReference<ReciteWordsFragments> reference;

        FlowContentObserver libraryObserver;

        //-----------------------sentenceDataLisenter--------------------------------//

        public LoaderCallbacks<Cursor> sentenceCallBack = new LoaderCallbacks<Cursor>() {
            @Override
            public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new SentenceCursorLoader(mContext);
            }


            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                ReciteWordsFragments fragment = getFragment();
                if (fragment == null) {
                    return;
                }
                if (data.moveToFirst()) {
                    Sentence_Adapter adapter = (Sentence_Adapter) FlowManager.getModelAdapter(Sentence.class);
                    fragment.showSentence(adapter.loadFromCursor(data));
                }

            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

            }
        };

        //-----------------------missionDataLisenter--------------------------------//
        public LoaderCallbacks<Cursor> missionCallBack = new LoaderCallbacks<Cursor>() {

            @Override
            public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                MissionDailyCursorLoader loader = new MissionDailyCursorLoader(mContext);
                return loader;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                ReciteWordsFragments fragment = getFragment();
                if (fragment == null) {
                    return;
                }
                if (data.moveToFirst()) {
                    MissionOfDay_Adapter adapter = (MissionOfDay_Adapter) FlowManager.getModelAdapter(MissionOfDay.class);
                    fragment.showMissionDaily(adapter.loadFromCursor(data));
                }else{
                    fragment.showMissionDaily(null);
                }

            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

            }
        };

        //-----------------------libraryDataLisenter--------------------------------//
        public LoaderCallbacks<Cursor> libraryCallBack = new LoaderCallbacks<Cursor>() {
            @Override
            public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                LibraryCursorLoader loader = new LibraryCursorLoader(mContext);
                return loader;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                ReciteWordsFragments fragment = getFragment();
                if (fragment == null) {
                    return;
                }
                onLibraryLoadFinishe(fragment,data);
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

            }
        };

        Context mContext;

        void onLibraryLoadFinishe(ReciteWordsFragments fragment,Cursor cursor){
            if (cursor.moveToFirst()) {
                Library_Adapter adapter = (Library_Adapter) FlowManager.getModelAdapter(Library.class);
                fragment.showSelectLibrary(adapter.loadFromCursor(cursor));
            }else{
                fragment.showSelectLibrary(null);
            }
        }

        public Presenter(final ReciteWordsFragments fragment) {
             reference = new WeakReference<ReciteWordsFragments>(fragment);
             mContext = fragment.getActivity();
             libraryObserver = new FlowContentObserver();
             libraryObserver.registerForContentChanges(mContext,Library.class);
             libraryObserver.registerForContentChanges(mContext,MissionOfDay.class);

             libraryObserver.addModelChangeListener(new FlowContentObserver.OnModelStateChangedListener() {
                 @Override
                 public void onModelStateChanged(@Nullable Class<? extends Model> table, BaseModel.Action action, @NonNull SQLCondition[] primaryKeyValues) {
                    if(table.getSimpleName().equals(Library.class.getSimpleName())){
                        final Library lib = LibrarySerivce.getSelectedLbrary();
                        fragment.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fragment.showSelectLibrary(lib);
                                }
                            });
                    }else if(table.getSimpleName().equals(MissionOfDay.class.getSimpleName())){
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment.getLoaderManager().restartLoader(LOAD_CODE_MISSIONDAILY, null, getMissionCallBack());
                            }
                        });
                    }else if(table.getSimpleName().equals(Sentence.class.getSimpleName())){
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment.getLoaderManager().restartLoader(LOAD_CODE_SENTENCE, null, getSentenceCallBack());
                            }
                        });
                    }
                 }
             });
        }

        public ReciteWordsFragments getFragment() {
            if (reference == null || reference.get() == null) {
                return null;
            }
            return reference.get();
        }

        public LoaderCallbacks<Cursor> getSentenceCallBack() {
            return sentenceCallBack;
        }

        public void setSentenceCallBack(LoaderCallbacks<Cursor> sentenceCallBack) {
            this.sentenceCallBack = sentenceCallBack;
        }

        public LoaderCallbacks<Cursor> getMissionCallBack() {
            return missionCallBack;
        }

        public void setMissionCallBack(LoaderCallbacks<Cursor> missionCallBack) {
            this.missionCallBack = missionCallBack;
        }

        public LoaderCallbacks<Cursor> getLibraryCallBack() {
            return libraryCallBack;
        }

        public void setLibraryCallBack(LoaderCallbacks<Cursor> libraryCallBack) {
            this.libraryCallBack = libraryCallBack;
        }

        public Context getmContext() {
            return mContext;
        }

        public void setmContext(Context mContext) {
            this.mContext = mContext;
        }

        ColorDialog dialog = null;
        public void clickToLearn(){
            final Library library = LibrarySerivce.getSelectedLbrary();
            final ReciteWordsFragments fragment = getFragment();

            if(library == null){//词库为空
                fragment.showPromoteDialog(null,"请先选择词库!", PromptDialog.DIALOG_TYPE_WARNING,null);
                return;
            }

            dialog = fragment.showChooseDailog(library, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WordLableView view = (WordLableView) v;
                        int count = Integer.valueOf(view.getNumText());
                        if (count == 0) {
                            fragment.showPromoteDialog(null,"词组 [" + view.getLableText() + "] : 单词数量为0,请选择其他词组",PromptDialog.DIALOG_TYPE_WRONG, null);
                        } else {
                            dialog.dismiss();
                            int famility = (Integer) view.getTag();
                            IntentManager.intentToReciteWrdsActivity(fragment.getActivity(), library, famility);
                        }
                    }
                }, new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                });

        }

        public void shareSentence(){

            Cursor cursor  = SentenceService.getSentenceCursor(null);
            Sentence_Adapter adapter = (Sentence_Adapter) FlowManager.getModelAdapter(Sentence.class);
            Sentence sentence;
            if(cursor.moveToNext()){
                sentence  = adapter.loadFromCursor(cursor);
            }else{
                sentence = new Sentence();
                sentence.setContent("2B or not 2B,is a question.");
            }

            String shareText = sentence.getContent();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");
            ReciteWordsFragments fragment = getFragment();
            fragment.startActivity(Intent.createChooser(shareIntent, "分享到"));
        }

        public void toAnalyze(){
            IntentManager.intentAnaylyzeActivity(reference.get().getActivity());
        }

        public void addTodayMission() {
            MissionService.addToDayMission();
        }
    }
}
