package tzy.qrecitewords;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import tzy.qrecitewords.app.BaseActivity;
import tzy.qrecitewords.dataUtils.serivce.MissionService;
import tzy.qrecitewords.javabean.MissionOfDay;

/**
 * Created by tzy on 2016/5/17.
 */
public class AnalyzeActivity extends BaseActivity {

    private ColumnChartView chart;
    private ColumnChartData data;
    Axis axisY;
    @Override
    public void preView() {

    }

    @Override
    public void initView() {
        chart = (ColumnChartView) findViewById(R.id.chart);
        axisY = new Axis().setHasLines(true);
        axisY.setName("单词数量");
    }

    @Override
    public void postInitView() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_analyze_layout;
    }

    public void loadData(){
        Calendar calendar = Calendar.getInstance();
        Date lastDate =new Date(calendar.getTimeInMillis());
        calendar.roll(Calendar.DAY_OF_YEAR,-5);
        Date firstDate = new Date(calendar.getTimeInMillis());
        MissionService.getMissionPfRangeAsync(firstDate, lastDate, new QueryTransaction.QueryResultListCallback<MissionOfDay>() {
            @Override
            public void onListQueryResult(QueryTransaction queryTransaction, @Nullable List<MissionOfDay> list) {
                showDate(list);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showDate(List<MissionOfDay> list){

        data = generateColumnData(list);
        data.setAxisYLeft(axisY);
        chart.setColumnChartData(data);
    }

    List<AxisValue> axisValues ;
    private ColumnChartData generateColumnData(List<MissionOfDay> list) {

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>(list.size());
        List<SubcolumnValue> values;

        for(MissionOfDay mission : list){
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(mission.getCountOfLearned(), ChartUtils.COLOR_GREEN));
            columns.add(new Column(values).setHasLabels(true));
        }

        while(columns.size() < 5){
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(0,ChartUtils.COLOR_GREEN));
            columns.add(0,new Column(values).setHasLabels(true));
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);

        axisValues = getAxisValues(list);

        columnChartData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        return columnChartData;
    }

    private LineChartData generateLineData(List<MissionOfDay> list) {

        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        int j = 1;
        for(MissionOfDay mission : list){
            values.add(new PointValue(j, j + 2));
            ++j;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_BLUE);
        line.setCubic(true);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);

        LineChartData lineChartData = new LineChartData(lines);

        return null;
    }

    public List<AxisValue> getAxisValues(List<MissionOfDay> list){
        List<AxisValue> axisValues = new ArrayList<AxisValue>(5);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;

        try {
            if(list != null && list.size() > 0){
                calendar.setTimeInMillis(format.parse(list.get(list.size() - 1).getDate()).getTime());
            }

            for(int i = 0;i < 5;++i){
                axisValues.add(new AxisValue(4 - i).setLabel(calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH)));
                calendar.roll(Calendar.DAY_OF_YEAR,-1);
            }
            return axisValues;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
