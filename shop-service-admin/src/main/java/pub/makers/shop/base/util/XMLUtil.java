package pub.makers.shop.base.util;

import org.dom4j.DocumentHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.u8.vo.U8StockVo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by dy on 2017/6/24.
 */
public class XMLUtil {
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws org.jdom.JDOMException
     * @throws java.io.IOException
     */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XMLUtil.getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    public static String doXMLParseForU8(String strxml) throws JDOMException, IOException {
//        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            ValidateUtils.notNull(strxml, "10101", "U8返回为空");
            return null;
        }
        String res ="";

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();

        List<Element> resultList = XPath.selectNodes(root, "/ufinterface/NewDataSet/depart");
        if (resultList.size() == 0) {
            ValidateUtils.isTrue(!(resultList.size() == 0), "10101", strxml);
            return null;
        }

        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XMLUtil.getChildrenText(children);
            }
            res =v;
        }
        //关闭流
        in.close();
        return res;
    }


    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 获取xml编码字符集
     * @param strxml
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
        InputStream in = HttpClientUtil.String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        in.close();
        return (String)doc.getProperty("encoding");
    }

    public static List<U8StockVo> doParseForStockList(String xml)  {
        try {
            String head = "<?xml version=\"1.0\"?><finterface roottag=\"SQLEXE\" receiver=\"999\" sender=\"u8\" proc=\"depart\" codeexchanged=\"n\" docid=\"\" maxdataitems=\"20000\" bignoreextenduserdefines=\"y\" request-roottag=\"sqlexe\"><NewDataSet>";
            String foot = "</NewDataSet></finterface>";
            org.dom4j.Document document = DocumentHelper.parseText(head+xml+foot);
            //获取根节点元素对象
            org.dom4j.Element node = document.getRootElement();
            //遍历所有的元素节点
            List<U8StockVo> packList = new ArrayList<>();
            U8StockVo u8Stock = null;
            listNodes(node, packList, u8Stock);
            return packList;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 遍历当前节点元素下面的所有(元素的)子节点
     *
     * @param node
     */
    public static List<U8StockVo> listNodes(org.dom4j.Element node, List<U8StockVo> packList, U8StockVo u8Stock) {
        if("depart".equals(node.getName())){
            u8Stock = new U8StockVo();
        }
        if("cinvcode".equals(node.getName())){
            u8Stock.setCinvcode(node.getText());
        }
        if("iquantity".equals(node.getName())){
            u8Stock.setIquantity(Double.parseDouble(node.getText()));
        }

        if(u8Stock != null && u8Stock.getIquantity()!=null){
            packList.add(u8Stock);
        }
        // 当前节点下面子节点迭代器
        Iterator<org.dom4j.Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            org.dom4j.Element e = it.next();
            // 对子节点进行遍历
            listNodes(e,packList,u8Stock);
        }
        return packList;
    }
}
