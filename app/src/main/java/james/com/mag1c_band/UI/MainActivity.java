package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Text;

import java.util.TimerTask;

import james.com.mag1c_band.ChartFile.ChartActivity;
import james.com.mag1c_band.R;

public class MainActivity extends Activity {

    ImageView setting;
    ImageView share;
    ImageView chat;
    private String title = "线性统计图示例";
    private XYSeries series;// XY数据点
    private XYMultipleSeriesDataset mDataset;// XY轴数据集
    private GraphicalView mViewChart;// 用于显示现行统计图
    private XYMultipleSeriesRenderer mXYRenderer;// 线性统计图主描绘器
    private Context context;// 用于获取上下文对象
    private LinearLayout mLayout;
    private int X = 5;// X数据集大小
    private int Y = 5;//
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.DOUBAN
            };//分享功能
    public static MainActivity MainInstance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        以下是分享功能的初始化设置,在这里填申请来的密钥,注意应放在程序的入口
         */
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3319386979", "4f8927a364847eac97ccb8f11abb9ac5");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
        setting = (ImageView)findViewById(R.id.setting);
        share = (ImageView)findViewById(R.id.share);
        chat = (ImageView)findViewById(R.id.chat);
        MainInstance = this;
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SettingActivity.class);
                startActivity(intent);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMImage image = new UMImage(MainActivity.this,
                        BitmapFactory.decodeResource(getResources(), R.drawable.pink));
                new ShareAction(MainActivity.this).setDisplayList(displaylist)
                        .withText( "test" )
                        .withTitle("title")//title参数对新浪、人人、豆瓣不生效
                        .withTargetUrl("Jameeeees.github.io")//文本中的目标地址
                        .withMedia(image)
                        .setListenerList(new UMShareListener() {
                            @Override
                            public void onResult(SHARE_MEDIA platform) {
                                Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA platform, Throwable t) {
                                Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA platform) {
                                Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .open();
            }
        });
        /*
        以下为设置图表的逻辑
         */
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
        mXYRenderer.setXLabels(5);//设置X轴刻度个数
        mXYRenderer.setYLabels(5);//设置Y轴刻度个数
        mXYRenderer.setExternalZoomEnabled(true);//设置是否可以缩放
        mXYRenderer.setYLabelsAlign(Paint.Align.RIGHT);// 右对齐
        //mXYRenderer.setAxisTitleTextSize(50); // 坐标轴标题字体大小： 16
        //mXYRenderer.setChartTitleTextSize(50); // 图表标题字体大小： 20
        mXYRenderer.setLabelsTextSize(100f); // 轴标签字体大小： 15
        //mXYRenderer.setLegendTextSize(55); // 图例字体大小： 15
        mXYRenderer.setShowLegend(false);// 不显示图例
        mXYRenderer.setZoomEnabled(false);
        mXYRenderer.setPanEnabled(true, false);
        mXYRenderer.setClickEnabled(false);
        setChartSettings(mXYRenderer, title, "时间", "数量", 1, 5, 50, 170,//xmin,xmax,ymin,ymax
                Color.WHITE, Color.WHITE);// 这个是采用官方APIdemo提供给的方法
        // 设置好图表的样式

        mViewChart = ChartFactory.getLineChartView(context, mDataset,
                mXYRenderer);// 通过ChartFactory生成图表

        mLayout.addView(mViewChart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));// 将图表添加到布局中去
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,ExitWindow.class);
        startActivity(intent);
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

}
