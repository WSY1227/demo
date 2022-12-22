package XPath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class demo1 {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        /*创建DocumentBuilder*/
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        /*从文件或数据流创建一个文档*/
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version=\" 1.0 \"?> <class> </class>");
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        /*构建XPath*/
        XPath xPath = XPathFactory.newInstance().newXPath();
        /*准备路径表达式，并计算它*/
        String expression = "/class/student";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

        /*遍历节点列表*/
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

        }
    }
}
