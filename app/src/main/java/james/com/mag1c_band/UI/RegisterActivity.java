package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import james.com.mag1c_band.R;

public class RegisterActivity extends Activity{
    Button register;
    Button cancel;
    EditText password;
    EditText account;
    EditText nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);
        register.getBackground().setAlpha(0);
        cancel.getBackground().setAlpha(0);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        nickname = (EditText)findViewById(R.id.nickname);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("personal_data",MODE_PRIVATE).edit();
                editor.putString("nickname",nickname.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.putString("account",account.getText().toString());
                editor.apply();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
