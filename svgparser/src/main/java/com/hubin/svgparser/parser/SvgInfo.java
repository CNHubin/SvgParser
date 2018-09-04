package com.hubin.svgparser.parser;

import android.graphics.Path;
import android.graphics.RectF;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.svgparser.svg
 * @文件名: SvgInfo
 * @创建者: 胡英姿
 * @创建时间: 2018/9/3 14:29
 * @描述： 存储解析后的svg数据 的javabean
 */
public class SvgInfo {

    /**
     * 通过Don解析出来的path节点集合
     */
    public NodeList mNodeList;
    /**
     * 该图像的最大外接矩形
     */
    public RectF totalRect;
    /**
     * 解析后的 Path集合
     */
    public List<Path> mPathList = new ArrayList<>();

    /**
     * 通过Svg解析器解析出来的所有属性
     */
    public List<SvgPathParser.PathDataNode[]> mAttrsList = new ArrayList<>();

}
