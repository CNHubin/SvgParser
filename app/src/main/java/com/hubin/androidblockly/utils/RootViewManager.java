package com.hubin.androidblockly.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.androidblockly.utils
 * @文件名: RootViewManager
 * @创建者: 胡英姿
 * @创建时间: 2018/9/4 10:16
 * @描述： 添加窗体视图 工具
 */
public class RootViewManager {
    private LinearLayout mGroupView;
    private WeakReference<Activity> mActivityWeak;

    /**
     * 构造 给activity窗体添加一层ViewGroup
     *
     * @param activity
     */
    public RootViewManager(Activity activity) {
        mActivityWeak = new WeakReference<>(activity);
        ViewGroup rootView = (ViewGroup) mActivityWeak.get().getWindow().getDecorView();
        mGroupView = new LinearLayout(mActivityWeak.get());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mGroupView.setLayoutParams(lp);
        mGroupView.setId(Integer.MAX_VALUE);
        mGroupView.setBackgroundResource(android.R.color.transparent);
        rootView.addView(mGroupView);
    }


    /**
     * 在mGroupView中添加一个view
     * @param view
     */
    public void addView(View view) {
        if (mGroupView != null) {
            mGroupView.addView(view);
        }
    }


    /**
     * 移除所有的视图
     */
    public void removeView() {
        if (mGroupView != null) {
            mGroupView.removeAllViews();//移除视图
        }
    }
}
