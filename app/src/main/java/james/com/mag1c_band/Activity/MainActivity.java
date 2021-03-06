package james.com.mag1c_band.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.umeng.socialize.media.UMImage;

import org.achartengine.GraphicalView;

import java.util.Timer;
import java.util.TimerTask;

import james.com.mag1c_band.ChartFile.ChartService;
import james.com.mag1c_band.R;
import james.com.mag1c_band.Util.KalmanAlgorithm;
import james.com.mag1c_band.Util.StepCount;
import james.com.mag1c_band.Util.Utils;

public class MainActivity extends Activity {

    private ImageView setting;
    private ImageView share;
    private ImageView chat;
    private Button start;
    private LinearLayout mLeftCurveLayout;//存放左图表的布局容器
    private GraphicalView mView;//左右图表
    private ChartService mService;
    private Timer timer;
    private int t = 0;
    private KalmanAlgorithm kalmanAlgorithm;
    private StepCount stepCount;
    private byte[] stepData;
    private TextView stepNum;
    private int numberOfStep = 0;
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN
            };//分享功能
    public static MainActivity MainInstance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initListener();
        initChart();
        initKeys();
        stepData = Utils.isChartDataExists(this);
        if (stepData != null)
        {
            Log.d("readOrNot",String .valueOf(stepData.length));
            kalmanAlgorithm = new KalmanAlgorithm(stepData);
            stepCount = new StepCount();
        }
    }

    private void initChart() {
        mLeftCurveLayout = (LinearLayout) findViewById(R.id.step_chart);

        mService = new ChartService(this);
        mService.setXYMultipleSeriesDataset("步数曲线");
        mService.setXYMultipleSeriesRenderer(2000, 40000, "步数曲线", "时间", "温度",
                Color.BLUE, Color.RED, Color.LTGRAY, Color.BLACK);//轴的颜色 标签的颜色 曲线的颜色 格子的颜色
        mView = mService.getGraphicalView();

        mLeftCurveLayout.addView(mView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage());
            }
        }, 50, 50);//更改时间
    }

    private Handler handler = new Handler() {
        @Override
        //定时更新图表
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double temp = kalmanAlgorithm.getFinalData();
            if (temp == -9999999f)//数据读完了就不读了
            {
                timer.cancel();
            }
            Log.d("processedData", String.valueOf(temp));
            mService.updateChart(t, temp);
            //mService2.updateChart(t, Math.random() * 100);
            t += 7;
            numberOfStep += stepCount.calcStep(kalmanAlgorithm.getCalcY());
            Log.d("yLast", String.valueOf(kalmanAlgorithm.getCalcY()));
            stepNum.setText(String.valueOf(numberOfStep));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
        {
            timer.cancel();
        }
    }

    private void initWidget() {
        setting = (ImageView) findViewById(R.id.setting);
        share = (ImageView) findViewById(R.id.share);
        chat = (ImageView) findViewById(R.id.chat);
        start = (Button) findViewById(R.id.start);
        stepNum = (TextView) findViewById(R.id.step_num);
        MainInstance = this;
    }

    private void initListener() {
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingActivity.class);
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
                        .withText("test")
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
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Test.class);
                startActivity(intent);
            }
        });
    }

    private void initKeys() {
                /*
        以下是分享功能的初始化设置,在这里填申请来的密钥,注意应放在程序的入口
         */
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3319386979", "4f8927a364847eac97ccb8f11abb9ac5");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, ExitWindow.class);
        startActivity(intent);
    }
}
