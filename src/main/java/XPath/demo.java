package XPath;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

public class demo {
    public static void main(String[] args) throws IOException {
        //创建Httpclient请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建http GET请求
        String url = "http://www.ahsgh.com/ahghjtweb/web/list?strColId=20782f569264489f87995ad0773ff626&strWebSiteId=4c5fcf57602b48a0acde5a4ef3ede48d";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //请求具体能容
//                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

                /*创建DocumentBuilder*/
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                /*从文件或数据流创建一个文档*/
                Document doc = builder.parse(response.getEntity().getContent());
                System.out.println(2);
                /*构建XPath*/
                XPath xPath = XPathFactory.newInstance().newXPath();
                /*准备路径表达式，并计算它*/
                String expression = "//ul[@calss='tab01open tab01tca']/li/a/div/div";
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                NodeList nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    System.out.println(nodeList.item(i).getTextContent());
                }

//                Document document = Jsoup.parse(content);
//
//                Elements titTimes = document.getElementsByClass("tit-time");
//                //获取ul下所有的内容
//                for (Element titTime : titTimes) {
//                    String fl = titTime.getElementsByClass("fl").eq(0).text();
//                    String fr = titTime.getElementsByClass("fr").eq(0).text();
//                    System.out.println("标题：" + fl);
//                    System.out.println("时间：" + fr);
//                }

            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }
}
