package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import james.com.mag1c_band.R;

public class MainActivity extends Activity {

    ImageView setting;
    ImageView share;
    ImageView chat;
    public static MainActivity MainInstance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent = new Intent(view.getContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,ExitWindow.class);
        startActivity(intent);
    }
}
