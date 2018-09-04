package com.hubin.androidblockly;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hubin.androidblockly.svg.SvgBlockFor;


/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.androidblockly
 * @文件名: BlockView
 * @创建者: 胡英姿
 * @创建时间: 2018/8/27 11:22
 * @描述： 将SvgBlockFor的矢量图 打造成 可以局部拉伸的 自定义View
 */
public class BlockForView extends View {
    private Paint mPaint;
    private float scale = 3.0f; //与svg原图的缩放比列
    private SvgBlockFor mSvgBlockFor;


    public BlockForView(Context context) {
        this(context, null);
    }

    public BlockForView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockForView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速

        //此处如果矢量图较大 解析耗时过长 需要放置到子线程执行，待完善
        mSvgBlockFor = new SvgBlockFor(getResources().openRawResource(R.raw.scrach3_for));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //父容器传过来的宽度的值
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        // 根据宽度计算缩放比例
        if (mSvgBlockFor.mSvgInfo.totalRect != null) {
            double mapWidth = mSvgBlockFor.mSvgInfo.totalRect.width() + mSvgBlockFor.getStrokeWidth(); //增加path描边宽度
            scale = (float) (width / mapWidth);
        }
        //测量高度
        height = (int) ((mSvgBlockFor.mSvgInfo.totalRect.height()+mSvgBlockFor.getStrokeWidth()) * scale+0.5f);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSvgBlockFor.mSvgInfo.mPathList == null) {
            return;
        }
        canvas.save();
        canvas.scale(scale, scale);
        //绘制
        mSvgBlockFor.drawPath(canvas);
    }
/*

    float downX ;
    float downY ;
    float dX;
    float dY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY =y;
                dX=downX;
                dY=downY;
                if (mSvgBlockFor.isTouch(downX/scale,downY/scale)) {
                    ToastUtils.toast("按下：x="+downX+" y="+downY);
                    LogUtils.e("onTouchEvent E : 按下：x="+downX+" y="+downY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                float offsetX = x - dX;
                float offsetY = y - dY;
                for (Path path : mSvgBlockFor.mSvgInfo.mPathList) {
                    path.offset(offsetX/scale,offsetY/scale);
                    mSvgBlockFor.updateTotalRectF(path);
                }
                invalidate();
                //记录坐标
                dX = x;
                dY = y;
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("onTouchEvent E : 离开！");
                break;
            default:
                break;
        }


        return true;

    }
*/

    /**
     * 放大
     */
    public void zoomIn() {
        mSvgBlockFor.updateNode(0, 24);
        resetLayout();
        invalidate();
    }


    /**
     * 缩小
     */
    public void zoomOut() {
        mSvgBlockFor.updateNode(0, -24);
        resetLayout();
        invalidate();
    }

    /**
     * 重新布局 适应高度变化
     */
    private void resetLayout() {
        layout(getLeft(),
                getTop(),
                getRight()+mSvgBlockFor.getStrokeWidth(),
                (int) ((mSvgBlockFor.mSvgInfo.totalRect.height()+mSvgBlockFor.getStrokeWidth()) * scale ) + getTop());
    }
}
