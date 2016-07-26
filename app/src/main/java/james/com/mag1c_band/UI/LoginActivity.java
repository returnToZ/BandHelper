package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import james.com.mag1c_band.Data.Account;
import james.com.mag1c_band.Data.Symbol;
import james.com.mag1c_band.Data.URL;
import james.com.mag1c_band.R;
import james.com.mag1c_band.Util.MD5;
import james.com.mag1c_band.Util.Utils;

public class LoginActivity extends Activity{
    private Button login;
    private Button register;
    private String password;
    private String username;
    private EditText mPassword;
    private EditText mUsername;
    private RequestQueue mQueue;
    private CheckBox rememberPassword;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String signal = "result";//存储服务器端返回的结果
    public static LoginActivity loginActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidget();
        //以下为记住密码功能
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){
            String username = pref.getString("username","");
            String password = pref.getString("password","");
            mUsername.setText(username);
            mPassword.setText(password);
            rememberPassword.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginResult();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(),RegisterActivity.class);
                finish();
                startActivity(intent2);
            }
        });
    }
    /*
   登录:
   1.无网络连接
   2.帐号密码长度不合法
   3.登录成功
   4.账号不存在
   5.密码错误
   6.服务器错误
   7.未知错误
    */
    public void loginResult(){
        loginActivity = this;
        username = mUsername.getText().toString();
        password = mPassword.getText().toString();
        if (username.length() < 6 | username.length() > 20){//用户名长度检测
            Toast.makeText(loginActivity, "用户名长度不合法,应在6-20个字符之间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6 | password.length() > 20){//密码长度检测
            Toast.makeText(loginActivity,"密码长度不合法,应在6-20个字符之间",Toast.LENGTH_SHORT).show();
            mPassword.setText("");//清空密码输入框
            return;
        }
        Log.d("TAG", password);
        if (!Utils.isNetworkAvailable(loginActivity)){
            Toast.makeText(loginActivity,"网络不可用",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        checkPassword();
    }

    private void checkPassword() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String answer = "";
                if (msg.what == Symbol.RETURN_SUCCESS)
                {
                    Bundle bundle = msg.getData();
                    answer = bundle.getString("result");
                    Log.d("TAG",answer);
                }else if (msg.what == Symbol.RETURN_FAIL){
                    Toast.makeText(loginActivity,"服务器错误,请稍后再试",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (answer == null){
                    Toast.makeText(loginActivity,"未知错误",Toast.LENGTH_SHORT).show();
                }else if (answer.equals("success")){
                    //在成功的情况下 如果账号相等而密码不等 则将正确的密码写入
                    if (rememberPassword.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.apply();
                    }else
                    {
                        editor.putBoolean("remember_password", false);
                        editor.apply();
                    }
                    Toast.makeText(loginActivity,"登录成功",Toast.LENGTH_SHORT).show();
                    if (pref.getString("username","").equals(username)){//账号相等
                        if (!pref.getString("password","").equals(password)){//密码不等
                            editor.putString("password", password);
                            editor.apply();
                        }
                    }else {//若是不同账号则直接把新的账号密码存入
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.apply();
                    }
                    Intent intent = new Intent(loginActivity,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if (answer.equals("not_exist")){
                    Toast.makeText(loginActivity,"账号不存在",Toast.LENGTH_SHORT).show();
                    mUsername.setText("");
                    mPassword.setText("");
                }else if (answer.equals("error")){
                    Toast.makeText(loginActivity,"密码错误",Toast.LENGTH_SHORT).show();
                    mPassword.setText("");
                }else if (answer.equals("result")){
                    Toast.makeText(loginActivity,"服务器问题,请稍后再试",Toast.LENGTH_SHORT).show();
                }
            }
        };
        /*
        网络请求的线程,将服务器返回的结果传给handler
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonRequest;
                JSONObject jsonObject = null;
                Account account = new Account(username,password);
                try
                {
                    jsonObject = new JSONObject(account.toJson());
                    Log.d("Sending_Message", jsonObject.toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //发送post请求
                    try
                    {
                        jsonRequest = new JsonObjectRequest(
                                Request.Method.POST, URL.LOGIN_URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //发送jsonObject 并在返回成功的回调里处理结果
                                        try
                                        {
                                            signal = response.getString("result");
                                            Log.d("Response_Message", response.toString());
                                            Log.d("Extract_result", signal);
                                            Message msg = new Message();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("result", signal);
                                            msg.setData(bundle);
                                            msg.what = Symbol.RETURN_SUCCESS;
                                            handler.sendMessage(msg);
                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }

                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                Log.d("Failure_Message", arg0.toString());
                                Message msg = new Message();
                                msg.what = Symbol.RETURN_FAIL;
                                handler.sendMessage(msg);
                            }
                        });
                        mQueue.add(jsonRequest);
                        Log.d("The_Whole_JsonRequest", jsonRequest.toString());
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                mQueue.start();
            }
        }).start();
    }
    private void initWidget(){
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        mUsername = (EditText)findViewById(R.id.username_edit);
        mPassword = (EditText)findViewById(R.id.password_edit);
        rememberPassword = (CheckBox)findViewById(R.id.remember_password);
        pref = getSharedPreferences("login_data", MODE_PRIVATE);
        editor = getSharedPreferences("login_data",MODE_PRIVATE).edit();
        login.getBackground().setAlpha(0);
        register.getBackground().setAlpha(0);
        mQueue = Volley.newRequestQueue(LoginActivity.loginActivity);
    }
}
