package com.hubin.androidblockly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.hubin.Utils;
import com.hubin.androidblockly.utils.RootViewManager;
import com.hubin.androidblockly.utils.DragViewUtil;
import com.hubin.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    private BlockForView mBlockForView;
    private RootViewManager mRootViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(this,BuildConfig.DEBUG);

        mRootViewManager = new RootViewManager(this);
    }


    /**
     * 添加一个可拖动视图到窗体
     * @param view
     */
    public void test(View view) {
        if (mBlockForView != null) {
            mRootViewManager.removeView();
            mBlockForView =null;
        }
        mBlockForView = new BlockForView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtils.dp2px(150),UIUtils.dp2px(150));
        mBlockForView.setLayoutParams(layoutParams);
        mRootViewManager.addView(mBlockForView);
        DragViewUtil.drag(mBlockForView); //设置拖动
    }


    /**
     * 更新节点  拉伸
     * @param view
     */
    public void zoomIn(View view) {
        mBlockForView.zoomIn();
    }


    /**
     * 缩短
     * @param view
     */
    public void zoomOut(View view) {
        mBlockForView.zoomOut();
    }
}
