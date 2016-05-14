package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.sql.Date;

import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.dataUtils.serivce.MissionService;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.Sentence;
import tzy.qrecitewords.net.CustomerPresenter;
import tzy.qrecitewords.net.DoubleCacheIView;
import tzy.qrecitewords.net.DoubleCachePresenter;
import tzy.qrecitewords.utils.IntentManager;
import tzy.qrecitewords.widget.LibraryInfoView;
import tzy.qrecitewords.widget.WordLableView;

/**
 * Created by tzy on 2016/1/1.
 */
public class ReciteWordsFragments extends BaseFragment implements DoubleCacheIView {

    public static final String TAG = ReciteWordsFragments.class.getSimpleName();

    LibraryInfoView libraryInfoView;

    WordLableView lableTodayWords;

    WordLableView lableCountOfLearned;

    View viewStartRead;

    TextView txStart;//view_start_read

    public ReciteWordsFragments() {
        super();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txStart = (TextView) view.findViewById(R.id.view_start_read);
        libraryInfoView = (LibraryInfoView) view.findViewById(R.id.library_info_view);
        txStart.setOnClickListener(this);

        lableTodayWords = (WordLableView) view.findViewById(R.id.view_todayWord_num);

        lableCountOfLearned = (WordLableView) view.findViewById(R.id.view_todayWord_num_completd);
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

    }

    @Override
    public void onStart() {
        super.onStart();
        rquestData();
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
        switch(v.getId()){
            case R.id.view_start_read:
                IntentManager.intentToReciteWrdsActivity(this.getActivity(),null);
                break;
        }
    }

    /**---------------------视图操作方法----------------------------*/

    @Override
    public void rquestData() {
        Library library = LibrarySerivce.getSelectedLbrary();
        if(library == null){
            libraryInfoView.setTxLibraryNameNull();
        }else{
            libraryInfoView.setLibraryInfo(library);
        }

        MissionOfDay missionOfDay = MissionService.queryTodayMission(new Date(System.currentTimeMillis()));
        lableTodayWords.setLNumText(missionOfDay.getTodayWords() + "");
        lableCountOfLearned.setLNumText(missionOfDay.getCountOfLearned() + "");


    }

    @Override
    public void requestMoreData(int page) {

    }

    @Override
    public void showLocalData(Object o) {

    }

    @Override
    public void showNetData(Object o) {

    }

    @Override
    public void showMoreData(Object data) {

    }

    @Override
    public void showErrorMsg(String error) {

    }

    /*---------------------内部类--------------------------------*/

    public class Presenter extends DoubleCachePresenter{

        public Presenter(DoubleCacheIView iView, RequestQueue queue, LoadType laodType) {
            super(iView, queue, laodType);
        }

        @Override
        public void preLoadLocalData() {

        }

        @Override
        public void loadLocalData() {

        }

        @Override
        public void preRequestDataFromNet() {

        }

        @Override
        public void requestDataFromNet() {

        }

        @Override
        public void dataDealFromNet(Object data) {

        }
    }
}
