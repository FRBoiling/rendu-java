package gate.client;

import configuration.dataManager.Data;
import configuration.dataManager.DataListManager;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-03
 * Time: 14:57
 **/
public class AuthorizationMng {
    private static volatile AuthorizationMng INSTANCE = new AuthorizationMng();

    private AuthorizationMng(){
    }

    public static AuthorizationMng getInstance() {
        return INSTANCE;
    }

    boolean checkToken = true;
    boolean checkVersion = true;
    boolean whiteOnly = false;

    /**
     * 加载配置数据
     */
    public void loadLibData(){
        Data authConfig = DataListManager.getInstance().getDataList("AuthorizationConfig").getData(1);
        checkToken = authConfig.getBoolean("CheckToken");

        //TODO:BOIL 加载 版本检查、白名单的相关数据
        checkVersion = authConfig.getBoolean("CheckVersion");
        whiteOnly = authConfig.getBoolean("WhiteOnly");
    }

    /**
     * 授权列表
     */
    HashMap<String ,Integer> authorizedList = new HashMap<>();

    public void authorize(String accountName, int token){
        authorizedList.put(accountName,token);
    }

    /**
     * 获取token
     * @param accountName
     * @return
     */
    private Integer getToken(String accountName){
        return authorizedList.get(accountName);
    }

    public boolean checkToken(String accountName,int token) {
        if (checkToken){
            if (token == getToken(accountName)){
                return true;
            }
            else {
                return  false;
            }
        }
        return true;
    }


}
