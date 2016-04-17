package com.xiangjuncheng.qgdalumni.control.activty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.model.manage.ManageActivity;

/**
 * Created by xiangjuncheng on 2016/3/1.
 */
public class NewForumPage extends Activity{
    private ProgressDialog m_pd;
    private EditText m_subject;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.new_thread_page);
        m_subject = (EditText)this.findViewById(R.id.newThreadSubject);
        ManageActivity.addActiviy("NewForumPage", this);
        //View v = this.findViewById(R.id.newThreadTopic);
        //v.setVisibility(View.VISIBLE);
    }

    public void onBackBtnClick(View v) {
        this.finish();
    }

    public void onSubmitBtnClick(View v) {
        EditText message = (EditText)this.findViewById(R.id.newThreadMessage);
        final String subjectText = m_subject.getText().toString();
        String msgText = message.getText().toString();
        if (m_subject.length() == 0) {
            Toast.makeText(NewForumPage.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            m_subject.requestFocus();
            return;
        }
        if (message.length() == 0) {
            Toast.makeText(NewForumPage.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            message.requestFocus();
            return;
        } else if (message.length() < 6) {
            Toast.makeText(NewForumPage.this, "内容长度不能小于6个字符", Toast.LENGTH_SHORT).show();
            message.requestFocus();
            return;
        }
        m_pd = ProgressDialog.show(this, null, "发送帖子内容，请稍后……", true, true);
        Bundle data = new Bundle();
        data.putString("title", subjectText);
        data.putString("content", message.getText().toString());
        Intent intent = NewForumPage.this.getIntent();
        intent.putExtras(data);
        NewForumPage.this.setResult(1, intent);
        NewForumPage.this.finish();
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.out_righttoleft);
    }
}
