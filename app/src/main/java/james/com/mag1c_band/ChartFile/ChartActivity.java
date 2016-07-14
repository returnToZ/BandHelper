package james.com.mag1c_band.ChartFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import james.com.mag1c_band.R;
import james.com.mag1c_band.UI.ChatActivity;
import james.com.mag1c_band.UI.ExitWindow;

public class ChartActivity extends Activity {
    private Button chart;
    private String title = "线性统计图示例";
    private XYSeries series;// XY数据点
    private XYMultipleSeriesDataset mDataset;// XY轴数据集
    private GraphicalView mViewChart;// 用于显示现行统计图
    private XYMultipleSeriesRenderer mXYRenderer;// 线性统计图主描绘器
    private Context context;// 用于获取上下文对象
    private LinearLayout mLayout;
    private int X = 5;// X数据集大小
    private int Y = 5;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        chart = (Button) findViewById(R.id.chart);
        startGraph();
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), DynamicChart.class);
                //startActivity(intent);
            }
        });
    }

    /*
以下为设置图表的逻辑
 */
    public void startGraph() {
        context = getApplicationContext();// 获取上下文对象
        mLayout = (LinearLayout) findViewById(R.id.chart_space);// 这里获得xy_chart的布局，下面会把图表画在这个布局里面
        series = new XYSeries(title);// 这个类用来放置曲线上的所有点，是一个点的集合，根据这些点画出曲线

        mDataset = new XYMultipleSeriesDataset(); // 创建一个数据集的实例，这个数据集将被用来创建图表
        mDataset.addSeries(series);// 将点集添加到这个数据集中

        int color = Color.RED;// 设置颜色
        PointStyle style = PointStyle.CIRCLE;// 设置外观周期性显示
        mXYRenderer = buildRenderer(color, style, true);
        mXYRenderer.setShowGrid(true);// 显示表格
        mXYRenderer.setGridColor(Color.GREEN);// 据说绿色代表健康色调，不过我比较喜欢灰色
        mXYRenderer.setXLabels(5);//设置X轴刻度个数
        mXYRenderer.setYLabels(5);//设置Y轴刻度个数
        mXYRenderer.setExternalZoomEnabled(true);//设置是否可以缩放
        mXYRenderer.setYLabelsAlign(Paint.Align.RIGHT);// 右对齐
        mXYRenderer.setAxisTitleTextSize(30f); // 坐标轴标题字体大小： 16
        mXYRenderer.setChartTitleTextSize(30f); // 图表标题字体大小： 20
        mXYRenderer.setLabelsTextSize(30f); // 轴标签字体大小： 15
        mXYRenderer.setLegendTextSize(30f); // 图例字体大小：
        mXYRenderer.setShowLegend(false);// 不显示图例
        mXYRenderer.setZoomEnabled(false);
        mXYRenderer.setPanEnabled(true, false);
        mXYRenderer.setClickEnabled(false);
        setChartSettings(mXYRenderer, title, "时间", "数量", 1, 5, 50, 170,//xmin,xmax,ymin,ymax
                Color.WHITE, Color.WHITE);// 这个是采用官方APIdemo提供给的方法
        // 设置好图表的样式

        mViewChart = ChartFactory.getLineChartView(context, mDataset,
                mXYRenderer);// 通过ChartFactory生成图表

        mLayout.addView(mViewChart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));// 将图表添加到布局中去
    }

    protected XYMultipleSeriesRenderer buildRenderer(int color,
                                                     PointStyle style, boolean fill)
    {// 设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
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
                                    int labelsColor)
    {// 设置主描绘器的各项属性，详情可阅读官方API文档
        renderer.setChartTitle(title);//图的标题
        renderer.setXTitle(xTitle);//图的x轴标题
        renderer.setYTitle(yTitle);//图的y轴标题
        renderer.setXAxisMin(xMin);//x轴最小值
        renderer.setXAxisMax(xMax);//x轴最大值
        renderer.setYAxisMin(yMin);//y轴最小值
        renderer.setYAxisMax(yMax);//y轴最大值
        renderer.setAxesColor(axesColor);//轴的颜色
        renderer.setLabelsColor(labelsColor);//label的颜色
    }
}
