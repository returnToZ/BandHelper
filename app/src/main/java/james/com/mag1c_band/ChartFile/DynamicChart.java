package james.com.mag1c_band.ChartFile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Timer;
import java.util.TimerTask;

import james.com.mag1c_band.R;

/**
 * @项目名称：模拟生命特征值图
 * @类名称：AnalogPulse
 * @作者：Qiuzhping
 * @时间：2014-1-17上午11:46:51
 * @作用 ：模拟生命特征值图，动态显示分钟脉搏走向
 *     每隔1000ms产生50组数据，并填充到表格中，主要使用的到是Handler+Task，因为我是用Activity显示这个表格
 *     每次产生的数据都需要快速的填充到主线程UI中，所以我就用Handler，这里的数据我都是采用随机数表示，这个demo可以作为开发
 *     医疗设备显示生命症状的表格信息参考，实际项目中如果需要用的每隔XX时间产生XX数据，最好是用Web提供数据，这样可以实现多个客户端 共享数据
 * @说明：环境是android4.0
 */
public class DynamicChart extends Activity {
    private Button startBtn = null;
    private Button stopBtn = null;
    private Timer timer = new Timer();// 定时器
    private TimerTask task;// 任务
    private Handler handler;// 主要用于更新表格视图
    private String title = "线性统计图示例";
    private XYSeries series;// XY数据点
    private XYMultipleSeriesDataset mDataset;// XY轴数据集
    private GraphicalView mViewChart;// 用于显示现行统计图
    private XYMultipleSeriesRenderer mXYRenderer;// 线性统计图主描绘器
    private Context context;// 用于获取上下文对象
    private LinearLayout mLayout;
    private int X = 5;// X数据集大小
    private int Y = 50;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analog_pulse);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new StartBtn());

        stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new StopBtn());

        context = getApplicationContext();// 获取上下文对象
        mLayout = (LinearLayout) findViewById(R.id.chart);// 这里获得xy_chart的布局，下面会把图表画在这个布局里面
        series = new XYSeries(title);// 这个类用来放置曲线上的所有点，是一个点的集合，根据这些点画出曲线

        mDataset = new XYMultipleSeriesDataset(); // 创建一个数据集的实例，这个数据集将被用来创建图表
        mDataset.addSeries(series);// 将点集添加到这个数据集中

        int color = Color.RED;// 设置颜色
        PointStyle style = PointStyle.CIRCLE;// 设置外观周期性显示
        mXYRenderer = buildRenderer(color, style, true);
        mXYRenderer.setShowGrid(true);// 显示表格
        mXYRenderer.setGridColor(Color.GREEN);// 据说绿色代表健康色调，不过我比较喜欢灰色
        mXYRenderer.setXLabels(50);
        mXYRenderer.setYLabels(50);
        mXYRenderer.setYLabelsAlign(Align.RIGHT);// 右对齐
        // mXYRenderer.setPointSize((float) 2);
        mXYRenderer.setShowLegend(false);// 不显示图例
        mXYRenderer.setZoomEnabled(false);
        mXYRenderer.setPanEnabled(true, false);
        mXYRenderer.setClickEnabled(false);
        setChartSettings(mXYRenderer, title, "时间", "数量", 0, X, 0, Y,
                Color.WHITE, Color.WHITE);// 这个是采用官方APIdemo提供给的方法
        // 设置好图表的样式

        mViewChart = ChartFactory.getLineChartView(context, mDataset,
                mXYRenderer);// 通过ChartFactory生成图表

        mLayout.addView(mViewChart, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));// 将图表添加到布局中去

    }

    @Override
    public void onDestroy() {
        if (timer != null) {// 当结束程序时关掉Timer
            timer.cancel();
            Log.i("qiuzhping", "onDestroy timer cancel");
            super.onDestroy();
        }
    }

    protected XYMultipleSeriesRenderer buildRenderer(int color,
                                                     PointStyle style, boolean fill) {// 设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(color);
        r.setPointStyle(style);
        r.setFillPoints(fill);
        r.setLineWidth(3);
        renderer.addSeriesRenderer(r);

        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer,
                                    String title, String xTitle, String yTitle, double xMin,
                                    double xMax, double yMin, double yMax, int axesColor,
                                    int labelsColor) {// 设置主描绘器的各项属性，详情可阅读官方API文档
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    /**
     * @方法名: updateChart
     * @作者：Qiuzhping
     * @作用: 主要工作是每隔1000ms刷新整个统计图 产生50组数据，完全填充表格
     */
    private void updateChart() {// 主要工作是每隔1000ms刷新整个统计图
        Log.i("qiuzhping", "updateChart ok");
        mDataset.removeSeries(series);// 移除数据集中旧的点集
        //series.remove(1);
        series.clear();// 点集先清空，为了做成新的点集而准备
        for (int k = 0; k < X; k++) {// 实际项目中这些数据最好是由线程搞定，可以从WebService中获取
            int y = (int)(Math.random()*Y);
            int temp = 0;
            series.add(k, y);
            //series.add(k + 1,temp);
        }
        mDataset.addSeries(series);// 在数据集中添加新的点集
        mViewChart.invalidate();// 视图更新，没有这一步，曲线不会呈现动态
    }

    private final class StartBtn implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            Log.i("qiuzhping", "startBtn onClick");
            handler = new Handler() {// 简单的通过Handler+Task形成一个定时任务，从而完成定时更新图表的功能
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Log.i("qiuzhping", "Handler handleMessage");
                        updateChart(); // 刷新图表,handler的作用是将此方法并入主线程，在非主线程是不能修改UI的
                        super.handleMessage(msg);
                    }
                }
            };

            task = new TimerTask() {// 定时器
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;// 设置标志
                    handler.sendMessage(message);
                    Log.i("qiuzhping", "TimerTask run");
                }
            };

            timer.schedule(task, 1000, 1000);// 运行时间和间隔都是1000ms
        }

    }

    private final class StopBtn implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            Log.i("qiuzhping", "stopBtn onClick");
            DynamicChart.this.finish();
        }
    }
}

