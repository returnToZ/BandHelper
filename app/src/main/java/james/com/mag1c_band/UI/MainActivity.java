package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;

import org.w3c.dom.Text;

import james.com.mag1c_band.ChartFile.ChartActivity;
import james.com.mag1c_band.R;

public class MainActivity extends Activity {

    ImageView setting;
    ImageView share;
    ImageView chat;
    TextView chart;
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
        chart = (TextView)findViewById(R.id.chart);
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
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ChartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,ExitWindow.class);
        startActivity(intent);
    }
}
