package com.xiangjuncheng.qgdalumni.control.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.xiangjuncheng.qgdalumni.R;
import com.xiangjuncheng.qgdalumni.common.LocalImageHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.transform.Transformer;

public class MainFragment extends Fragment {
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_main = inflater.inflate(R.layout.frag_main, container, false);

        convenientBanner = (ConvenientBanner) view_main.findViewById(R.id.convenientBanner);
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("qgd_img_" + position, R.drawable.class));
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages);
//
        return view_main;
    }
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(4000);
    }
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }
}
