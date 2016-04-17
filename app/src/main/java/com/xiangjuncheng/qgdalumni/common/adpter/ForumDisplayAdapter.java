package com.xiangjuncheng.qgdalumni.common.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.model.bean.ForumEntityList;

import java.util.List;

/**
 * Created by xiangjuncheng on 2016/3/1.
 */
public class ForumDisplayAdapter extends BaseAdapter {
    private List<ForumEntityList> forumEntities;
    private LayoutInflater mInflater;

    public ForumDisplayAdapter(Context context,List<ForumEntityList> forumEntities) {
        this.mInflater = LayoutInflater.from(context);
        this.forumEntities = forumEntities;
    }

    @Override
    public int getCount() {
        return forumEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return forumEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        ForumEntityList forumEntity = forumEntities.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.forum_display_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView
                    .findViewById(R.id.forumDisplayTitle);
//            holder.replyCnt = (TextView) convertView
//                    .findViewById(R.id.replyCnt);
            holder.userName = (TextView) convertView
                    .findViewById(R.id.postUserName);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(forumEntity.getTitle());
//        holder.replyCnt.setText(forumEntity.getRecNum());
        holder.userName.setText(forumEntity.getPostName());
        return convertView;
    }

    private final class ViewHolder {
        public TextView title;
        public TextView replyCnt;
        public TextView userName;
    }
}
