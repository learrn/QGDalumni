package com.xiangjuncheng.qgdalumni.control.activty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.control.fragment.MeFragment;
import com.xiangjuncheng.qgdalumni.control.fragment.ForumFragment;
import com.xiangjuncheng.qgdalumni.control.fragment.GroupFragment;
import com.xiangjuncheng.qgdalumni.control.fragment.MainFragment;
import com.xiangjuncheng.qgdalumni.model.bean.User_info;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static User_info me;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView title;
    private ImageButton bt_user;
    WindowManager.LayoutParams lp;

    private LinearLayout mTabBtnMain;
    private LinearLayout mTabBtnChatRoom;
    private LinearLayout mTabBtnAddGroup;
    private LinearLayout mTabBtnForum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIM.init(this);
        setContentView(R.layout.activity_main);
        //User_info user = BmobUser.getCurrentUser(this, User_info.class);
        BmobIM.connect(me.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Log.i("Main","connect success");
                } else {
                    Log.e("Main",e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });

     //   me = ParseInfoUtils.Parse(LoginActivity.myInfo);
        initView();
        initDate();
        lp = MainActivity.this.getWindow().getAttributes();
    }
    private void initView() {
        View topBar = findViewById(R.id.top_bar);
        title = (TextView) topBar.findViewById(R.id.title);
//        bt_user = (ImageButton) topBar.findViewById(R.id.bt_user);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabBtnMain = (LinearLayout) findViewById(R.id.id_tab_bottom_main);
        mTabBtnChatRoom = (LinearLayout) findViewById(R.id.id_tab_bottom_chat_room);
        mTabBtnAddGroup = (LinearLayout) findViewById(R.id.id_tab_bottom_group);
        mTabBtnForum = (LinearLayout) findViewById(R.id.id_tab_bottom_forum);

//        bt_user.setOnClickListener(this);
        mTabBtnMain.setOnClickListener(this);
        mTabBtnChatRoom.setOnClickListener(this);
        mTabBtnAddGroup.setOnClickListener(this);
        mTabBtnForum.setOnClickListener(this);

        MainFragment mainFragment = new MainFragment();
        GroupFragment groupFragment = new GroupFragment();
        MeFragment chatRoomFragment = new MeFragment();
        ForumFragment forumFragment = new ForumFragment();

        mFragments.add(mainFragment);
        mFragments.add(groupFragment);
        mFragments.add(forumFragment);
        mFragments.add(chatRoomFragment);
    }

    private void initDate() {

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetTabBtn();
                setTabSelection(position);
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        /**
         * test *
         **/
        //test();
    }

    protected void resetTabBtn() {
        ((ImageButton) mTabBtnMain.findViewById(R.id.btn_tab_bottom_main))
                .setImageResource(R.drawable.tab_home_nor);
        ((ImageButton) mTabBtnChatRoom.findViewById(R.id.btn_tab_bottom_chat_room))
                .setImageResource(R.drawable.tab_me_nor);
        ((ImageButton) mTabBtnAddGroup.findViewById(R.id.btn_tab_bottom_group))
                .setImageResource(R.drawable.tab_group_nor);
        ((ImageButton) mTabBtnForum.findViewById(R.id.btn_tab_bottom_forum))
                .setImageResource(R.drawable.tab_forum_nor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_bottom_main:
                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_group:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_forum:
                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_chat_room:
                setTabSelection(3);
                break;

//            case R.id.bt_user:
//                break;
            default:
                break;
        }
    }


    private void setTabSelection(int index) {
        // 重置按钮
        resetTabBtn();
        mViewPager.setCurrentItem(index);
        switch (index) {
            case 0:
                ((ImageButton) mTabBtnMain.findViewById(R.id.btn_tab_bottom_main))
                        .setImageResource(R.drawable.tab_home_press);
                title.setText(R.string.main);
                break;
            case 1:
                ((ImageButton) mTabBtnAddGroup.findViewById(R.id.btn_tab_bottom_group))
                        .setImageResource(R.drawable.tab_group_press);
                title.setText(R.string.group);
                break;
            case 2:
                ((ImageButton) mTabBtnForum.findViewById(R.id.btn_tab_bottom_forum))
                        .setImageResource(R.drawable.tab_forum_press);
                title.setText(R.string.forum);
                break;
            case 3:
                ((ImageButton) mTabBtnChatRoom.findViewById(R.id.btn_tab_bottom_chat_room))
                        .setImageResource(R.drawable.tab_me_press);
                title.setText(R.string.me);
                break;

        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        User_info i = new User_info();
//        i.setAccount("1");
//        i.setName("向俊成");
//        i.setYear(2016);
//        i.setCollage("电气与电子工程学院");
//        MainActivity.me=i;
//    }
}

