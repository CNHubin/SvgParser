package com.hubin.androidblockly.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

import com.hubin.svgparser.Svg;
import com.hubin.svgparser.parser.SvgPathParser;
import com.hubin.util.UIUtils;

import java.io.InputStream;
import java.util.List;

/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.svgparser.svg
 * @文件名: SvgBlockFor
 * @创建者: 胡英姿
 * @创建时间: 2018/9/3 11:44
 * @描述： Scratch 编程中 for循环的块 svg图像  利用svg解析器将思量图进行解析
 */
public class SvgBlockFor extends Svg{
    private Paint mPaint;
    private int strokeWidth;

    public SvgBlockFor(InputStream inputStream) {
        super(inputStream);
        parserPath();//开始解析

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.parseColor("#FFAB19")); //画笔颜色
        mPaint.setStyle(Paint.Style.FILL); //填充
        strokeWidth = UIUtils.dp2px(1);//设置描边的宽度未1dp
    }

    /**
     * 更新图像中的某个节点的属性值  达到局部变形的效果
     * @param index 路径的索引
     * @return
     */
    public List<Path> updateNode(int index,int value) {
        //更新第index条路径下的第24个节点的值 达到拉伸效果
        SvgPathParser.PathDataNode[] mAttrs = mSvgInfo.mAttrsList.get(index);
        mAttrs[24].getParams()[0]+=value;
        //更新后的值从新转换为path
        try {
            Path path = new Path();
            SvgPathParser.PathDataNode.nodesToPath(mAttrs, path);
            mSvgInfo.mPathList.set(index,path); //更新这条path
            //重新计算外接矩形
            if (value > 0) {
                zoomInTotalRectF(path);
            } else {
                zoomOutTotalRectFBottom(path);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing ", e);
        }
        return mSvgInfo.mPathList;
    }



    /**
     * 绘制路径
     * @param canvas
     */
    public void drawPath(Canvas canvas) {
        for (Path path : mSvgInfo.mPathList) {
            canvas.drawPath(path, mPaint);
        }

        //描边
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeWidth(strokeWidth); //画笔宽度
        paint.setColor(Color.parseColor("#CF8B17")); //画笔颜色
        paint.setStyle(Paint.Style.STROKE); //空心
        for (Path path : mSvgInfo.mPathList) {
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 判断当前触摸的位置是否在图像中
     * @param x
     * @param y
     * @return
     */
    public boolean isTouch(float x, float y) {
        RectF rectF = mSvgInfo.totalRect;
        Path path = mSvgInfo.mPathList.get(0);
//        rectF   矩形  包含了Path
        Region region = new Region();
        region.setPath(path, new Region((int)rectF.left, (int)rectF.top,(int)rectF.right, (int)rectF.bottom));
        return region.contains((int)x,(int)y);
    }

    /**
     * @return
     */
    public int getStrokeWidth() {
        return strokeWidth;
    }
}
