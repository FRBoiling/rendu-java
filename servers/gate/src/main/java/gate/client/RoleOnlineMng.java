package gate.client;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 10:17
 **/
public class RoleOnlineMng {
    private static RoleOnlineMng ourInstance = new RoleOnlineMng();

    public static RoleOnlineMng getInstance() {
        return ourInstance;
    }

    private RoleOnlineMng() {
    }
}
