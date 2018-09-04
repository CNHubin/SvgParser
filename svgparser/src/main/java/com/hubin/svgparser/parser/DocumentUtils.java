package com.hubin.svgparser.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @项目名： AndroidBlockly
 * @包名： com.hubin.svgparser.util
 * @文件名: DocumentUtils
 * @创建者: 胡英姿
 * @创建时间: 2018/9/3 10:41
 * @描述： dom解析 工具
 */
public class DocumentUtils {

    /**
     * 检索文件中所有指定名字的节点
     * @param inputStream
     * @param node 要检索的节点名字
     * @return
     */
    public static NodeList getNode(InputStream inputStream,String node) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //取得DocumentBuilderFactory实例
            DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
            //解析输入流 得到Document实例
            Document doc = builder.parse(inputStream);
            Element rootElement = doc.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName(node);//获取子节点的数据
            return nodeList;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
