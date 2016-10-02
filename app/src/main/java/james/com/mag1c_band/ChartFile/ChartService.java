package james.com.mag1c_band.ChartFile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class ChartService {

    private GraphicalView mGraphicalView;
    private XYMultipleSeriesDataset multipleSeriesDataset;// 数据集容器
    private XYMultipleSeriesRenderer multipleSeriesRenderer;// 渲染器容器
    private XYSeries mSeries;// 单条曲线数据集
    private XYSeriesRenderer mRenderer;// 单条曲线渲染器
    private Context context;
    public static int Xmin = 0;
    public static int Xmax = 2000;// TODO: 2016/10/2

    public ChartService(Context context) {
        this.context = context;
    }

    /**
     * 获取图表
     *
     * @return
     */
    public GraphicalView getGraphicalView() {
        mGraphicalView = ChartFactory.getCubeLineChartView(context,
                multipleSeriesDataset, multipleSeriesRenderer, 0.1f);
        return mGraphicalView;
    }

    /**
     * 获取数据集，及xy坐标的集合
     *
     * @param curveTitle
     */
    public void setXYMultipleSeriesDataset(String curveTitle) {
        multipleSeriesDataset = new XYMultipleSeriesDataset();
        mSeries = new XYSeries(curveTitle);
        multipleSeriesDataset.addSeries(mSeries);
    }

    /**
     * 获取渲染器
     *
     * @param maxX
     *            x轴最大值
     * @param maxY
     *            y轴最大值
     * @param chartTitle
     *            曲线的标题
     * @param xTitle
     *            x轴标题
     * @param yTitle
     *            y轴标题
     * @param axeColor
     *            坐标轴颜色
     * @param labelColor
     *            标题颜色
     * @param curveColor
     *            曲线颜色
     * @param gridColor
     *            网格颜色
     */
    public void setXYMultipleSeriesRenderer(double maxX, double maxY,
                                            String chartTitle, String xTitle, String yTitle, int axeColor,
                                            int labelColor, int curveColor, int gridColor) {
        multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        if (chartTitle != null) {
            multipleSeriesRenderer.setChartTitle(chartTitle);
        }
        multipleSeriesRenderer.setPanEnabled(true,false);//x轴可以平移而y轴不可以
        multipleSeriesRenderer.setXTitle(xTitle);
        multipleSeriesRenderer.setYTitle(yTitle);
        multipleSeriesRenderer.setRange(new double[]{0, maxX, 0, maxY});//xy轴的范围
        multipleSeriesRenderer.setLabelsColor(labelColor);
        multipleSeriesRenderer.setXAxisMin(Xmin);
        multipleSeriesRenderer.setXAxisMax(Xmax);
        multipleSeriesRenderer.setXLabels(5);
        multipleSeriesRenderer.setYLabels(10);
        multipleSeriesRenderer.setXLabelsColor(Color.BLACK);
        multipleSeriesRenderer.setYLabelsColor(0,Color.BLACK);
        multipleSeriesRenderer.setXLabelsAlign(Align.RIGHT);
        multipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
        multipleSeriesRenderer.setAxisTitleTextSize(30);
        multipleSeriesRenderer.setChartTitleTextSize(30);
        multipleSeriesRenderer.setLabelsTextSize(20);
        multipleSeriesRenderer.setLegendTextSize(20);
        multipleSeriesRenderer.setShowLegend(false);//不显示图例
        //multipleSeriesRenderer.setPointSize(10f);//曲线描点尺寸
        multipleSeriesRenderer.setFitLegend(true);
        multipleSeriesRenderer.setGridLineWidth(5f);//边框线条宽度
        multipleSeriesRenderer.setMargins(new int[]{30, 30, 30, 30});
        multipleSeriesRenderer.setShowGrid(true);
        multipleSeriesRenderer.setZoomEnabled(true, false);
        multipleSeriesRenderer.setAxesColor(axeColor);
        multipleSeriesRenderer.setGridColor(gridColor);
        multipleSeriesRenderer.setDisplayValues(true);
        multipleSeriesRenderer.setApplyBackgroundColor(true);
        multipleSeriesRenderer.setBackgroundColor(Color.WHITE);//背景色 必须和上面那条连用
        multipleSeriesRenderer.setMarginsColor(Color.WHITE);//边距背景色，默认背景色为黑色，这里修改为白色
        mRenderer = new XYSeriesRenderer();
        mRenderer.setColor(Color.BLUE);
        mRenderer.setLineWidth(3f);
        //mRenderer.setPointStyle(PointStyle.CIRCLE);//描点风格，可以为圆点，方形点等等
        //mRenderer.setFillPoints(true);
        multipleSeriesRenderer.addSeriesRenderer(mRenderer);
    }

    /**
     * 根据新加的数据，更新曲线，只能运行在主线程
     *
     * @param x
     *            新加点的x坐标
     * @param y
     *            新加点的y坐标
     */
    public void updateChart(double x, double y) {
        multipleSeriesDataset.removeSeries(mSeries);
        mSeries.add(x, y);
        multipleSeriesDataset.addSeries(0,mSeries);
        //Xmin += 5;
        //Xmax += 5;
        // TODO: 2016/10/2 原来是90
        if (Xmin + x >= 1800){//当图还有百分之十的空闲时开始平移背景
            multipleSeriesRenderer.setXAxisMin(Xmin + x - 1800);//始终留有百分之十的空余
            multipleSeriesRenderer.setXAxisMax(Xmax + x - 1800);// TODO: 2016/10/2  原来是80
        }
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }

    /**
     * 添加新的数据，多组，更新曲线，只能运行在主线程
     * @param xList
     * @param yList
     */
    public void updateChart(List<Double> xList, List<Double> yList) {
        for (int i = 0; i < xList.size(); i++) {
            mSeries.add(xList.get(i), yList.get(i));
        }
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }
}