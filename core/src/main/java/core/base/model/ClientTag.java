package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
public class ClientTag implements ISessionTag {
    private int playerId;
    private String playerName;
    private String strTag;

    public ClientTag() {
        playerId = 0;
        return;
    }

    @Override
    public int hashCode(){
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientTag) {
            ClientTag p = (ClientTag) obj;
            return this.playerName.equals(p.playerName)&&this.playerId==p.playerId;
//            return this.type.equals(p.type)&&this.groupId==p.groupId&&this.subId==p.subId;
        } else {
            return false;
        }
    }

    @Override
    public String getKey() {
        return strTag;
    }

    //    Object [] tagParam = new Object[]{
//            serverType,
//            0,
//            0
//    };
    @Override
    public void initTag(Object[] params) {
        this.playerId = (int)params[0];
        this.playerName = (String) params[0];
//        strTag = type.name();
//        if (groupId != 0){
//            strTag = strTag+ "_" + groupId;
//        }
//        if (subId!=0){
//            strTag = strTag+ "_" + subId;
//        }
    }

    public void setTag(int playerId) {
//        this.type = type;
//        this.groupId = groupId;
//        this.subId = subId;
//        strTag = type.name();
//        if (this.groupId != 0){
//            strTag = strTag+ "_" + this.groupId;
//        }
//        if (this.subId!=0){
//            strTag = strTag+ "_" + this.subId;
//        }
    }


}
