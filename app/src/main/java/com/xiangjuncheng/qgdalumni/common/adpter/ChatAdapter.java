package com.xiangjuncheng.qgdalumni.common.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.control.activty.MainActivity;
import com.xiangjuncheng.qgdalumni.model.bean.ChatEntity;

import java.util.List;

/**
 * Created by xiangjuncheng on 2016/2/22.
 */
public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<ChatEntity> list;
    private int type;
    LayoutInflater inflater;

    public ChatAdapter(Context context,List<ChatEntity> list,int type){
        this.context = context;
        this.list = list;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup root) {
        TextView content;
        TextView name;
        ChatEntity ce=list.get(position);
        if(ce.getSendAccount().equals(MainActivity.me.getUsername())){
            convertView=inflater.inflate(R.layout.chat_listview_item_right, null);

            content=(TextView) convertView.findViewById(R.id.message_chat_right);

            // int id=ce.getAvatar();
            content.setText(ce.getContent());

        }else{
            convertView = inflater.inflate(R.layout.chat_listview_item_left, null);
            name=(TextView) convertView.findViewById(R.id.sendName_chat_left);
            content=(TextView) convertView.findViewById(R.id.message_chat_left);
            name.setText(ce.getSendName());
            content.setText(ce.getContent());
            if (type == 1){
                name.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
}
