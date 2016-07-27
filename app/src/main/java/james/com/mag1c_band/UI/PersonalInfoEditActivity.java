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

import james.com.mag1c_band.Data.Profile;
import james.com.mag1c_band.Data.Symbol;
import james.com.mag1c_band.Data.URL;
import james.com.mag1c_band.R;

public class PersonalInfoEditActivity extends Activity {
    private Button save;
    private EditText nickname;//nickname
    private EditText telephone;//telephone
    private EditText email;//email
    private EditText birthday;//birthday
    private EditText status;
    private EditText sex;
    private SharedPreferences getInfo;
    private SharedPreferences.Editor setInfo;
    private SharedPreferences getLoginInfo;
    private RequestQueue mQueue;
    private String username;
    Profile profile;
    public static PersonalInfoEditActivity personalInfoEditActivity;
    private String signal = "result";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_edit);
        initWidget();
        showProfile();//先加载本地数据
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
                Intent intent = new Intent(view.getContext(),PersonalInfoShowActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    /**
     * 主要思路：
     * 先初始化 用本地文件初始化EditText
     * 得到所有的EditText框中的数据
     * 先写入本地 然后发送到服务器
     */
    private void setProfile(){//设置个人信息
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String answer;
                Bundle bundle = msg.getData();
                answer = bundle.getString("result");
                if (msg.what == Symbol.RETURN_SUCCESS)
                {
                    if (answer.equals("success")){
                        Toast.makeText(personalInfoEditActivity,"资料更新成功",Toast.LENGTH_SHORT).show();
                    }
                }else if (msg.what == Symbol.RETURN_FAIL){
                    Toast.makeText(personalInfoEditActivity,"服务器错误,请稍后再试",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };
        /*
        网络请求的线程,将服务器返回的结果传给handler
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                profile.setBirthday(birthday.getText().toString());
                profile.setEmail(email.getText().toString());
                profile.setSex(sex.getText().toString());
                profile.setTelephone(telephone.getText().toString());
                profile.setStatus(status.getText().toString());
                profile.setNickname(nickname.getText().toString());
                profile.setUsername(getUsername());//从本地文件中取出username
                mQueue = Volley.newRequestQueue(LoginActivity.loginActivity);
                JsonObjectRequest jsonRequest;
                JSONObject jsonObject = null;
                try
                {
                    jsonObject = new JSONObject(profile.toJson());
                    Log.d("Sending_Message", jsonObject.toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //发送post请求
                try
                {
                    jsonRequest = new JsonObjectRequest(
                            Request.Method.POST, URL.SAVE_PROFILE_URL, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //发送jsonObject 并在返回成功的回调里处理结果
                                    try
                                    {
                                        signal = response.getString("result");
                                        Log.d("Response_Message", response.toString());
                                        Bundle bundle = new Bundle();
                                        bundle.putString("result",signal);
                                        Message msg = new Message();
                                        msg.setData(bundle);
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
    private void showProfile(){//读取文件中的个人信息
        birthday.setText(getInfo.getString("birthday","error"));
        nickname.setText(getInfo.getString("nickname","error"));
        sex.setText(getInfo.getString("sex","error"));
        status.setText(getInfo.getString("status","error"));
        telephone.setText(getInfo.getString("telephone","error"));
        email.setText(getInfo.getString("email","error"));
        
    }
    private String getUsername(){
        return getLoginInfo.getString("username","error");
    }
    private void initWidget(){
        save = (Button)findViewById(R.id.save_profile);
        nickname = (EditText)findViewById(R.id.nickname);
        telephone = (EditText)findViewById(R.id.phone_number);
        email = (EditText)findViewById(R.id.mail);
        birthday = (EditText)findViewById(R.id.birthday);
        sex = (EditText)findViewById(R.id.my_sex);
        status = (EditText)findViewById(R.id.status);
        getInfo = getSharedPreferences("personal_data",MODE_PRIVATE);
        setInfo = getSharedPreferences("personal_data",MODE_PRIVATE).edit();
        getLoginInfo = getSharedPreferences("login_data",MODE_PRIVATE);
        personalInfoEditActivity = this;
        mQueue = Volley.newRequestQueue(PersonalInfoEditActivity.personalInfoEditActivity);
        profile = new Profile();
    }
}
