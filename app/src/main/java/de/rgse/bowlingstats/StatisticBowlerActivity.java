package de.rgse.bowlingstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.BowlerStatistic;
import de.rgse.bowlingstats.model.BowlerStatisticWrapper;
import de.rgse.bowlingstats.tasks.LoadBowlerStatisticsTask;
import de.rgse.bowlingstats.tasks.LoadBowlersTask;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class StatisticBowlerActivity extends AppCompatActivity {

    private Spinner spinner;
    private String name;
    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_bowler);
        lineChartView = findViewById(R.id.chart);

        loadBowlers();
        initSpinner();
    }

    private void updateChart(BowlerStatisticWrapper bowlerStatisticWrapper) {
        List<PointValue> averageValues = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();

        int count = 0;
        int value = 0;

        for (int i = 0; i < bowlerStatisticWrapper.getStatistic().size(); i++) {
            BowlerStatistic stat = bowlerStatisticWrapper.getStatistic().get(i);
            count += stat.getCount();
            value += (stat.getAverage() * stat.getCount());

            axisValues.add(new AxisValue(i).setLabel(DateFormat.getDateInstance(DateFormat.SHORT).format(stat.getDateTime())));
            float pointValue = count == 0 ? 0 : value / count;
            averageValues.add(new PointValue(i, pointValue).setLabel(Float.toString(pointValue)));
        }

        Axis yAxis = new Axis();
        Axis axis = new Axis(axisValues);
        Line line = new Line(averageValues);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        data.setAxisXBottom(axis);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(this, bowlers -> {
            List<String> bowlerNames = bowlers.stream().map(Bowler::getName).collect(Collectors.toList());
            updateSpinner(bowlerNames);
        });
    }

    private void updateSpinner(List<String> bowlerNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bowlerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void loadStatistics(String bowlerName) {
        LoadBowlerStatisticsTask.loadBowlerStatistics(bowlerName, getApplicationContext(), this::updateChart);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.bowlers_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = (String) spinner.getAdapter().getItem(position);
                loadStatistics(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                name = null;
            }
        });
    }
}
