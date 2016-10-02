package james.com.mag1c_band.ChartFile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.achartengine.GraphicalView;

import java.util.Timer;
import java.util.TimerTask;

import james.com.mag1c_band.R;

public class ChartActivity extends Activity {

    private LinearLayout mLeftCurveLayout;//存放左图表的布局容器
    private GraphicalView mView;//左右图表
    private ChartService mService;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mLeftCurveLayout = (LinearLayout) findViewById(R.id.step_chart);

        mService = new ChartService(this);
        mService.setXYMultipleSeriesDataset("步数曲线");
        mService.setXYMultipleSeriesRenderer(100, 4000, "步数曲线", "时间", "温度",
                Color.BLUE, Color.RED, Color.LTGRAY, Color.BLACK);//轴的颜色 标签的颜色 曲线的颜色 格子的颜色
        mView = mService.getGraphicalView();

        mLeftCurveLayout.addView(mView, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage());
            }
        }, 3000, 3000);
    }

    private int t = 0;
    private Handler handler = new Handler() {
        @Override
        //定时更新图表
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mService.updateChart(t, Math.random() * 100 % 50);
            //mService2.updateChart(t, Math.random() * 100);
            t+=5;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

}
