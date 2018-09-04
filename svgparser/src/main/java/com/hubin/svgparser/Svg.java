package com.hubin.svgparser;

import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;

import com.hubin.svgparser.parser.DocumentUtils;
import com.hubin.svgparser.parser.SvgInfo;
import com.hubin.svgparser.parser.SvgPathParser;

import org.w3c.dom.Element;

import java.io.InputStream;

/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.svgparser
 * @文件名: Svg
 * @创建者: 胡英姿
 * @创建时间: 2018/9/3 10:21
 * @描述： Svg图像
 */
public class Svg {
    public static final String SVG_NODE = "path";//需要解析的svg图像的<path/>节点
    public static final String SVG_ATTRIBUTE = "d";//<path/>节点下的属性
    public static final String SVG_ATTRIBUTE2 = "pathData";//<path/>节点下的属性

    protected InputStream inputStream;
    /**
     * 封装所有svg解析出来的数据的bean
     */
    public SvgInfo mSvgInfo;


    public Svg(InputStream inputStream) {
        this.inputStream = inputStream;
        mSvgInfo = new SvgInfo();
        //Dom解析
        mSvgInfo.mNodeList = DocumentUtils.getNode(inputStream, SVG_NODE);
    }

    /**
     * 解析path属性下的数据 并转换未路径
     */
    public void parserPath() {
        mSvgInfo.mPathList.clear();
        mSvgInfo.mAttrsList.clear();
        //用于计算最大外接矩形
        float left = -1;
        float right = -1;
        float top = -1;
        float bottom = -1;

        for (int i = 0; i < mSvgInfo.mNodeList.getLength(); i++) {
            Element element = (Element) mSvgInfo.mNodeList.item(i);
            //得到路径数据
            String pathData = element.getAttribute(SVG_ATTRIBUTE);//得到属性值
            if (TextUtils.isEmpty(pathData)) {
                pathData = element.getAttribute(SVG_ATTRIBUTE2);//得到属性值
            }

            //得到节点属性
            SvgPathParser.PathDataNode[]  mAttrs = SvgPathParser.createNodesFromPathData(pathData);
            mSvgInfo.mAttrsList.add(mAttrs);
            //将节点解析成path并添加到集合
            Path path = new Path();
            if (mAttrs != null&&mAttrs.length!=0) {
                try {
                    //将节点下的数据转换为path
                    SvgPathParser.PathDataNode.nodesToPath(mAttrs, path);
                    //添加到路径集合
                    mSvgInfo.mPathList.add(path);
                } catch (RuntimeException e) {
                    throw new RuntimeException("Error in parsing ", e);
                }
            }

            RectF rect = new RectF();
            path.computeBounds(rect, true);
            left = left == -1 ? rect.left : Math.min(left, rect.left);
            right = right == -1 ? rect.right : Math.max(right, rect.right);
            top = top == -1 ? rect.top : Math.min(top, rect.top);
            bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);
            mSvgInfo.totalRect = new RectF(left, top, right, bottom);
        }
    }


    /**
     * 更新最大外接矩形
     * @param path
     */
    public void zoomInTotalRectF(Path path) {
        //重新计算外接矩形
        RectF rect = new RectF();
        path.computeBounds(rect,true);
        float left = rect.left < mSvgInfo.totalRect.left ? rect.left : mSvgInfo.totalRect.left;
        float right = rect.right > mSvgInfo.totalRect.right ? rect.right : mSvgInfo.totalRect.right;
        float top = rect.top < mSvgInfo.totalRect.top ? rect.top : mSvgInfo.totalRect.top;
        float bottom = rect.bottom > mSvgInfo.totalRect.bottom ? rect.bottom : mSvgInfo.totalRect.bottom;
        mSvgInfo.totalRect = new RectF(left, top, right, bottom);
    }

    /**
     * 缩小外接矩形的 底部
     * @param path
     */
    public void zoomOutTotalRectFBottom(Path path) {
        RectF rect = new RectF();
        path.computeBounds(rect,true);
        float left = rect.left < mSvgInfo.totalRect.left ? rect.left : mSvgInfo.totalRect.left;
        float top = rect.top < mSvgInfo.totalRect.top ? rect.top : mSvgInfo.totalRect.top;
        float right = rect.right > mSvgInfo.totalRect.right ? rect.right : mSvgInfo.totalRect.right;
        float bottom = rect.bottom < mSvgInfo.totalRect.bottom ? rect.bottom : mSvgInfo.totalRect.bottom;
        mSvgInfo.totalRect = new RectF(left, top, right, bottom);
    }
}
