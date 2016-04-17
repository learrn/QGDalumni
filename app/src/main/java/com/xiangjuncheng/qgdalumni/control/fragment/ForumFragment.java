package com.xiangjuncheng.qgdalumni.control.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.control.activty.ForumActivity;


public class ForumFragment extends Fragment implements View.OnClickListener {
    ImageButton ib_work;
    ImageButton ib_talk;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View newsLayout = inflater.inflate(R.layout.frag_forum, container, false);
        ib_work = (ImageButton) newsLayout.findViewById(R.id.ib_work);
        ib_talk = (ImageButton) newsLayout.findViewById(R.id.ib_talk);
        ib_work.setOnClickListener(this);
        ib_talk.setOnClickListener(this);
        return newsLayout;
	}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ForumActivity.class);
        switch (v.getId()){
            case R.id.ib_talk:
                intent.putExtra("forumType", 1);
                startActivity(intent);
                break;
            case R.id.ib_work:
                intent.putExtra("forumType",2);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
