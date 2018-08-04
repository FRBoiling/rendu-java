package util;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-06
 * Time: 10:32
 **/
public class StringUtil {

    /***
     *  例子 拆分  |key1：value1|key2：value2|key3：value3|
     * @param resourceString
     * @return
     */
    public static HashMap<String, String> getProbability(String resourceString)
    {
        HashMap<String, String> getItems = new HashMap<String, String>();
        //拆开字符串
        String[] resourceList = resourceString.split("\\|" ,-1);
        for (String resourceItem:resourceList) {
            if ( !resourceItem.isEmpty()){
                String[] resource = resourceItem.split(":" ,-1);
                if (!resource[0].isEmpty() ){
                    //取出单个设置
                    getItems.put(resource[0], resource[1]);
                }
            }
        }
        return getItems;
    }

    public static String toHexString(int value){
        return "0x"+Integer.toHexString(value);
    }

}
