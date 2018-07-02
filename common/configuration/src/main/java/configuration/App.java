package configuration;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.print.Book;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SAXReader sr = new SAXReader();
        Document document = null;
        try {
            document = sr.read(new File("D:/GitHub/ServerCluster-CSharp/Bin/Data/XML/ServerConfig.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        List<Book> bookList = new ArrayList();
        for (Element e : elementList) {
            Book book = new Book();
//            int a = e.attributeValue("GatePort");
//            book.setTitle(e.elementText("title"));
//            book.setAuthor(e.elementText("author"));
//            book.setYear(e.elementText("year"));
//            book.setPrice(e.elementText("price"));
//            book.setCategory(e.attributeValue("category"));
            bookList.add(book);
        }

//        for (Book book : bookList) {
//            System.out.println("title:"+book.getTitle()+"\t category:"+book.getCategory()+"\t author:"+book.getAuthor()+"\t year:"+book.getYear()+"\t price:"+book.getPrice());
//        }

    }
}
