package tzy.qrecitewords.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/1/7.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == mRootView){
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void showPromoteDialog(String title,String msg, int type,PromptDialog.OnPositiveListener listener){
        PromptDialog dialog = new PromptDialog(getActivity());
        dialog.setAnimationEnable(true);
        if(TextUtils.isEmpty(title)){
            dialog.setTitleShow(false);
        }else{
            dialog.setTitleShow(true);
            dialog.setTitleText(title);
        }
        dialog.setDialogType(type);
        dialog.setContentText(msg);
        if(listener == null){
            listener = new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            };
        }
        dialog.setPositiveListener(R.string.sure,listener);
        dialog.show();

    }

    public void showPromoteDialog(int titleSId,int id, int type,PromptDialog.OnPositiveListener listener){
        showPromoteDialog(getString(titleSId),getString(id),type,listener);
    }
}
