package com.xiangjuncheng.qgdalumni.control.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.User_info;
import com.xiangjuncheng.qgdalumni.common.Util.TimeUtils;
import com.xiangjuncheng.qgdalumni.common.adpter.QGDExpandableListAdapter;
import com.xiangjuncheng.qgdalumni.control.activty.ChatActivity;
import com.xiangjuncheng.qgdalumni.control.activty.MainActivity;
import com.xiangjuncheng.qgdalumni.model.bean.ChatEntity;
import com.xiangjuncheng.qgdalumni.model.bean.GroupEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;

public class GroupFragment extends Fragment {
    public static String[] chatRoom = new String[]{"全校校友"};
    View messageLayout;
    ExpandableListView expandablelistview;
    Gson gson = new Gson();
    List<GroupEntity> sameGroupEntityList;
    List<GroupEntity> allGroupEntityList;
    BmobRealTimeData data = new BmobRealTimeData();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        messageLayout = inflater.inflate(R.layout.farg_group, container, false);
        expandablelistview = (ExpandableListView) messageLayout.findViewById(R.id.expandablelistview);
        initData();
        ExpandableListAdapter adapter = new QGDExpandableListAdapter(getActivity().getApplicationContext(), sameGroupEntityList, allGroupEntityList);
        expandablelistview.setGroupIndicator(getResources().getDrawable(R.drawable.expandablelistviewselector));
        expandablelistview.setAdapter(adapter);
        //子项单击
        expandablelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView arg0, View arg1,
                                        int groupPosition, int childPosition, long arg4) {
                if (groupPosition < 2) {
                    GroupEntity temp = (GroupEntity) getChildItem(groupPosition, childPosition);
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("account", temp.getAccount());
                    intent.putExtra("nick", temp.getNick());
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("account", chatRoom[childPosition]);
                    intent.putExtra("nick", chatRoom[childPosition]);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }
                return false;
            }
        });
        return messageLayout;
    }

    private void initData() {
        sameGroupEntityList = new ArrayList<>();
        allGroupEntityList = new ArrayList<>();
        BmobQuery<User_info> query= new BmobQuery<User_info>();
        query.addWhereEqualTo("year", MainActivity.me.getYear());
        query.addWhereNotEqualTo("username",MainActivity.me.getUsername());
        query.setLimit(100);
        query.findObjects(getContext(), new FindListener<User_info>() {
            @Override
            public void onSuccess(List<User_info> list) {
                for (int i=0;i<list.size();i++) {
                    GroupEntity groupEntity = new GroupEntity();
                    groupEntity.setAccount(list.get(i).getUsername());
                    groupEntity.setNick(list.get(i).getName());
                    sameGroupEntityList.add(groupEntity);
                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                    sameGroupEntityList = gson.fromJson("", new TypeToken<List<GroupEntity>>() {
                    }.getType());
                }
        });

        BmobQuery<User_info> queryAll= new BmobQuery<User_info>();
        queryAll.addWhereNotEqualTo("username",MainActivity.me.getUsername());
        queryAll.setLimit(100);
        queryAll.findObjects(getContext(), new FindListener<User_info>() {
            @Override
            public void onSuccess(List<User_info> list) {
                for (int i=0;i<list.size();i++) {
                    GroupEntity groupEntity = new GroupEntity();
                    groupEntity.setAccount(list.get(i).getUsername());
                    groupEntity.setNick(list.get(i).getName());
                    allGroupEntityList.add(groupEntity);
                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                allGroupEntityList = gson.fromJson("", new TypeToken<List<GroupEntity>>() {
                }.getType());
            }
        });
        data.start(getContext(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                if (data.isConnected()) {
                    data.subTableUpdate("_User");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data.optInt("year") == MainActivity.me.getYear()){
                        GroupEntity groupEntity = new GroupEntity();
                        groupEntity.setAccount(data.optString("username"));
                        groupEntity.setNick(data.optString("name"));
                        sameGroupEntityList.add(groupEntity);
                    }
                    GroupEntity groupEntity = new GroupEntity();
                    groupEntity.setAccount(data.optString("username"));
                    groupEntity.setNick(data.optString("name"));
                    allGroupEntityList.add(groupEntity);
                    ExpandableListAdapter adapter = new QGDExpandableListAdapter(getActivity().getApplicationContext(), sameGroupEntityList, allGroupEntityList);
                    expandablelistview.setAdapter(adapter);
                }
            }
        });
    }

    public Object getChildItem(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return sameGroupEntityList.get(childPosition);
            case 1:
                return allGroupEntityList.get(childPosition);
            default:
                break;
        }
        return null;
    }
}
