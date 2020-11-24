package com.common.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.common.app.R;
import com.common.app.common.base.BaseActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    private GraphView graphView;
    LineGraphSeries<DataPoint> series;

    private int i = 0;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_activity_main);
        graphView = findViewById(R.id.graphView);

        graphView.setTitle("当前血氧");
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(false);

        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(160);
        graphView.getViewport().setYAxisBoundsManual(true);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(30);


        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);//右上角对每条线注释
        graphView.getLegendRenderer().setTextColor(Color.WHITE);//标注字的颜色

        series = new LineGraphSeries<>(new DataPoint[]{new DataPoint(i, 0)});
        series.setColor(ContextCompat.getColor(this, R.color.color_ff6800));
        series.setTitle("血氧");
        graphView.addSeries(series);
        loadTextData();
    }

    /**
     * 加载测试数据
     */
    private void loadTextData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            strings.add(String.valueOf(i));
        }
        Observable<String> data = Observable.fromIterable(strings);
        Observable<Long> time = Observable.interval(1, TimeUnit.SECONDS);//数据延迟发送
        Observable
                .zip(data, time, new BiFunction<String, Long, String>() {
                    @Override
                    public String apply(String s, Long aLong) throws Exception {
                        return s;
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        int p = integer;
                        Logger.d("p:" + p);
                        i++;
                        if (i >= 30) {
                            series.appendData(new DataPoint(i, p), true, 100);
                        } else {
                            series.appendData(new DataPoint(i, p), false, 100);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
