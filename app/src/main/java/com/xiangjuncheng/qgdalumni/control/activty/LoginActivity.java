package com.xiangjuncheng.qgdalumni.control.activty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.User_info;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiangjuncheng on 2016/1/13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    public static String myInfo;
    private EditText user_input;
    private EditText password_input;
    private Button log_button;
    private Button reg_button;
    private CheckBox remember;
    private SharedPreferences sp;
    private String userNameValue;
    private String passwordValue;
    private ProgressBar m_pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myInfo = "";
        initControl();//实例化对象
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            remember.setChecked(true);
            user_input.setText(sp.getString("USER_NAME", ""));
            password_input.setText(sp.getString("PASSWORD", ""));
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).apply();
                } else {
                    sp.edit().putBoolean("ISCHECK", false).apply();
                }
            }
        });

        log_button.setOnClickListener(this);
        reg_button.setOnClickListener(this);
    }

    private void initControl() {

        //获得实例对象
        user_input = (EditText) findViewById(R.id.user_input);
        password_input = (EditText) findViewById(R.id.password_input);
        log_button = (Button) findViewById(R.id.log_button);
        reg_button = (Button) findViewById(R.id.register_button);
        remember = (CheckBox) findViewById(R.id.remember);
        m_pBar = (ProgressBar) findViewById(R.id.registerProgressBar);
        sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_button:
                userNameValue = user_input.getText().toString();
                passwordValue = password_input.getText().toString();
                if (userNameValue.isEmpty()||passwordValue.isEmpty()) {
                    Toast.makeText(this, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                m_pBar.setVisibility(View.VISIBLE);
                User_info user =  new User_info();
                user.setUsername(userNameValue);
                user.setPassword(passwordValue);
                user.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        m_pBar.setVisibility(View.GONE);
                        //登录成功和记住密码框为选中状态才保存用户信息
                        if (remember.isChecked()) {
                            //记住用户名、密码、
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_NAME", userNameValue);
                            editor.putString("PASSWORD", passwordValue);
                            editor.apply();
                        }
                        MainActivity.me = BmobUser.getCurrentUser(LoginActivity.this, User_info.class);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(LoginActivity.this, "账号或密码错误  请重新登陆", Toast.LENGTH_LONG).show();
                        m_pBar.setVisibility(View.GONE);
                    }
                });

                break;
            case R.id.register_button:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
    }

}
