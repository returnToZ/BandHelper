package james.com.mag1c_band.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import james.com.mag1c_band.Util.Utils;

public class RegisterActivity extends Activity{
    private Button register;
    private Button cancel;
    private EditText mPassword;
    private EditText mUsername;
    private EditText mConfirm;
    private String username;
    private String password;
    private String confirm;
    private String signal;
    private RequestQueue mQueue;
    public static RegisterActivity registerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWidget();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
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
    /*
   注册:
   1.网络不可用
   2.帐号密码长度不合法
   3.两次密码输入不一致
   4.未知错误
   5.用户名已存在
   6.注册成功
    */
    private void Register(){
        if (!Utils.isNetworkAvailable(registerActivity)){
            Toast.makeText(registerActivity, "网络不可用", Toast.LENGTH_SHORT).show();
        }
        username = mUsername.getText().toString();
        password = mPassword.getText().toString();
        confirm = mConfirm.getText().toString();
        if (username.length() < 6 | username.length() > 20){//用户名长度检测
            Toast.makeText(registerActivity,"用户名长度不合法,应在6-20个字符之间",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6 | password.length() > 20){
            Toast.makeText(registerActivity,"密码长度不合法,应在6-20个字符之间",Toast.LENGTH_SHORT).show();
            mPassword.setText("");
            mConfirm.setText("");//清空两个密码输入框
            return;
        }
        if (!password.equals(confirm)){
            Toast.makeText(registerActivity,"两次输入密码不一致,请重新输入",Toast.LENGTH_SHORT).show();
            mPassword.setText("");
            mConfirm.setText("");//清空两个密码输入框
            return;
        }
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String answer = null;
                if (msg.what == Symbol.RETURN_SUCCESS)
                {
                    Bundle bundle = msg.getData();
                    answer = bundle.getString("result");
                }else if (msg.what == Symbol.RETURN_FAIL){
                    Toast.makeText(registerActivity,"服务器错误,请稍后再试",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (answer == null){
                    Toast.makeText(registerActivity,"未知错误",Toast.LENGTH_SHORT).show();
                }else if (answer.equals("already_exist")){
                    Toast.makeText(registerActivity,"该用户名已存在!",Toast.LENGTH_SHORT).show();
                    mPassword.setText("");
                    mConfirm.setText("");
                }else if (answer.equals("success")){
                    Toast.makeText(registerActivity,"注册成功!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerActivity,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Account account = new Account(username,password);
                mQueue = Volley.newRequestQueue(RegisterActivity.registerActivity);
                JsonObjectRequest jsonRequest;
                JSONObject jsonObject = null;
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
                            Request.Method.POST, URL.REGISTER_URL, jsonObject,
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
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                mQueue.start();
            }
        }).start();
    }
    private void initWidget(){
        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);
        register.getBackground().setAlpha(0);
        cancel.getBackground().setAlpha(0);
        mUsername = (EditText)findViewById(R.id.account);
        mPassword = (EditText)findViewById(R.id.password);
        mConfirm = (EditText)findViewById(R.id.confirm_password);
        registerActivity = this;
        mQueue = Volley.newRequestQueue(RegisterActivity.registerActivity);
    }
}
