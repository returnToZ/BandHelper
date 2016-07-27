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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import james.com.mag1c_band.Data.Symbol;
import james.com.mag1c_band.Data.URL;
import james.com.mag1c_band.R;

public class PersonalInfoShowActivity extends Activity{
    private Button edit;
    private TextView nickname;//nickname
    private TextView telephone;//telephone
    private TextView email;//email
    private TextView address;//address
    private TextView sex;//sex
    private TextView status;
    private SharedPreferences getInfo;
    private SharedPreferences.Editor setInfo;
    private SharedPreferences getLoginInfo;
    private RequestQueue mQueue;
    private String username;
    public static PersonalInfoShowActivity personalInfoShowActivity;
    private String signal = "result";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_show);
        initWidget();
        updateProfile();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PersonalInfoEditActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    private void updateProfile(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String answer = "";
                if (msg.what == Symbol.RETURN_SUCCESS)
                {
                    Toast.makeText(personalInfoShowActivity,"资料更新成功",Toast.LENGTH_SHORT).show();
                }else if (msg.what == Symbol.RETURN_FAIL){
                    Toast.makeText(personalInfoShowActivity,"服务器错误,请稍后再试",Toast.LENGTH_SHORT).show();
                    showLocalProfile();//没读到数据 则直接读本地的
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
                mQueue = Volley.newRequestQueue(LoginActivity.loginActivity);
                JsonObjectRequest jsonRequest;
                JSONObject jsonObject = null;
                username = getUsername();//从本地文件中取出username
                try
                {
                    jsonObject = new JSONObject("{username:" + username + "}");
                    Log.d("Sending_Message", jsonObject.toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //发送post请求
                try
                {
                    jsonRequest = new JsonObjectRequest(
                            Request.Method.POST, URL.GET_PROFILE_URL, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //发送jsonObject 并在返回成功的回调里处理结果
                                    try
                                    {
                                        signal = response.getString("result");
                                        Log.d("Response_Message", response.toString());
                                        if (signal.equals("success")){
                                            handleProfile(response);//处理返回的资料信息
                                            Message msg = new Message();
                                            msg.what = Symbol.RETURN_SUCCESS;
                                            handler.sendMessage(msg);
                                        }else if (signal.equals("not_exist")){
                                            Message msg = new Message();
                                            msg.what = Symbol.RETURN_FAIL;
                                            handler.sendMessage(msg);
                                        }
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

    private void handleProfile(JSONObject reponse){
        //主要信息有 1.状态信息(success or not_exist) 2.昵称 3.性别 4.手机 5.邮箱 6.出生日期 7.状态
        try
        {
            setInfo.putString("nickname",reponse.getString("nickname"));
            setInfo.putString("sex",reponse.getString("sex"));
            setInfo.putString("telephone",reponse.getString("telephone"));
            setInfo.putString("email",reponse.getString("email"));
            setInfo.putString("birthday",reponse.getString("birthday"));
            setInfo.putString("status",reponse.getString("status"));
            setInfo.commit();
            showLocalProfile();//更新成功后读取本地数据
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
    /**
     * 如何读取个人信息
     * 从服务器端同步个人信息
     * 无论同步的结果是成功还是失败
     * 都再从本地xml文件中读取
     */
    private void showLocalProfile(){//读取本地的个人信息
        nickname.setText(getInfo.getString("nickname","尚未填写"));
        telephone.setText(getInfo.getString("telephone","尚未填写"));
        email.setText(getInfo.getString("email","尚未填写"));
        address.setText(getInfo.getString("address", "尚未填写"));
        sex.setText(getInfo.getString("sex", "尚未填写"));
        status.setText(getInfo.getString("status","尚未填写"));
    }
    private String getUsername(){
        return getLoginInfo.getString("username","error");
    }
    private void initWidget(){
        edit = (Button)findViewById(R.id.edit_profile);
        edit.getBackground().setAlpha(0);
        nickname = (TextView)findViewById(R.id.nickname);
        telephone = (TextView)findViewById(R.id.phone_number);
        email = (TextView)findViewById(R.id.mail);
        address = (TextView)findViewById(R.id.birthday);
        sex = (TextView)findViewById(R.id.my_sex);
        status = (TextView)findViewById(R.id.status);
        getInfo = getSharedPreferences("personal_data",MODE_PRIVATE);
        setInfo = getSharedPreferences("personal_data",MODE_PRIVATE).edit();
        getLoginInfo = getSharedPreferences("login_data",MODE_PRIVATE);
        personalInfoShowActivity = this;
        mQueue = Volley.newRequestQueue(PersonalInfoShowActivity.personalInfoShowActivity);
    }
}
