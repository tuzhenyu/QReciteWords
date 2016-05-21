package tzy.qrecitewords.fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.R;
import tzy.qrecitewords.dataUtils.serivce.LearnAlarmService;
import tzy.qrecitewords.dataUtils.serivce.SentenceService;
import tzy.qrecitewords.javabean.LearnAlarmSetting;
import tzy.qrecitewords.serivce.AlarmService;
import tzy.qrecitewords.utils.IntentManager;

/**
 * Created by tzy on 2016/1/1.
 */
public class SettingFragment extends BaseFragment {

    TextView txMissionAdjust;

    TextView txLearnAlarm;

    TextView txclearSample;

    TextView txAbout;

    TextView txCheckUpdate;

    //Switch pushNotifySwitch;

    Switch learnAlarmSwitch;

    TimePickerDialog timePickerDialog;

    Presenter presenter;


    public static final String TAG = SettingFragment.class.getSimpleName();

    public SettingFragment() {
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
        initView(view);
        presenter = new Presenter(this);
    }

    void initView(View view){
         txMissionAdjust = (TextView) view.findViewById(R.id.tx_mssion_set);
         txMissionAdjust.setOnClickListener(this);

         txLearnAlarm = (TextView) view.findViewById(R.id.tx_learn_admire);
         txLearnAlarm.setOnClickListener(this);

         txclearSample = (TextView) view.findViewById(R.id.tx_clear_sen);
         txclearSample.setOnClickListener(this);

         txAbout = (TextView) view.findViewById(R.id.tx_about);
         txAbout.setOnClickListener(this);

         txCheckUpdate = (TextView) view.findViewById(R.id.tx_update);
         txCheckUpdate.setOnClickListener(this);

       /*  pushNotifySwitch = (Switch) view.findViewById(R.id.push_reminder_switch);
         pushNotifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             }
         });*/

         learnAlarmSwitch = (Switch) view.findViewById(R.id.learn_reminder_switch);
         learnAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.learnAlarmSet(isChecked);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    public void showAlarmSetting(LearnAlarmSetting setting){
         learnAlarmSwitch.setChecked(setting.isOpen());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadSetting();
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

    public TimePickerDialog createAlarmSettingDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener,int hour,int minute){
        if(timePickerDialog == null){
            timePickerDialog =new TimePickerDialog(this.getActivity(),onTimeSetListener,hour,minute,true);
        }else{
            timePickerDialog.updateTime(hour,minute);
        }
        timePickerDialog.setTitle("请设置提醒时间");
        timePickerDialog.show();
        return timePickerDialog;
    }

    public ColorDialog createAboutDialog(String txt){
        ColorDialog dialog = new ColorDialog(this.getActivity());
        dialog.setTitle("关于");
        dialog.setContentCenter(true);
        dialog.setColor(getResources().getColor(R.color.seagreen));
        dialog.setContentText(txt);
        dialog.setPositiveListener(R.string.sure, new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return  dialog;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingFragment newInstance(int sectionNumber) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));*/
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tx_mssion_set:
                presenter.adjustMission();
            break;
            case R.id.tx_learn_admire:
                presenter.learnAlarmSet();
            break;
            case R.id.tx_clear_sen:
                presenter.clearSentenceCache();
            break;
            case R.id.tx_about:
                presenter.about();
            break;
            case R.id.tx_update:
                presenter.checkoutUpdate();
                break;
       }
    }

    public static class Presenter{
          WeakReference<SettingFragment> reference;

          public Presenter(SettingFragment fragment) {
              reference = new WeakReference<SettingFragment>(fragment);
          }

          public void loadSetting(){
              LearnAlarmSetting setting = LearnAlarmService.getLearnAlarmSetting(reference.get().getActivity());
              reference.get().showAlarmSetting(setting);
          }

          public void adjustMission(){
              IntentManager.intentMIssionSettingActivity(reference.get().getActivity());
          }

         public void learnAlarmSet(){
             LearnAlarmSetting setting = LearnAlarmService.getLearnAlarmSetting(reference.get().getActivity());

             TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                 @Override
                 public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                     LearnAlarmService.setLearnAlarm(reference.get().getActivity(),hourOfDay,minute);
                 }
             };
             TimePickerDialog dialog = reference.get().createAlarmSettingDialog(listener,setting.getHour(),setting.getMinute());
         }

        public void learnAlarmSet(boolean isopen){
            LearnAlarmService.setLearnAlarmSwitch(reference.get().getActivity(),isopen);
        }

         public void about(){
             StringBuilder sb  =new StringBuilder("快背单词\n");
             Context context = reference.get().getActivity();

             try {
                 sb.append("版本" + context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS)
                         .versionName);
                 sb.append("\nby:tuzhenyu");
             } catch (PackageManager.NameNotFoundException e) {
                 e.printStackTrace();
             }
             reference.get().createAboutDialog(sb.toString());
         }

         public void checkoutUpdate(){
            reference.get().showPromoteDialog("版本更新","已是最新版",PromptDialog.DIALOG_TYPE_INFO,null);
         }

        public void clearSentenceCache(){
            SentenceService.clearSentence(new Transaction.Success() {
                @Override
                public void onSuccess(Transaction transaction) {
                    SettingFragment fragment = reference.get();
                    if(fragment == null){return;}
                    fragment.showPromoteDialog("","清除例句缓存成功",PromptDialog.DIALOG_TYPE_SUCCESS,null);
                }
            }, new Transaction.Error() {
                @Override
                public void onError(Transaction transaction, Throwable error) {
                    SettingFragment fragment = reference.get();
                    if(fragment == null){return;}
                    fragment.showPromoteDialog("","清除例句缓存成功",PromptDialog.DIALOG_TYPE_WRONG,null);
                }
            });

        }
    }
}
