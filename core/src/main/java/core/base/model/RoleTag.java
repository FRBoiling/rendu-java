package core.base.model;

import java.util.Objects;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-03
 * Time: 14:43
 **/
public class RoleTag {
    private int id;
    private String name;
    String strTag;

    public RoleTag() {
        id = 0;
        name ="";
        strTag ="";
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoleTag) {
            RoleTag t = (RoleTag) obj;
            return this.id == t.id &&
                    Objects.equals(this.name, t.name);
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return strTag;
    }

//    Object [] tagParam = new Object[]{
//            id,
//            name,
//    };

    public void initTag(Object[] params) {
        this.id = (int)params[0];
        this.name = (String) params[1];

        this.strTag = this.strTag+ "_" + this.id+"_" + this.name;
    }

    public void setTag(int id,String name) {
        this.id = id;
        this.name = name;
        this.strTag = this.strTag+ "_" + this.id+"_" + this.name;
    }

}
