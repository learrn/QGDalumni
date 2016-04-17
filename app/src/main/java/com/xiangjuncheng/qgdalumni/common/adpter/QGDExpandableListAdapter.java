package com.xiangjuncheng.qgdalumni.common.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.control.fragment.GroupFragment;
import com.xiangjuncheng.qgdalumni.model.bean.GroupEntity;

import java.util.List;

/**
 * Created by xiangjuncheng on 2016/2/22.
 */
public class QGDExpandableListAdapter extends BaseExpandableListAdapter {
    private String[] group;
    private Context context;
    private List<GroupEntity> sameList;
    private List<GroupEntity> allList;
    LayoutInflater inflater;

    public QGDExpandableListAdapter(Context context, List<GroupEntity> samelist, List<GroupEntity> alllist) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.sameList = samelist;
        this.allList = alllist;
        group = context.getResources().getStringArray(R.array.frag_group_groups_name);

    }


    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView,
                             ViewGroup arg4) {
        String childName = "";
        convertView = inflater.inflate(R.layout.frag_childs, null);
        TextView tv_childName = (TextView) convertView.findViewById(R.id.child_name);
        TextView tv_childTrends = (TextView) convertView.findViewById(R.id.child_trends);

        switch (groupPosition){
            case 0:
                GroupEntity sameGroupEntity = sameList.get(childPosition);
                childName = sameGroupEntity.getNick();
                break;
            case 1:
                GroupEntity allGroupEntity = allList.get(childPosition);
                childName = allGroupEntity.getNick();
                break;
            case 2:
                childName = GroupFragment.chatRoom[childPosition];
                break;
            default:
                break;
        }
            tv_childName.setText(childName);
            tv_childTrends.setVisibility(View.GONE);
        return convertView;
    }

    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        switch (groupPosition){
            case 0:
                return sameList.size();
            case 1:
                return allList.size();
            case 2:
                return GroupFragment.chatRoom.length;
            default:
                return 0;
        }
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup arg3) {
        convertView = inflater.inflate(R.layout.frag_groups, null);
        TextView groupNameTextView = (TextView) convertView.findViewById(R.id.buddy_listview_group_name);
        groupNameTextView.setText(getGroup(groupPosition).toString());
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    // 子选项是否可以选择
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }
}
