package com.xiangjuncheng.qgdalumni.control.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.control.activty.MainActivity;
import com.xiangjuncheng.qgdalumni.model.manage.ManageActivity;

public class MeFragment extends Fragment implements View.OnClickListener {
	Button bt_about;
	Button bt_exit;
	TextView tv_name;
	TextView tv_year;
	TextView tv_class;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View newsLayout = inflater.inflate(R.layout.frag_me, container, false);
		tv_class = (TextView) newsLayout.findViewById(R.id.tv_class);
		tv_name = (TextView) newsLayout.findViewById(R.id.tv_name);
		tv_year = (TextView) newsLayout.findViewById(R.id.tv_year);
	//	tv_class.setText(MainActivity.me.getCollage());
		tv_name.setText(MainActivity.me.getName());
		tv_year.setText(MainActivity.me.getYear()+"届");
		bt_about = (Button) newsLayout.findViewById(R.id.bt_about);
		bt_exit = (Button) newsLayout.findViewById(R.id.bt_exit);
		bt_about.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		return newsLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.bt_about:
				Dialog alertDialog = new AlertDialog.Builder(getContext())
						.setTitle("武汉轻工大学校友录")
						.setMessage("版本：V1.0\n年级：2016届\n学院：电气与电子工程学院\n作者：向俊成")
						.create();
				alertDialog.show();
				break;
			case R.id.bt_exit:
				closeAllActivity();
				break;
		}
	}
	public void closeAllActivity(){
		if(ManageActivity.getActivity("loginActivity")!=null){
			ManageActivity.getActivity("loginActivity").finish();
		}
		getActivity().finish();
		System.exit(0);
	}
}
