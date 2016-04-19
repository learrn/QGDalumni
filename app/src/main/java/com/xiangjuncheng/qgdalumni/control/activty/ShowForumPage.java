package com.xiangjuncheng.qgdalumni.control.activty;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.QGDMessage;
import com.xiangjuncheng.qgdalumni.common.Util.TimeUtils;
import com.xiangjuncheng.qgdalumni.common.adpter.ShowForumAdapter;
import com.xiangjuncheng.qgdalumni.model.bean.ForumEntity;
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
public class ShowForumPage extends Activity implements XListView.IXListViewListener {
    private XListView m_listView;
    private ShowForumAdapter m_adapter;
    private int m_id = -1;
    private ProgressBar m_pBar;
    private RefreshActionBtn m_refreshBtn;
    private TextView m_titleView;
    private String m_title;
    private Bundle data;
    private String myAccount;
    public static List<ForumEntity> forumEntities = new ArrayList<ForumEntity>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_thread_page);
        initData();
        initView();
        refresh();
        ManageActivity.addActiviy("ShowForumPage", this);
    }

    private void initData() {
        data = this.getIntent().getExtras();
        m_id = data.getInt("id");
        m_title = data.getString("title");
        myAccount = MainActivity.me.getUsername();
    }

    private void initView() {
        m_listView = (XListView)this.findViewById(R.id.showthreadListview);
        m_listView.setPullLoadEnable(false);
        m_listView.setPullRefreshEnable(true);
        m_listView.setXListViewListener(this);
        m_titleView = (TextView)this.findViewById(R.id.textView1);
        m_titleView.setText(m_title);
        m_pBar = (ProgressBar)this.findViewById(R.id.showThreadProgressBar);
        m_refreshBtn = (RefreshActionBtn)this.findViewById(R.id.showThreadRefreshBtn);
    }

    @Override
    public void onRefresh() {
        m_refreshBtn.startRefresh();
        refresh();
    }

    @Override
    public void onLoadMore() {
    }

    public void onBackBtnClick(View v) {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.out_righttoleft);
    }

    public void onPageTitleClick(View v) {
        //滑动listView到顶端
        m_listView.setSelection(0);
    }

    private void refresh() {
        BmobQuery<ForumEntity> query = new BmobQuery<ForumEntity>();
        query.setLimit(100);
        query.addWhereEqualTo("id",m_id);
        query.findObjects(this, new FindListener<ForumEntity>() {
            @Override
            public void onSuccess(List<ForumEntity> list) {
                forumEntities = list;
                m_adapter = new ShowForumAdapter(ShowForumPage.this,data,forumEntities);
                m_listView.setAdapter(m_adapter);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ShowForumPage.this,"加载失败", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                forumEntities = gson.fromJson("", new TypeToken<List<GroupEntity>>() {
                }.getType());
            }
        });
        m_adapter = new ShowForumAdapter(ShowForumPage.this,data,forumEntities);
        m_listView.setAdapter(m_adapter);
        m_pBar.setVisibility(View.GONE);

        if (m_refreshBtn.isRefreshing()) {
            m_refreshBtn.endRefresh();
        }
        if (m_listView.getPullRefreshing()) {
            m_listView.stopRefresh();
        }
    }

    public void onReplyBtnClick(View v) {
        final EditText replyTextView = (EditText)this.findViewById(R.id.showThreadReplyText);
        if (replyTextView.length() == 0) {
            Toast.makeText(ShowForumPage.this, "回帖内容不能为空", Toast.LENGTH_SHORT).show();
            replyTextView.requestFocus();
            return;
        } else if (replyTextView.length() < 6) {
            Toast.makeText(ShowForumPage.this, "回帖内容长度不能小于6个字符", Toast.LENGTH_SHORT).show();
            replyTextView.requestFocus();
            return;
        }
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        String recContent=replyTextView.getText().toString();
        replyTextView.setText("");
//        //发送消息
        ForumEntity forumEntity = new ForumEntity();
        forumEntity.setContent(recContent);
        forumEntity.setPostName(MainActivity.me.getName());
        forumEntity.setPostAccount(myAccount);
        forumEntity.setId(m_id);
        forumEntity.setTime(TimeUtils.geTimeNoS());
        forumEntity.save(ShowForumPage.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ShowForumPage.this, "发表成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ShowForumPage.this, "发表失败", Toast.LENGTH_SHORT).show();
            }
        });
        imm.hideSoftInputFromWindow(replyTextView.getWindowToken(), 0);//使软键盘在editText失去焦点时关闭。
        replyTextView.clearFocus();//使editText失去焦点
        refresh();
    }

    public void onRefreshBtnClick(View v) {
        if (m_refreshBtn.isRefreshing())
            return;
        m_refreshBtn.startRefresh();
        m_listView.setSelection(0);
        m_listView.pullRefreshing();
        refresh();
    }
}
