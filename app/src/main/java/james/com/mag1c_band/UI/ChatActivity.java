package james.com.mag1c_band.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import james.com.mag1c_band.R;

public class ChatActivity extends Activity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapterActivity adapter;
    private List<MsgActivity> msgList = new ArrayList<>();       //创建各个控件

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);         //创建页面布局
        initMsgs();                                     //初始化消息
        adapter = new MsgAdapterActivity(ChatActivity.this, R.layout.msg_item,msgList);
        send = (Button) findViewById(R.id.send);
        inputText = (EditText) findViewById(R.id.input_text);
        msgListView = (ListView)findViewById(R.id.msg_list_view);//将各个控件实例化，通过自己在xml文件中定义的id来查找
        msgListView.setAdapter(adapter);                        //为ListView存放适配器
        send.setOnClickListener(new View.OnClickListener()      //设置Button的点击动作，通过一个监听器
        {
            @Override
            public void onClick(View v)
            {
                String content = inputText.getText().toString();//content是inputText中的文本内容 是String类型
                if(!"".equals(content))                         //如果content不为空
                {
                    MsgActivity msg = new MsgActivity(content, MsgActivity.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();             //当有新消息时 通知列表的数据发生了变化 这样新增的消息才能才ListView中显示
                    msgListView.setSelection(msgList.size());   //将数据定位到最后一行 保证刚发送的那条信息出现在屏幕
                    inputText.setText("");                      //将inputText中的内容清空
                }
            }
        });
    }
    private void initMsgs()                                     //初始化聊天框内容
    {
        MsgActivity msg1 = new MsgActivity("Hello Guy.", MsgActivity.TYPE_RECEIVED);
        msgList.add(msg1);
        MsgActivity msg2 = new MsgActivity("Hello Who is that?", MsgActivity.TYPE_SENT);
        msgList.add(msg2);
        MsgActivity msg3 = new MsgActivity("This is Tom.", MsgActivity.TYPE_RECEIVED);
        msgList.add(msg3);
        MsgActivity msg4 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg4);
        MsgActivity msg5 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg5);
        MsgActivity msg6 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg6);
        MsgActivity msg7 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg7);
        MsgActivity msg8 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg8);
        MsgActivity msg9 = new MsgActivity("Hi Tom", MsgActivity.TYPE_SENT);
        msgList.add(msg9);
    }
}
