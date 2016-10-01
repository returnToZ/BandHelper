package james.com.mag1c_band.Activity;

import android.app.Activity;
import android.os.Bundle;

import james.com.mag1c_band.R;

public class MyDeviceActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);
    }
}
