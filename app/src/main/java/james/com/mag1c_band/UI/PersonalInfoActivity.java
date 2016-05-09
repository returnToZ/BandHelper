package james.com.mag1c_band.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import james.com.mag1c_band.R;

public class PersonalInfoActivity extends Activity{
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        edit = (Button)findViewById(R.id.edit_profile);
        edit.getBackground().setAlpha(0);
    }
}
