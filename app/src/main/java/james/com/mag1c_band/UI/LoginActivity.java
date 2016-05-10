package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import james.com.mag1c_band.R;

public class LoginActivity extends Activity{
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        login.getBackground().setAlpha(0);
        register.getBackground().setAlpha(0);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(),RegisterActivity.class);
                startActivity(intent2);
            }
        });
    }
}
