package configuration.datamanager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-02
 * Time: 16:40
 */
@Getter
@Slf4j
public class DataList extends AbstractMap {
    private HashMap<Integer , Data> dataListById;
    private HashMap<String , Data> dataListByName;
    /**
     * 数据标识Key
     */
    private String Key;

    public DataList() {
        dataListById = new HashMap<>();
        dataListByName = new HashMap<>();
    }

    public void init(String id) {
        this.Key = id;
    }

    @Override
    public Set<Entry<Integer, Data>> entrySet() {
        return dataListById.entrySet();
    }


    public boolean addData(Data data) {
        if (dataListById.containsKey(data.getId()))
        {
            log.error("IdSpace {} has duplicated id {}",getKey(),data.getId());
            return false;
        }else if (data.getName()!=null && dataListByName.containsKey(data.getName())){
            log.error("IdSpace {} has duplicated name {}",getKey(),data.getName());
            return false;
        }

        data.setRootElement(this);

        return false;
    }
}
