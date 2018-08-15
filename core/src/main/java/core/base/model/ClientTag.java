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
public class ClientTag implements ISessionTag {
    private String username;
    private Integer areaId;

    public ClientTag() {
        username = "";
        areaId =0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.username,this.areaId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientTag) {
            ClientTag t = (ClientTag) obj;
            return t.username.equals( this.username) &&
                    t.areaId.equals(this.areaId) ;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String strTag = username+ "_" + areaId;
        return strTag;
    }
    //    Object [] tagParam = new Object[]{
//            id,
//            name,
//    };
    @Override
    public void initTag(Object[] params) {
        this.username = (String) params[0];
        this.areaId = (Integer) params[1];
    }

    public void setTag(String accountName,Integer areaId) {
        this.username = accountName;
        this.areaId = areaId;
    }


}
