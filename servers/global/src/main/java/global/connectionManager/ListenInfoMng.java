package global.connectionManager;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-18
 * Time: 17:30
 **/

@Slf4j
public class ListenInfoMng {
    private static ListenInfoMng ourInstance = new ListenInfoMng();

    public static ListenInfoMng getInstance() {
        return ourInstance;
    }

    private ListenInfoMng() {
    }

    HashMap<ServerTag,HashMap<ServerType,ServerRegister.Connect_Info>> registeredConnectInfo = new HashMap<>();

    public ServerRegister.Connect_Info getConnectInfo(ServerTag tag,ServerType type){
        HashMap<ServerType,ServerRegister.Connect_Info> connect_infoHashMap = registeredConnectInfo.get(tag);
        if (connect_infoHashMap !=null){
            return connect_infoHashMap.get(type);
        }
        return null;
    }

    public boolean setRegisteredConnectInfo(ServerTag tag,ServerType type,ServerRegister.Connect_Info info){

        if (registeredConnectInfo.containsKey(tag)) {
            registeredConnectInfo.get(tag).put(type,info);
        }else {
            HashMap<ServerType,ServerRegister.Connect_Info> connectInfo = new HashMap<>();
            connectInfo.put(type,info);
            registeredConnectInfo.put(tag,connectInfo);
        }

        return true;
    }
}
