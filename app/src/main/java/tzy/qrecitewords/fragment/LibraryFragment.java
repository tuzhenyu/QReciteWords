package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.melnykov.fab.FloatingActionButton;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.adapter.LibrarysAdapter;
import tzy.qrecitewords.dataUtils.serivce.LibrarySerivce;
import tzy.qrecitewords.javabean.Libraries;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.net.DoubleCacheIView;
import tzy.qrecitewords.net.DoubleCachePresenter;
import tzy.qrecitewords.net.UrlValue;
import tzy.qrecitewords.utils.DownLoadManager;
import tzy.qrecitewords.utils.IntentManager;
import tzy.qrecitewords.widget.LibraryInfoView;
import tzy.qrecitewords.widget.ProgressBn;

/**
 * Created by tzy on 2016/1/1.
 */
public class LibraryFragment extends BaseFragment implements AdapterView.OnItemClickListener,DoubleCacheIView<Libraries>,AdapterView.OnItemLongClickListener {

    public static final String TAG = LibraryFragment.class.getSimpleName();

    Presenter presenter;

    LibrarysAdapter librarysAdapter;

    ListView listView;

    FloatingActionButton floatingActionButton;

    LibraryInfoView libraryInfoView;

    ProgressReceiver receiver;

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
        MainActivity activity = (MainActivity) getActivity();
        presenter = new Presenter(this,activity.getQueue());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        libraryInfoView = (LibraryInfoView) view.findViewById(R.id.library_info_view);
        libraryInfoView.setTxLibraryNameNull();
        rquestData();
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
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterReceiver();
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

