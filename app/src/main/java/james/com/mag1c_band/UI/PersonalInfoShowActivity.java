package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import james.com.mag1c_band.R;

public class PersonalInfoShowActivity extends Activity{
    Button edit;
    TextView nickname;//nickname
    TextView phone_number;//phone_number
    TextView mail;//mail
    TextView address;//address
    TextView sex;//sex
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_show);
        edit = (Button)findViewById(R.id.edit_profile);
        edit.getBackground().setAlpha(0);
        nickname = (TextView)findViewById(R.id.nickname);
        phone_number = (TextView)findViewById(R.id.phone_number);
        mail = (TextView)findViewById(R.id.mail);
        address = (TextView)findViewById(R.id.birthday);
        sex = (TextView)findViewById(R.id.my_sex);
        status = (TextView)findViewById(R.id.status);
        showPersonal();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PersonalInfoEditActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    private void showPersonal(){
        SharedPreferences pref = getSharedPreferences("personal_data",MODE_PRIVATE);
        nickname.setText(pref.getString("nickname","尚未填写"));
        phone_number.setText(pref.getString("phone_number","尚未填写"));
        mail.setText(pref.getString("mail","尚未填写"));
        address.setText(pref.getString("address", "尚未填写"));
        sex.setText(pref.getString("sex", "尚未填写"));
    }
}
