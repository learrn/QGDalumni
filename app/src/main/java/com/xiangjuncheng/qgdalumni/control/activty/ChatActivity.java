package com.xiangjuncheng.qgdalumni.control.activty;

/**
 * Created by xiangjuncheng on 2016/2/22.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.Util.TimeUtils;
import com.xiangjuncheng.qgdalumni.common.adpter.ChatAdapter;
import com.xiangjuncheng.qgdalumni.model.bean.ChatEntity;

import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;

public class ChatActivity extends Activity{
    EditText et_input;
    private String chatContent;//消息内容
    ListView chatListView;
    public  List<ChatEntity> chatEntityList=new ArrayList<ChatEntity>();//所有聊天内容
    private String myAccount;
    private String chatAccount;
    private String chatNick;
    private int chatType;
    BmobRealTimeData data = new BmobRealTimeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        //设置top面板信息
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        chatAccount = getIntent().getStringExtra("account");
        chatNick=getIntent().getStringExtra("nick");
        chatType=getIntent().getIntExtra("type",0);
        TextView nick_tv=(TextView) findViewById(R.id.chat_top_nick);
        nick_tv.setText(chatNick);
        chatListView=(ListView) findViewById(R.id.lv_chat);
        et_input=(EditText) findViewById(R.id.myMessage);
        //发送新消息
        findViewById(R.id.send).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myAccount = MainActivity.me.getUsername();
                ObjectOutputStream oos;
                //得到输入的数据，并清空EditText
                chatContent = et_input.getText().toString();
                et_input.setText("");
                //发送消息
                ChatEntity chat = new ChatEntity();
                chat.setSendAccount(myAccount);
                chat.setSendName(MainActivity.me.getName());
                chat.setReceiveAccount(chatAccount);
                chat.setContent(chatContent);
                chat.setTime(TimeUtils.geTimeNoS());
                imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);//使软键盘在editText失去焦点时关闭。
                et_input.clearFocus();//使editText失去焦点
                chat.save(ChatActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        updateChatView(new ChatEntity(
                                chatContent,
                                TimeUtils.geTime(),
                                myAccount,
                                MainActivity.me.getName()));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(ChatActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        data.start(this, new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                if (data.isConnected()) {
                    data.subTableUpdate("ChatEntity");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (MainActivity.me.getUsername().equals(data.optString("receiveAccount"))
                            && chatAccount.equals(data.optString("sendAccount"))) {
                        updateChatView(new ChatEntity(
                                data.optString("content"),
                                TimeUtils.geTime(),
                                data.optString("sendAccount"),
                                data.optString("sendName")));
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String sql = "select * from ChatEntity where ( sendAccount = ? and receiveAccount = ? ) or ( sendAccount = ? and receiveAccount = ? )";
        BmobQuery<ChatEntity> query = new BmobQuery<ChatEntity>();
        query.setSQL(sql);
        query.setPreparedParams(new Object[]{myAccount, chatAccount, chatAccount, myAccount});
        query.doSQLQuery(this, new SQLQueryListener<ChatEntity>() {
            @Override
            public void done(BmobQueryResult<ChatEntity> bmobQueryResult, BmobException e) {
                if (e == null) {
                    chatEntityList = (List<ChatEntity>) bmobQueryResult.getResults();
                    Log.d("xjc", "success");
                } else {
                    Log.d("xjc", "error:" + e);
                }
            }
        });
        chatListView.setAdapter(new ChatAdapter(ChatActivity.this, chatEntityList, chatType));

    }

    @Override
    public void finish() {
        super.finish();
        data.unsubTableUpdate("ChatEntity");
    }

    public void updateChatView(ChatEntity chatEntity) {
        chatEntityList.add(chatEntity);
        chatListView.setAdapter(new ChatAdapter(this, chatEntityList,chatType));
        chatListView.setSelection(chatListView.getBottom());//使ListView显示最后一条
    }
    public void onBackBtnClick(View v) {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.out_righttoleft);
    }
}
