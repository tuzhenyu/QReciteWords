package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.melnykov.fab.FloatingActionButton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.adapter.LibrarysAdapter;
import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.javabean.Libraries;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.net.DoubleCacheIView;
import tzy.qrecitewords.net.DoubleCachePresenter;
import tzy.qrecitewords.net.UrlValue;
import tzy.qrecitewords.widget.LibraryInfoView;

/**
 * Created by tzy on 2016/1/1.
 */
public class LibraryFragment extends BaseFragment implements AdapterView.OnItemClickListener,DoubleCacheIView<Libraries> {

    public static final String TAG = LibraryFragment.class.getSimpleName();

    DoubleCachePresenter<Libraries> presenter;

    LibrarysAdapter librarysAdapter;

    ListView listView;

    FloatingActionButton floatingActionButton;

    LibraryInfoView libraryInfoView;

    public LibraryFragment() {
        super();
    }

    /**--------生命周期相关方法-------------*/
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this,Volley.newRequestQueue(this.getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        listView = (ListView) view.findViewById(R.id.listView);

        //String[] ss = new String[]{"四级", "六级", "雅思", "托福", "六年级英语", "高中英语", "初中英语", "小学英语"};
        //List<String> stringList = Arrays.asList(ss);
        //List<Library> libraries = new LinkedList<>();
        //for (String libraryName : stringList) {
        //    libraries.add(new Library(libraryName));
       // }
        //librarysAdapter = new LibrarysAdapter(libraries, this.getActivity())
        //listView.setAdapter(librarysAdapter);
        libraryInfoView = (LibraryInfoView) view.findViewById(R.id.library_info_view);
        rquestData();
        listView.setOnItemClickListener(this);
        floatingActionButton.attachToListView(listView);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_library;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));*/
    }

    @Override
    public void onResume() {
        super.onResume();
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
        presenter.destory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LibraryFragment newInstance(int sectionNumber) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        librarysAdapter.notifyDataSetChanged();//刷新界面
    }

    @Override
    public void onClick(View v) {

    }

    /**-----------视图操作方法----------------*/
    @Override
    public void rquestData() {
        presenter.loadDataWithCache();
    }

    @Override
    public void requestMoreData(int page) {

    }

    @Override
    public void showLocalData(Libraries data) {
        if(data == null || data.getLibraries() == null){return;}
        if (librarysAdapter == null) {
            librarysAdapter = new LibrarysAdapter(data.getLibraries(),this.getActivity());
            listView.setAdapter(librarysAdapter);
        }else{
            librarysAdapter.setLibraries(data.getLibraries());
        }
    }

    @Override
    public void showNetData(Libraries data) {
        showLocalData(data);
    }

    @Override
    public void showMoreData(Libraries data) {

    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getActivity(),error,Toast.LENGTH_SHORT).show();
    }

    /**------------------------内部类---------------------*/

    /**
     * 处理业务逻辑的类
     * */
    public static class Presenter extends DoubleCachePresenter<Libraries>{

        Set<Library> mLibraries;

        public Presenter(DoubleCacheIView<Libraries> iView, RequestQueue queue) {
            super(iView, queue,LoadType.CACHE);
            mLibraries = new HashSet<>();
        }

        @Override
        public void preLoadLocalData() {

        }

        @Override
        public void loadLocalData() {
            Libraries libraries = LibrarySerivce.getLibrariesLocal();
            postLocalData(libraries);
        }

        @Override
        public void postLocalData(Libraries data) {
            mLibraries.addAll(data.getLibraries());
            super.postLocalData(data);
        }

        @Override
        public void preRequestDataFromNet() {

        }

        @Override
        public void requestDataFromNet() {
              String url = UrlValue.getLibrariesUrl();
              this.requestData(url,Libraries.class);
        }

        @Override
        public void dataDealFromNet(Libraries data, Runnable run) {
            if(data == null || data.getLibraries() == null){return;}
            Set<Library> nDatas = new HashSet<>(data.getLibraries());
            nDatas.addAll(mLibraries);//新旧数据的结合
            mLibraries.clear();
            mLibraries = null;
            mLibraries = nDatas;
            Libraries libraries = new Libraries();
            super.dataDealFromNet(libraries, run);
        }
    }


}