    public void registerReceiver(){
        if(receiver == null){
            receiver = new ProgressReceiver();
        }
        Intent intent = new Intent();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownLoadManager.DownLoadTask.action_download_porgress);
        intentFilter.addAction(DownLoadManager.DownLoadTask.action_store_complete);
        intentFilter.addAction(DownLoadManager.DownLoadTask.action_download_complete);
        getActivity().registerReceiver(receiver,intentFilter);
    }

    public void unRegisterReceiver(){
        if(receiver != null){
            getActivity().unregisterReceiver(receiver);
        }
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
        presenter.itemClick(parent,view,position,id);

        librarysAdapter.notifyDataSetChanged();//刷新界面
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       presenter.itemLongClick(parent,view,position,id);
            return true;
    }

    @Override
    public void onClick(View v) {

    }

    public ColorDialog getAskForDeleteDialog(String libtaryName, ColorDialog.OnPositiveListener pListener, ColorDialog.OnNegativeListener nListener){
        ColorDialog dialog = new ColorDialog(this.getActivity());
        dialog.setColor(getResources().getColor(R.color.seagreen));
        dialog.setTitle(getString(R.string.title_library_delete));
        dialog.setContentText(getString(R.string.msg_library_delete,libtaryName));
        dialog.setPositiveListener(R.string.delete,pListener);
        dialog.setNegativeListener(R.string.cancle,nListener);
        return dialog;
    }

    public ColorDialog getAskForDownLoadDialog(String libtaryName, ColorDialog.OnPositiveListener pListener, ColorDialog.OnNegativeListener nListener){
        ColorDialog dialog = new ColorDialog(this.getActivity());
        dialog.setColor(getResources().getColor(R.color.seagreen));
        dialog.setTitle(getString(R.string.title_library_download));
        dialog.setContentText(getString(R.string.msg_library_download,libtaryName));
        dialog.setPositiveListener(R.string.sure,pListener);
        dialog.setNegativeListener(R.string.cancle,nListener);
        return dialog;
    }

    public ColorDialog getDownLoadDialog(String libtaryName, ColorDialog.OnNegativeListener nListener, DialogInterface.OnDismissListener dismissListener){
        ColorDialog dialog = new ColorDialog(this.getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setColor(getResources().getColor(R.color.white));
        dialog.setTitle(getString(R.string.title_downloading_library,libtaryName));
        dialog.setTitleTextColor(getResources().getColor(R.color.seagreen));
        dialog.setNegativeListener(R.string.cancle,nListener);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    public AlertDialog getDownLoadDialog(final ProgressBn progressBn, String libraryName, DialogInterface.OnClickListener cancleListener){
        AlertDialog  pdialog = new AlertDialog.Builder(this.getActivity())
                .setTitle(getString(R.string.title_deleting_library,libraryName))
                .setView(progressBn)
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBn.incrementProgressBy(1);
                    }
                }).create();
        return pdialog;
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

        boolean hasSelect = false;
        for(Library library : data.getLibraries()){//
            if(library.isSelected()){
                libraryInfoView.setLibraryInfo(library);
                hasSelect = true;
                break;
            }
        }
        if(!hasSelect){
            libraryInfoView.setTxLibraryNameNull();
        }
    }

    @Override
    public void showNetData(Libraries data) {
        showLocalData(data);
    }

    @Override
    public void showMoreData(Libraries data) {

    }

    public void notifyDataSetChanged(){
        librarysAdapter.notifyDataSetChanged();
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

        List<Library> mLibraries;

        ColorDialog mDialog;

        ColorDialog downLoadDialog;

        ProgressBn progressBn;

        DownLoadManager downLoadManager;

        public Presenter(DoubleCacheIView<Libraries> iView, RequestQueue queue) {
            super(iView, queue,LoadType.CACHE);
            mLibraries = new ArrayList<>();
        }

        public void changeLibrary(Library oldLibr,Library newLibr){
            if(LibrarySerivce.changLibrary(oldLibr,newLibr)){
                if(oldLibr != null){
                    mLibraries.remove(oldLibr);
                    mLibraries.add(oldLibr);
                }
                mLibraries.remove(newLibr);
                mLibraries.add(newLibr);

                LibraryFragment fragment = (LibraryFragment) getIView();
                fragment.libraryInfoView.setLibraryInfo(newLibr);
                fragment.notifyDataSetChanged();
            }
        }

        @Override
        public void preLoadLocalData() {

        }

        public void itemClick(AdapterView<?> parent, View view, int position, long id){
            Library library = (Library) parent.getAdapter().getItem(position);
            if(library.hasWords()){
                if(library.isSelected()){
                    dropSelectedLibrary(library);
                }else{
                    Library oldLibr = LibrarySerivce.getSelectedLbrary();
                    changeLibrary(oldLibr,library);
                }

            }else{
                downLoadLibrary(library);
            }
        }

        public void dropSelectedLibrary(Library library){
            library.setSelected(false);
            library.update();
            LibraryFragment fragment = (LibraryFragment) getIView();
            fragment.libraryInfoView.setTxLibraryNameNull();
            fragment.notifyDataSetChanged();
        }

        public void intoLibraryDetails(Library library){
           IntentManager.intentToLibraryDetails(getContext());
        }

        public void downLoadLibrary(final Library library){
           final LibraryFragment fragment = (LibraryFragment) getIView();
            mDialog = fragment.getAskForDownLoadDialog(library.getIntrodu(),new ColorDialog.OnPositiveListener() {
               @Override
               public void onClick(ColorDialog dialog) {
                   dialog.dismiss();

                   progressBn = getInitPorgressBn();
                   downLoadDialog = fragment.getDownLoadDialog(library.getIntrodu(), new ColorDialog.OnNegativeListener() {

                       @Override
                       public void onClick(ColorDialog dialog) {
                           dialog.cancel();
                       }
                   }, new DialogInterface.OnDismissListener() {
                       @Override
                       public void onDismiss(DialogInterface dialog) {
                           downLoadManager.cancleDownLoad();
                   }
                   });

                   downLoadDialog.show();
                   downLoadDialog.setCcontentView(progressBn);
                   if(downLoadManager == null){
                       downLoadManager  = new DownLoadManager(getContext());
                   }
                   downLoadManager.startDownLoad(library);
               }
           }, new ColorDialog.OnNegativeListener() {
               @Override
               public void onClick(ColorDialog dialog) {
                   dialog.dismiss();
               }
           });
            mDialog.setAnimationEnable(true);
            mDialog.show();
        }

        public void itemLongClick(AdapterView<?> parent, View view, int position, long id){
            Library library = (Library) parent.getAdapter().getItem(position);
            if(library.getIsExist() == Library.IsExist.exist){
                onDeleteLibrary(library);
            }
        }

        public void onDeleteLibrary(final Library library){
            LibraryFragment fragment = (LibraryFragment) getIView();
             mDialog = fragment.getAskForDeleteDialog(library.getIntrodu(),new ColorDialog.OnPositiveListener() {
                @Override
                public void onClick(ColorDialog dialog) {
                    dialog.dismiss();
                    deleteLibrary(library);
                }
            }, new ColorDialog.OnNegativeListener() {
                @Override
                public void onClick(ColorDialog dialog) {
                    dialog.dismiss();
                }
            });
            mDialog.setAnimationEnable(true);
            mDialog.show();
        }

        public void deleteLibrary(final Library library){

            final ColorDialog dDialog = new ColorDialog(getContext());
            dDialog.setTitle("删除词库");
            dDialog.setContentText("正在删除 "+ library.getIntrodu()+" ...");
            dDialog.setCanceledOnTouchOutside(false);
            dDialog.setCancelable(false);
            dDialog.show();

            LibrarySerivce.deleteTableAsync(library, new Transaction.Success() {
                @Override
                public void onSuccess(Transaction transaction) {
                    dDialog.dismiss();
                    LibraryFragment fragment = (LibraryFragment) getIView();
                    fragment.notifyDataSetChanged();
                    checkLibraryInfo(mLibraries);

                    PromptDialog pialog = new PromptDialog(getContext());
                    pialog.setContentText("删除成功");
                    pialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS);
                    pialog.setCanceledOnTouchOutside(true);
                    pialog.setPositiveListener(R.string.sure, new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    pialog.show();
                }
            }, new Transaction.Error() {
                @Override
                public void onError(Transaction transaction, Throwable error) {
                    dDialog.dismiss();
                    PromptDialog pialog = new PromptDialog(getContext());
                    pialog.setContentText("删除失败");
                    pialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
                    pialog.setPositiveListener(R.string.cancle, new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                          dialog.dismiss();
                        }
                    });
                    pialog.show();
                }
            });
        }

        public void checkLibraryInfo(List<Library> datas){
            LibraryFragment fragment = (LibraryFragment) getIView();
            boolean hasSelect = false;
            //
            for(Library library : datas)
                if (library.isSelected()) {

                    fragment.libraryInfoView.setLibraryInfo(library);
                    hasSelect = true;
                    break;
                }
            if(!hasSelect){
                fragment.libraryInfoView.setTxLibraryNameNull();
            }
        }
         /*----------------数据加载方法-----*/
        @Override
        public void loadLocalData() {
            Libraries libraries = LibrarySerivce.getLibrariesLocal();
            postLocalData(libraries);
        }

        @Override
        public void postLocalData(Libraries data) {
            mLibraries.clear();
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
        public void dataDealFromNet(Libraries data) {
            if(data == null || data.getLibraries() == null){return;}

            List<Library> librFromNet = data.getLibraries();
            librFromNet.removeAll(mLibraries);

            mLibraries.addAll(data.getLibraries());

            data.setLibraries(mLibraries);

            postRequestDataFromNet(data,null);
        }

        public ProgressBn getProgressBn() {
            return progressBn;
        }

        public void setProgressBn(ProgressBn progressBn) {
            this.progressBn = progressBn;
        }

        public ProgressBn getInitPorgressBn(){
                progressBn = new ProgressBn(getContext());
                progressBn.setProgressText("下载中...");
                progressBn.setOnCompletedListener(completedListener);
                return progressBn;
        }

        ProgressBn.OnCompletedListener completedListener = new ProgressBn.OnCompletedListener() {
            @Override
            public void onComplete(int porgress) {
                progressBn.setProgressText(R.string.complete);
            }
        };

        public void updateProgress(int porgress){
            progressBn.setProgress(porgress);
        }

        public void cancleDialog() {
            if(downLoadDialog != null){
                downLoadDialog.dismiss();
            }
        }

    }

    public class ProgressReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownLoadManager.DownLoadTask.action_download_porgress)){
                presenter.updateProgress(intent.getIntExtra(DownLoadManager.DownLoadTask.PAR_PROGRESS,0));
            }else if(intent.getAction().equals(DownLoadManager.DownLoadTask.action_download_complete)){
                ProgressBn progressBn = presenter.getProgressBn();
                progressBn.setProgressText(R.string.complete);
            }else if(intent.getAction().equals(DownLoadManager.DownLoadTask.action_store_complete)){
                final Library library = intent.getParcelableExtra(DownLoadManager.DownLoadTask.PAR_LIBRARY);
                final ProgressBn progressBn = presenter.getProgressBn();
                progressBn.setProgressText(R.string.open);
                progressBn.setTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.cancleDialog();
                        Library oldLirb = LibrarySerivce.getSelectedLbrary();
                        presenter.changeLibrary(oldLirb,library);

                        presenter.intoLibraryDetails(library);
                    }
                });
            }
        }
    }
}
