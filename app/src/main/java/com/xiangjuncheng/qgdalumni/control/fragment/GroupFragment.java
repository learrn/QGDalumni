package com.xiangjuncheng.qgdalumni.control.fragment;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.User_info;
import com.xiangjuncheng.qgdalumni.common.adpter.QGDExpandableListAdapter;
import com.xiangjuncheng.qgdalumni.control.activty.ChatActivity;
import com.xiangjuncheng.qgdalumni.control.activty.MainActivity;
import com.xiangjuncheng.qgdalumni.model.bean.GroupEntity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class GroupFragment extends Fragment {
    public static String[] chatRoom = new String[]{"同级校友", "全校校友"};
    View messageLayout;
    ExpandableListView expandablelistview;
    Gson gson = new Gson();
    List<GroupEntity> sameGroupEntityList;
    List<GroupEntity> allGroupEntityList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        messageLayout = inflater.inflate(R.layout.farg_group, container, false);
        initdata();
        expandablelistview = (ExpandableListView) messageLayout.findViewById(R.id.expandablelistview);
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

    private void initdata() {
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
