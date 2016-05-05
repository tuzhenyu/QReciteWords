package tzy.qrecitewords;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tzy.qrecitewords.fragment.BaseFragment;
import tzy.qrecitewords.fragment.LearnMoreFragment;
import tzy.qrecitewords.fragment.LibraryFragment;
import tzy.qrecitewords.fragment.PersonFragment;
import tzy.qrecitewords.fragment.ReciteWordsFragments;
import tzy.qrecitewords.fragment.SettingFragment;
//import tzy.tapbarmenu.TapBarMenu;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    FragmentPagerAdapter pagerAdapter;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    //private Toolbar mToolbar;// toolbar

   // private DrawerLayout drawerLayout;

    //private TapBarMenu tapBarMenu;

    ImageView imageViewPersonal,imageViewSetting,imageViewLibrary,imageViewMore;


    //private ActionBarDrawerToggle mActionBarDrawerToggle;

    String[] titles = new String[]{"背单词","词库","多学点","个人中心","设置中心"};

    List<String> mTitleList = new ArrayList();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
   // private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    List<BaseFragment> baseFragments =new  ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mTabLayout = (TabLayout) findViewById(R.id.tl);

        baseFragments.add(ReciteWordsFragments.newInstance(0));
        baseFragments.add(LibraryFragment.newInstance(1));
        baseFragments.add(SettingFragment.newInstance(2));

        mTabLayout.addTab(mTabLayout.newTab().setText("背单词"));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText("词库"));
        mTabLayout.addTab(mTabLayout.newTab().setText("设置中心"));

        mTitleList.add("背单词");
        mTitleList.add("词库");
        mTitleList.add("设置中心");

        pagerAdapter =new  WordViewPagerAdapter(getSupportFragmentManager(),baseFragments);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setTabsFromPagerAdapter(pagerAdapter);//给Tabs设置适配器
        // tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenu);
       /* tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });*/
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

      //  mNavigationDrawerFragment = (NavigationDrawerFragment)
        //        getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

       // mNavigationDrawerFragment.setUp(
         //       R.id.navigation_drawer,
          //      drawerLayout);

       // mTitle = getTitle();

       // initToolBar();

       // onNavigationDrawerItemSelected(0);
    }

    /**
     * 为页面加载初始状态的fragment
     */
    public void initFragment(Bundle savedInstanceState) {

    }

    /**
     * 初始化ToolBar
     */
   /* private void initToolBar()
    {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(titles[0]);
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);


    }*/

   /* @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BaseFragment fragment = null;
        String tag = null;
        switch(position) {
            case 0:
                fragment = (BaseFragment) fm.findFragmentByTag(ReciteWordsFragments.TAG);
        if(fragment == null)
            fragment = ReciteWordsFragments.newInstance(0);
                tag = ReciteWordsFragments.TAG;
                break;
            case 1:
                fragment = (BaseFragment) fm.findFragmentByTag(LibraryFragment.TAG);
                if(fragment == null)
                    fragment = LibraryFragment.newInstance(1);
                tag = LibraryFragment.TAG;
                break;
            case 2:
                fragment = (BaseFragment) fm.findFragmentByTag(LearnMoreFragment.TAG);
                if(fragment == null)
                    fragment = LearnMoreFragment.newInstance(2);
                tag = LearnMoreFragment.TAG;
                break;
            case 3:
                fragment = (BaseFragment) fm.findFragmentByTag(PersonFragment.TAG);
                if(fragment == null)
                    fragment = PersonFragment.newInstance(3);
                tag = PersonFragment.TAG;
                break;
            case 4:
                fragment = (BaseFragment) fm.findFragmentByTag(SettingFragment.TAG);
                if(fragment == null)
                    fragment = SettingFragment.newInstance(4);
                tag = SettingFragment.TAG;
                break;
        }

       // ft.replace(R.id.container, fragment, tag).commit();
    }*/

    /*public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_words);
                break;
            case 1:
                mTitle = getString(R.string.title_library);
                break;
            case 2:
                mTitle = getString(R.string.title_learn);
                break;
            case 3:
                mTitle = getString(R.string.title_personal);
                break;
            case 4:
                mTitle = getString(R.string.title_setting);
                break;
        }

        mToolbar.setTitle(mTitle);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        /*switch(v.getId()){
            case R.id.item1:
                Log.i("TAG", "Item 1 selected");
                break;
            case R.id.item2:
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Log.i("TAG", "Item 4 selected");
                break;
        }*/
       // tapBarMenu.close();
    }

    class WordViewPagerAdapter extends FragmentPagerAdapter{

        List<BaseFragment> fragments ;

        public WordViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<BaseFragment>();
        }

        public WordViewPagerAdapter(FragmentManager fm,List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mTitleList.get(position);
        }
    }
}
