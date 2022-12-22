package JSoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ahsgs {
    public static void main(String[] args) throws IOException {
//        /*被爬取的界面*/
//        String url = "http://www.ahsgh.com/ahghjtweb/web/list?strColId=20782f569264489f87995ad0773ff626&strWebSiteId=4c5fcf57602b48a0acde5a4ef3ede48d";
//        /*jsoup解析得到的对象*/
//        Document document = Jsoup.parse(new URL(url), 30000);
//
//        Elements titTimes = document.getElementsByClass("tit-time");
//        //获取ul下所有的内容
//        for (Element titTime : titTimes) {
//            String fl = titTime.getElementsByClass("fl").eq(0).text();
//            String fr = titTime.getElementsByClass("fr").eq(0).text();
//            System.out.println("标题：" + fl);
//            System.out.println("时间：" + fr);
//        }

        Connection connect = Jsoup.connect("http://www.ahsgh.com/ahghjtweb/web/list");
        //添加参数
        connect.data("listPage","li").data("text", "73123917441103");
        Connection.Response response = connect.method(Connection.Method.POST).ignoreContentType(true).execute();
        //获取数据，处理成html


    }
}
