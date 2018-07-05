package configuration.dataManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-02
 * Time: 15:02
 */
@Getter
@Setter
@Slf4j
public class Data {
    private int id;
    private String name;
    private DataList rootElement;

    private HashMap<String,Attr> attrHashMap;

    public Data(){
        attrHashMap = new HashMap<>();
    }

    public boolean addAttr(Attr attr) {
        if (attrHashMap.containsKey(attr.key)){
            log.error("IdSpace {} class {} has duplicated id {}",rootElement.getKey(),id,attr.key);
            return false;
        }
        attrHashMap.put(attr.key,attr);
        return true;
    }

    public Attr getAttr(String key){
        return attrHashMap.get(key);
    }

    public String getString(String key) {
        Attr attr = getAttr(key);
        if (attr == null){
            return "";
        }
        else {
            return attr.getString();
        }
    }

    public int getInteger(String key) {
        Attr attr = getAttr(key);
        if (attr == null){
            return 0;
        }
        else {
            return attr.getInteger();
        }
    }

    public Float getFloat(String key) {
        Attr attr = getAttr(key);
        if (attr == null){
            return 0f;
        }
        else {
            return attr.getFloat();
        }
    }

    public Boolean getBoolean(String key) {
        Attr attr = getAttr(key);
        if (attr == null){
            return  false;
        }else{
            return attr.getBoolen();
        }
    }
}
