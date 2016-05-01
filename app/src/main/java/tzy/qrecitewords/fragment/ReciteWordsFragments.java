package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.utils.IntentManager;
import tzy.qrecitewords.widget.LibraryInfoView;

/**
 * Created by tzy on 2016/1/1.
 */
public class ReciteWordsFragments extends BaseFragment  {

    public static final String TAG = ReciteWordsFragments.class.getSimpleName();

    LibraryInfoView libraryInfoView;

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
        txStart.setOnClickListener(this);
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
}
