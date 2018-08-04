package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
public class ServerTag implements ISessionTag {
    private int groupId;
    private int subId;
    private ServerType type;
    private String strTag;

    public ServerTag() {
        groupId = 0;
        subId = 0;
        type = ServerType.Default;
        strTag="";
        return;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.type,this.groupId,this.subId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerTag) {
            ServerTag t = (ServerTag) obj;
            return Objects.equals(this.type, t.type) &&
                    this.groupId == t.groupId &&
                    this.subId == t.subId;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return strTag;
    }

    //    Object [] tagParam = new Object[]{
//            serverType,
//            0,
//            0
//    };
    @Override
    public void initTag(Object[] params) {
        this.type = (ServerType) params[0];
        this.groupId = (int) params[1];
        this.subId = (int) params[2];
        strTag = type.name();
        if (groupId != 0){
            strTag = strTag+ "_" + groupId;
        }
        if (subId!=0){
            strTag = strTag+ "_" + subId;
        }
    }

    public void setTag(ServerType type, int groupId, int subId) {
        this.type = type;
        this.groupId = groupId;
        this.subId = subId;
        strTag = type.name();
        if (this.groupId != 0){
            strTag = strTag+ "_" + this.groupId;
        }
        if (this.subId!=0){
            strTag = strTag+ "_" + this.subId;
        }
    }

}
