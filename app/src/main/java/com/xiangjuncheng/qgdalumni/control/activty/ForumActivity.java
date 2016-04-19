package com.xiangjuncheng.qgdalumni.control.activty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.Util.TimeUtils;
import com.xiangjuncheng.qgdalumni.common.adpter.ForumDisplayAdapter;
import com.xiangjuncheng.qgdalumni.model.bean.ForumEntityList;
import com.xiangjuncheng.qgdalumni.model.bean.GroupEntity;
import com.xiangjuncheng.qgdalumni.model.manage.ManageActivity;
import com.xiangjuncheng.qgdalumni.view.RefreshActionBtn;
import com.xiangjuncheng.qgdalumni.view.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiangjuncheng on 2016/3/1.
 */
public class ForumActivity extends Activity implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private String myAccount;
    private XListView m_listView;
    private ForumDisplayAdapter m_adapter;
    private ProgressBar m_pBar;
    private RefreshActionBtn m_refreshBtn;
    private int forumType;
    private List<ForumEntityList> forumEntities = new ArrayList<ForumEntityList>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.forum_display_page);
        initdata();
        initView();
        ManageActivity.addActiviy("ForumActivity", this);
    }

    private void initdata() {
        myAccount = MainActivity.me.getUsername();
        forumType = getIntent().getIntExtra("forumType", 0);
        ManageActivity.addActiviy("ForumActivity", this);
    }

    private void initView() {
        m_listView = (XListView) this.findViewById(R.id.forumDisplayListView);
        m_listView.setPullLoadEnable(false);
        m_listView.setPullRefreshEnable(true);
        m_listView.setXListViewListener(this);
        m_listView.setOnItemClickListener(this);

        m_refreshBtn = (RefreshActionBtn) this.findViewById(R.id.forumDisplayRefreshBtn);
        m_pBar = (ProgressBar) this.findViewById(R.id.forumDisplayProgressBar);
        TextView tv = (TextView) this.findViewById(R.id.forumDisplayPageTitle);
        if (forumType == 1) {
            tv.setText("灌水闲聊");
        } else if (forumType == 2) {
            tv.setText("职场生涯");
        }
    }

    public void onBackBtnClick(View v) {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.out_righttoleft);
    }

    public void onPageTitleClick(View v) {
        // 滑动listView到顶端
        m_listView.setSelection(0);
    }

    public void onNewThreadBtnClick(View v) {
        Intent intent = new Intent(this, NewForumPage.class);
        this.startActivityForResult(intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        BmobQuery<ForumEntityList> query = new BmobQuery<ForumEntityList>();
        query.addWhereEqualTo("type",forumType);
        query.setLimit(100);
        query.findObjects(this, new FindListener<ForumEntityList>() {
            @Override
            public void onSuccess(List<ForumEntityList> list) {
                forumEntities = list;
                Log.d("xjc","success");
                Log.d("xjc",forumEntities.size()+"");
                m_adapter = new ForumDisplayAdapter(ForumActivity.this, forumEntities);
                m_listView.setAdapter(m_adapter);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ForumActivity.this,"加载失败", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                forumEntities = gson.fromJson("", new TypeToken<List<GroupEntity>>() {
                }.getType());
            }
        });
        m_adapter = new ForumDisplayAdapter(ForumActivity.this, forumEntities);
        Log.d("xjc","refresh");
        m_listView.setAdapter(m_adapter);
        m_pBar.setVisibility(View.GONE);

        if (m_refreshBtn.isRefreshing()) {
            m_refreshBtn.endRefresh();
        }
        if (m_listView.getPullRefreshing()) {
            m_listView.stopRefresh();
        }
    }


    public void onRefreshBtnClick(View v) {
        Log.d("xjc","onRefreshBTnClick");
        if (m_refreshBtn.isRefreshing())
            return;
        m_refreshBtn.startRefresh();
        refresh();
    }

    @Override
    public void onRefresh() {
        m_refreshBtn.startRefresh();
        Log.d("xjc","onRefresh");
        refresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        // 过滤header被点击的情况
        if (position < 1)
            return;

        ForumEntityList forumEntity = forumEntities.get(position - 1);
        Bundle data = new Bundle();
        // listview有header,position做减一处理
        data.putInt("id", forumEntity.getId());
        data.putString("name", forumEntity.getPostName());
        data.putString("time", forumEntity.getTime());
        data.putString("title", forumEntity.getTitle());
        data.putString("content", forumEntity.getContent());
        Intent intent = new Intent(this, ShowForumPage.class);
        intent.putExtras(data);
        this.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                Bundle bunde = data.getExtras();
                ForumEntityList forumEntityList = new ForumEntityList();
                forumEntityList.setTitle(bunde.getString("title"));
                forumEntityList.setContent(bunde.getString("content"));
                forumEntityList.setTime(TimeUtils.geTimeNoS());
                forumEntityList.setPostName(MainActivity.me.getName());
                forumEntityList.setPostAccount(myAccount);
                forumEntityList.setType(forumType);
                forumEntityList.save(ForumActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ForumActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(ForumActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
                    }
                });
                refresh();
                break;
            default:
                break;
        }
    }




}
