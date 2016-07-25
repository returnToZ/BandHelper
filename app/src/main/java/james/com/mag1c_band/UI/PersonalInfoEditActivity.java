package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import james.com.mag1c_band.R;

public class PersonalInfoEditActivity extends Activity {
    Button save;
    EditText nickname;//nickname
    EditText phone_number;//phone_number
    EditText mail;//mail
    EditText address;//address
    EditText status;
    EditText sex;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_edit);
        save = (Button)findViewById(R.id.save_profile);
        nickname = (EditText)findViewById(R.id.nickname);
        phone_number = (EditText)findViewById(R.id.phone_number);
        mail = (EditText)findViewById(R.id.mail);
        address = (EditText)findViewById(R.id.birthday);
        sex = (EditText)findViewById(R.id.my_sex);
        status = (EditText)findViewById(R.id.status);
        showPersonal();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPersonal();
                Intent intent = new Intent(view.getContext(),PersonalInfoShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    private void setPersonal(){//设置个人信息
    }
    private void showPersonal(){//读取文件中的个人信息
    }

}
