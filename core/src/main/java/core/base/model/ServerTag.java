package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
@Setter
@Accessors(chain=true)
public class ServerTag implements ISessionTag {
    private int areaId;
    private int subId;
    private ServerType type;

    public ServerTag() {
        areaId = 0;
        subId = 0;
        type = ServerType.Default;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.type,this.areaId,this.subId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerTag) {
            ServerTag t = (ServerTag) obj;
            return Objects.equals(this.type, t.type) &&
                    this.areaId == t.areaId &&
                    this.subId == t.subId;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String strTag = type.name();
        if (areaId != 0){
            strTag = strTag+ "_" + areaId;
        }
        if (subId!=0){
            strTag = strTag+ "_" + subId;
        };
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
        this.areaId = (int) params[1];
        this.subId = (int) params[2];
    }

    public void setTag(ServerType type, int areaId, int subId) {
        this.type = type;
        this.areaId = areaId;
        this.subId = subId;
    }

}
