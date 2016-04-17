package com.xiangjuncheng.qgdalumni.common.adpter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.model.bean.ForumEntity;

import java.util.List;

/**
 * Created by xiangjuncheng on 2016/3/2.
 */
public class ShowForumAdapter extends BaseAdapter {
    private List<ForumEntity> forumEntities;
    private LayoutInflater mInflater;
    private Bundle data;

    public ShowForumAdapter(Context context,Bundle data,List<ForumEntity> forumEntities) {
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
        this.forumEntities = forumEntities;
    }

    @Override
    public int getCount() {
        return forumEntities.size()+1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0){
            return null;
        }else {
            return forumEntities.get(position-1);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.show_thread_item, null);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.showthreadUsername);
            holder.floorNum = (TextView) convertView.findViewById(R.id.showThreadFloorNum);
            holder.postTime = (TextView) convertView.findViewById(R.id.showthreadPosttime);
            holder.msg = (TextView) convertView.findViewById(R.id.showthreadMsg);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.floorNum.setText((position + 1) + "#");
        if (position == 0){
            holder.username.setText(data.getString("name"));
            holder.postTime.setText(data.getString("time"));
            holder.msg.setText(data.getString("content"));
        }else {
            ForumEntity forumEntity = forumEntities.get(position-1);
            holder.username.setText(forumEntity.getPostName());
            holder.postTime.setText(forumEntity.getTime());
            holder.msg.setText(forumEntity.getContent());
        }
        return convertView;
    }
    private final class ViewHolder {
        public TextView username;
        public TextView floorNum;
        public TextView postTime;
        public TextView msg;
    }
}

