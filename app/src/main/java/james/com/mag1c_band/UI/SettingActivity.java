package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import james.com.mag1c_band.R;

public class SettingActivity extends Activity implements View.OnClickListener{
    TableRow personInfo;
    TableRow myDevice;
    TableRow about;
    TableRow help;
    TableRow service;
    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        personInfo = (TableRow)findViewById(R.id.personInfo);
        myDevice = (TableRow)findViewById(R.id.myDevice);
        about = (TableRow)findViewById(R.id.about);
        help = (TableRow)findViewById(R.id.help);
        logout = (ImageView)findViewById(R.id.logout);
        service = (TableRow)findViewById(R.id.myService);
        personInfo.setOnClickListener(this);
        myDevice.setOnClickListener(this);
        about.setOnClickListener(this);
        help.setOnClickListener(this);
        logout.setOnClickListener(this);
        service.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personInfo:
                Intent intent = new Intent(v.getContext(),PersonalInfoShowActivity.class);
                startActivity(intent);
                break;
            case R.id.myDevice:
                Intent intent1 = new Intent(v.getContext(),MyDeviceActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(v.getContext(),AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent3 = new Intent(v.getContext(),HelpActivity.class);
                startActivity(intent3);
                break;
            case R.id.logout:
                Intent intent4 = new Intent(v.getContext(),LoginActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.myService:
                Intent intent5 = new Intent(v.getContext(),ServiceActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
