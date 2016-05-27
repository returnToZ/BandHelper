package james.com.mag1c_band.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import james.com.mag1c_band.R;

public class MsgAdapterActivity extends ArrayAdapter<MsgActivity> {
    static Integer data = 0;
    private int resourceID;

    public MsgAdapterActivity(Context context, int textViewResourceID, List<MsgActivity> objects)
    {
        super(context, textViewResourceID, objects);          //adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item,msgList);
        resourceID = textViewResourceID;                    //resourceID是传入的消息类的布局
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //data += 1;
        //Log.d("TAG","helloworld"+data);                     //debug用 查看getView被调用次数
        MsgActivity msg = getItem(position);                        //position是该项在列表中的下标
        View view;
        ViewHolder viewHolder;
        if (convertView == null)                             //判断是否需要新建一个view
        {
            data += 1;
            Log.d("TAG", "helloworld+" + data);
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);          //LayoutInflater专门用于给子项传入布局
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
            view.setTag(viewHolder);                                                   //保存当前的布局信息 与else分支中的getTag对应
        } else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();                                     //取出之前保存的布局
        }
        if (msg.getType() == MsgActivity.TYPE_RECEIVED)                                          //假如是收到的信息则用左边的布局
        {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
        } else if (msg.getType() == MsgActivity.TYPE_SENT)
        {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
        return view;
    }

    class ViewHolder                                    //ViewHolder是用来存储view中的基本布局的
    {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }
}
