package james.com.mag1c_band;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button setting;
    Button share;
    Button chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting = (Button)findViewById(R.id.setting);
        share = (Button)findViewById(R.id.share);
        chat = (Button)findViewById(R.id.chat);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
