package configuration.datamanager;

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
}
