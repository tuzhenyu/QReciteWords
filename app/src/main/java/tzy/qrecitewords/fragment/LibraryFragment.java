package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.adapter.LibrarysAdapter;
import tzy.qrecitewords.javabean.Library;

/**
 * Created by tzy on 2016/1/1.
 */
public class LibraryFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = LibraryFragment.class.getSimpleName();

    LibrarysAdapter librarysAdapter;

    ListView listView;

    FloatingActionButton floatingActionButton;

    public LibraryFragment() {
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

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        listView = (ListView) view.findViewById(R.id.listView);

        String[] ss = new String[]{"四级", "六级", "雅思", "托福", "六年级英语", "高中英语", "初中英语", "小学英语"};

        List<String> stringList = Arrays.asList(ss);

        List<Library> libraries = new LinkedList<>();

        for (String libraryName : stringList) {
            libraries.add(new Library(libraryName));
        }

        librarysAdapter = new LibrarysAdapter(libraries, this.getActivity());

        listView.setAdapter(librarysAdapter);
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
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
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
}
