package XPath;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class XPathTest {
    private static Document doc;
    private static XPath xpath;

    public static void main(String[] args) throws Exception {
        // 初始化Document、XPath对象
        init();
        // 获取根元素
        getRootEle(); // rss--------null
        // 获取子元素并打印
        getChildEles(); // title language item item
        
        // start 不是很重要
        getPartEles();
        haveChildsEles();
        getLevelEles();
        getAttrEles();
        // end 不是很重要
        
        // 任意位置的title: title->Java Tutorials and Examples 2->null 任意位置的title: title->Java Tutorials 2->null 任意位置的title: title->Java Examples 2->null 任意位置的title: title->Harry Potter->null 任意位置的title: title->Learning XML->null 任意位置的title: title->t->null 任意位置的title: title->tt->null 
        NodeList nodeList1 = (NodeList)xpath.evaluate("//title", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList1.getLength(); i++) {
            System.out.print("任意位置的title: " + nodeList1.item(i).getNodeName() + "->" + nodeList1.item(i).getTextContent()
                + "->" + nodeList1.item(i).getNodeValue() + " ");
        }
        System.out.println();

        // a下面的title: title->tt->null
        NodeList nodeList2 = (NodeList)xpath.evaluate("/rss/a/title", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList2.getLength(); i++) {
            System.out.print("a下面的title: " + nodeList2.item(i).getNodeName() + "->" + nodeList2.item(i).getTextContent()
                    + "->" + nodeList2.item(i).getNodeValue() + " ");
        }
        System.out.println();

        // a下面的任意位置的title: title->t->null a下面的任意位置的title: title->tt->null 
        NodeList nodeList22 = (NodeList)xpath.evaluate("/rss/a//title", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList22.getLength(); i++) {
            System.out.print("a下面的任意位置的title: " + nodeList22.item(i).getNodeName() + "->" + nodeList22.item(i).getTextContent()
                    + "->" + nodeList22.item(i).getNodeValue() + " ");
        }
        System.out.println();

        NodeList nodeList3 = (NodeList)xpath.evaluate("/rss/channel//title", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList3.getLength(); i++) {
            System.out.print("channel下的任意位置的title: " + nodeList3.item(i).getNodeName() + "->" + nodeList3.item(i).getTextContent()
                    + "->" + nodeList3.item(i).getNodeValue() + " ");
        }
        System.out.println();

        System.out.println("打印根节点下的所有元素节点");
        System.out.println("有子元素的 节点的数量" + doc.getDocumentElement().getChildNodes().getLength());
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.print(nodeList.item(i).getNodeName() + " ");
            }
        }
    }

    /**
     * 初始化Document、XPath对象
     *
     * @throws Exception
     */
    public static void init() throws Exception {
        // 创建Document对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(new FileInputStream(new File(
            "D:\\tools\\IDEA\\idea-project\\springboot\\demo\\spring-boot-demo-mybaits-plus\\src\\main\\resources\\demo.xml")));

        // 创建XPath对象
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
    }

    // 获取根元素
    // 表达式可以更换为/*,/rss
    public static void getRootEle() throws XPathExpressionException {
        Node node = (Node)xpath.evaluate("/rss", doc, XPathConstants.NODE);
        System.out.println(node.getNodeName() + "--------" + node.getNodeValue());
    }

    // 获取子元素并打印
    public static void getChildEles() throws XPathExpressionException {
        System.out.println("获取子元素并打印");
        NodeList nodeList = (NodeList)xpath.evaluate("/rss/channel/*", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + " ");
        }
        System.out.println();
    }

    // 获取部分元素
    // 只获取元素名称为title的元素
    public static void getPartEles() throws XPathExpressionException {
        System.out.println("只获取元素名称为title的元素");
        NodeList nodeList = (NodeList)xpath.evaluate("//*[name() = 'title']", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->" + nodeList.item(i).getTextContent());
        }
        System.out.println();
    }

    // 获取包含子节点的元素
    public static void haveChildsEles() throws XPathExpressionException {
        System.out.println("获取包含子节点的元素");
        NodeList nodeList = (NodeList)xpath.evaluate("//*[*]", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + " ");
        }
        System.out.println();
    }

    // 获取指定层级的元素
    public static void getLevelEles() throws XPathExpressionException {
        System.out.println("获取指定层级的元素");
        NodeList nodeList = (NodeList)xpath.evaluate("/*/*/*/*", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->" + nodeList.item(i).getTextContent() + " ");
        }
        System.out.println("-----------------------------");
    }

    // 获取指定属性的元素
    // 获取所有大于指定价格的书箱
    public static void getAttrEles() throws XPathExpressionException {
        System.out.println("获取指定属性的元素");
        NodeList nodeList =
            (NodeList)xpath.evaluate("//bookstore/book[price>35.00]/title", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->" + nodeList.item(i).getTextContent() + " ");
        }
        System.out.println();
    }
}
