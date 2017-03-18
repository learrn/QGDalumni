package com.xiangjuncheng.qgdalumni.control.activty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.model.bean.User_info;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiangjuncheng on 2015/12/29.
 * 注册页面
 */
public class RegisterActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //初始化控件
    EditText userName;
    EditText userAccount;
    EditText password;
    EditText year;
    ProgressBar m_pBar;
    Spinner collage;
    Button cancel;
    Button reg;
    String[] collages;
    String collageName;
    User_info user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        collages = getResources().getStringArray(R.array.colleges);
        initControl();//实例化控件
        //设置点击监听
        cancel.setOnClickListener(this);
        reg.setOnClickListener(this);
        collage.setOnItemSelectedListener(this);
    }

    //实例化控件
    private void initControl() {
        userName = (EditText) findViewById(R.id.username_input);
        userAccount = (EditText) findViewById(R.id.user_RegInput);
        password = (EditText) findViewById(R.id.password_regInput);
        year = (EditText) findViewById(R.id.year);
        collage = (Spinner) findViewById(R.id.spinner);
        cancel = (Button) findViewById(R.id.cancel_button);
        reg = (Button) findViewById(R.id.register_button);
        m_pBar = (ProgressBar) findViewById(R.id.registerProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                if (userName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (userAccount.getText().toString().isEmpty()) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (year.getText().toString().isEmpty()) {
                    Toast.makeText(this, "毕业年份不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    user = new User_info();
                    user.setCollage(collageName);
                    user.setName(userName.getText().toString());
                    user.setUsername(userAccount.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setYear(Integer.parseInt(year.getText().toString()));
                    m_pBar.setVisibility(View.VISIBLE);
                    user.signUp(this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            m_pBar.setVisibility(View.GONE);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            m_pBar.setVisibility(View.GONE);
                        }
                    });
                }
                break;
            case R.id.cancel_button:
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        R.anim.out_righttoleft);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        collageName = collages[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
