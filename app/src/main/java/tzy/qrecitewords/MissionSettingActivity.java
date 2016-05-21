package tzy.qrecitewords;

import tzy.qrecitewords.app.BaseActivity;
import tzy.qrecitewords.dataUtils.serivce.MissionSettingService;
import tzy.qrecitewords.javabean.MissionSetting;
import tzy.qrecitewords.widget.rsv.RangeSliderView;

/**
 * Created by tzy on 2016/5/17.
 */
public class MissionSettingActivity extends BaseActivity {

    MissionSetting missionSetting;

    private RangeSliderView Slider;

    @Override
    public void preView() {

    }

    @Override
    public void initView() {
        Slider = (RangeSliderView) findViewById(
                R.id.rsv_large);

        Slider.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                int count = MissionSetting.getCountFromStatic(index);
                setMissionSetting(count);
            }
        });
    }

    @Override
    public void postInitView() {
         MissionSetting missionSetting = MissionSettingService.getMissionSetting();
         int count = missionSetting.getDayCount();
        int[] missSetingList = MissionSetting.DAYCOUNTs;
         for(int i = 0;i<missSetingList.length;++i){
             if(count == missSetingList[i]){
                 Slider.setInitialIndex(i);
                 break;
             }
         }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_missionsetting;
    }

    public void setMissionSetting(int count){
        MissionSettingService.setMissionDayCount(count);
    }

}
