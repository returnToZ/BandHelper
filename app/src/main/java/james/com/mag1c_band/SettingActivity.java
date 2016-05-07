package james.com.mag1c_band;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

public class SettingActivity extends Activity implements View.OnClickListener{
    TableRow personInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        personInfo = (TableRow)findViewById(R.id.personInfo);
        personInfo.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personInfo:
                Intent intent1 = new Intent(v.getContext(),PersonalInfoActivity.class);
                startActivity(intent1);
        }
    }
}
