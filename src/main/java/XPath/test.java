package XPath;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://www.baidu.com/");
        HttpResponse response = client.execute(request);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(response.getEntity().getContent());
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        System.out.println(1);
        NodeList nodeList = (NodeList) xPath.evaluate("//ul", document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(2);
            System.out.println(nodeList.item(i).getTextContent());
        }

    }
}
