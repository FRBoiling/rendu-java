package configuration.dataManager;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description: Attrbuite
 * User: Boiling
 * Date: 2018-07-02
 * Time: 20:47
 */
@Getter
public class Attr {
    public String key;
    public Object value;

    public Attr(String key,Object value)
    {
        this.key=key;
        this.value = value;
    }

    public String getString(){
        return value.toString().trim();
    }

    public Integer getInteger()
    {
        return Integer.parseInt(value.toString());
    }

    public Float getFloat()
    {
        return Float.parseFloat(value.toString());
    }

    public Boolean getBoolen()
    {
        return Boolean.parseBoolean(value.toString());
    }
}
