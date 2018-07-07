package configuration.dataManager;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-02
 * Time: 17:48
 *  Format:
 * <idspace id="Config">
 * 	<class id="1" name="xml1" group="xml" Ip="127.0.0.1" PassWord="1234567890" />
 * </idspace>
 */
@Slf4j
public class DataParser{
    private String filename;

    public DataList Parse(String fileName) {
        this.filename=fileName;
        Document xmlDoc = readXmlDocument(fileName);

        Attribute rootAttribute = getIdSpaceAttribute(xmlDoc);
        String rootId  = rootAttribute.getValue();

        DataList dataList = DataListManager.getInstance().getDataList(rootId);
        if (dataList == null){
            dataList = new DataList();
            dataList.init(rootId);
        }
        List elements = getClassElements(xmlDoc.getRootElement());
        for (Object obj : elements) {
            Element e = (Element)obj;
            Data data = new Data();
            Attribute idAttr = e.attribute("id");
            if ( idAttr!=null) {
                String idString =idAttr.getValue();
                int id =  Integer.parseInt(idString);
                data.setId(id);
                e.attributes().remove(idAttr);
            }
            else{
                continue;
            }

            Attribute nameAttr = e.attribute("name");
            if (nameAttr != null){
                String name = nameAttr.getValue();
                data.setName(name);
                e.attributes().remove(nameAttr);
            }

            List attributeList = e.attributes();
            for ( Object o:attributeList){
                Attribute attr =(Attribute) o;
                Attr a = new Attr(attr.getName(),attr.getData());
                data.addAttr(a);
            }

            dataList.addData(data);
        }
        return dataList;
    }

    private Document readXmlDocument(String filePath){
        InputStream in = null;
        Document doc = null;
        // 解析xml文档内容
        try {
            SAXReader reader = new SAXReader();
            in = new FileInputStream(new File(filePath));
            doc = reader.read(in);
        } catch (Exception e) {
            log.error("XMLUtil.readXml error: "+ e);
            return null;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    private List readXml(String filePath){
        InputStream in = null;
        List elementList = null;
        // 解析xml文档内容
        try {
            SAXReader reader = new SAXReader();
            in = new FileInputStream(new File(filePath));
            Document doc = reader.read(in);

            Element root = doc.getRootElement();
            elementList = root.elements();
            log.debug("XMLUtil.readXml root name:" + root.getName());
        } catch (Exception e) {
            log.error("XMLUtil.readXml error: "+ e);
            return null;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return elementList;
    }

    public Attribute getIdSpaceAttribute(Document doc){
        Element rootEle = doc.getRootElement();
        Attribute rootAttr = rootEle.attribute("id");
        String rootName = rootAttr.getParent().getName();

         if (!rootName.equals("idspace") ){
            return null;
        }
        return rootAttr;
    }

    public List getClassElements(Element root){
        List elementList = root.elements("class");
        return elementList;
    }


}
