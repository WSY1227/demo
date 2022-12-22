package JSoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        //被pa取页面的url https://search.jd.com/Search?keyword=Java
        String url = "https://search.jd.com/Search?keyword=手机";
        // jsoup解析网页，返回浏览器document对象，所有在js可以使用的方法这里都能用！
        Document document = Jsoup.parse(new URL(url), 30000);  //url 最长解析时间
        Element element = document.getElementById("J_goodsList");
        // 获取J_goodsList下的所有的li元素
        Elements elements = element.getElementsByTag("li");
 
        int count=0;
        for (Element el : elements) {
            //              找到<img>标签； 找到<li>标签下的第1个<img>标签； 获取其src属性
            //String img = el.getElementsByTag("img").eq(0).attr("src");
            //             找到"p-price"类；找到<li>标签下的第1个"p-price"； 将其内容转为文字
            String price = el.getElementsByClass("p-price").eq(0).text();
            //             同上
            String name = el.getElementsByClass("p-name").eq(0).text();
 
            //System.out.println(img);
            System.out.println(price);
            System.out.println(name);
            count++;
        }
        System.out.println(count);
    }
}