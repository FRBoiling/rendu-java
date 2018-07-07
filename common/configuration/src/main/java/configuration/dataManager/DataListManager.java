package configuration.dataManager;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-02
 * Time: 15:07
 */

public class DataListManager {
    private static volatile DataListManager INSTANCE;
    private HashMap<String,DataList> dateListMap;
    private DataParser parser;

    private DataListManager(){
        dateListMap = new HashMap<>();
        parser = new DataParser();
    }

    public static DataListManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataListManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataListManager();
                }
            }
        }
        return INSTANCE;
    }

    public boolean Parse(String fileName)
    {
        DataList dateList = parser.Parse(fileName);
        if ( dateList.isEmpty()) {
            return false;
        }else if (dateListMap.containsKey(dateList.getKey())) {
        }else {
            dateListMap.put(dateList.getKey(),dateList);
        }
        return true;
    }

    public DataList getDataList(String key) {
        return dateListMap.get(key);
    }

}
