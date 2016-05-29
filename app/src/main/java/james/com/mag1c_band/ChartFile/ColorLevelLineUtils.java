package james.com.mag1c_band.ChartFile;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportSeriesRender;

import java.util.ArrayList;

public class ColorLevelLineUtils extends  BaseSupportUtils{

    private static final String TAG = ColorLevelLineUtils.class.getSimpleName();
    private final  static int COLOR_UP_TARGET = Color.parseColor("#FF843D");
    private final  static int COLOR_LOW_TARGET = Color.parseColor("#FFC23E");
    private final  static int COLOR_OTHER= Color.parseColor("#8FD85A");

    public ColorLevelLineUtils(Context context) {//构造函数
        super(context);
        mContext = context;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {
        render.setPointSize(12f);//设置点的大小
    }

    public View initLineGraphView() {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        final SupportSeriesRender lineSeriesRender = new SupportSeriesRender();
        lineSeriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        lineSeriesRender.setColorLevelValid(false);
        ArrayList<SupportColorLevel> list = new ArrayList<SupportColorLevel>();

        //如果仅仅以target作为颜色分级，可以使用这个用法
//        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,mXYRenderer.getTargetValue(),COLOR_LOW_TARGET);
//        SupportColorLevel supportColorLevel_b = new SupportColorLevel(mXYRenderer.getTargetValue(),mXYRenderer.getTargetValue()*10,COLOR_UP_TARGET);

        // 若有多个颜色等级可以使用这个用法
        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,10,COLOR_LOW_TARGET);
        SupportColorLevel supportColorLevel_b = new SupportColorLevel(10,15,COLOR_UP_TARGET);
        SupportColorLevel supportColorLevel_c = new SupportColorLevel(15,20,COLOR_OTHER);


        list.add(supportColorLevel_a);
        list.add(supportColorLevel_b);
        list.add(supportColorLevel_c);
        lineSeriesRender.setColorLevelList(list);


        final String[] hours = new String[20];
        final int[] allDataSets = new int[]{
                5,8,10,11,13,15,10,7,14,18,13,10, 5,8,10,11,15,10,7,14
        };
        XYSeries sysSeries = new XYSeries("");
        for (int i = 0; i < allDataSets.length; i++) {
            sysSeries.add(i, allDataSets[i]);//XYSeries相当于content values
            mXYRenderer.addXTextLabel(i, hours[i]);
        }

        mXYRenderer.addSupportRenderer(lineSeriesRender);

        mXYMultipleSeriesDataSet.addSeries(sysSeries);
        //如果不许要颜色分级功能，则直接用原始的lineChart既可
        final View view =  ChartFactory.getLineChartView(mContext, mXYMultipleSeriesDataSet, mXYRenderer);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphicalView graphicalView = (GraphicalView) v;
                SeriesSelection seriesSelection = ((GraphicalView) v).getCurrentSeriesAndPoint();
                if (seriesSelection == null)    return;//如果没有点到点上则返回
                graphicalView.handPointClickEvent(lineSeriesRender,String.valueOf(seriesSelection.getValue()));//显示相应数据
            }
        });
        return  view;
    }


    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setFillPoints(true);   //是否是实心的点
        renderer.setDisplayChartValues(true);  // 设置是否在点上显示数据
        renderer.setLineWidth(5f);    //设置曲线的宽度
        renderer.setPointStyle(PointStyle.CIRCLE_POINT);
        renderer.setInnerCircleColor(Color.parseColor("#CC9B61"));
        return renderer;
    }


}
