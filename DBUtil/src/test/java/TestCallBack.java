import basicCallBack.ArgObject;
import basicCallBack.ObjectBeCalled;
import gamedb.dao.account.SelectAccountDBOperator;
import gamedb.dao.role.*;
import gamedb.pojo.RolePOJO;
import gamedb.pojo.account.AccountPOJO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TestCallBack {

    @Test
    public void callBack(){

        String name="";
        ArgObject object=new ArgObject();
        object.argName=name;
        object.argCount=1;


        SelectRoleDBOperator operator=new SelectRoleDBOperator(1);


        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {
                SelectRoleDBOperator op=(SelectRoleDBOperator)operator;
                object.argCount=2;
                object.argName="test";
                String roleName=op.getRole().getRoleName();
                System.out.println(roleName+" in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();

        System.out.println("111111111");
        log.info("{} {}",1,20000000000000L);
    }

    @Test
    public void testQueryAccount() {
        AccountPOJO pojo=new AccountPOJO();
        pojo.setUsername("root");
        pojo.setChannelName("default");
        SelectAccountDBOperator operator=new SelectAccountDBOperator(pojo);


        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {
                SelectAccountDBOperator op=(SelectAccountDBOperator)operator;
                AccountPOJO temppojo=op.account;
                log.info("result {}",op.getResult());
                log.info("pojo root default createTime {} {} {} {} {}",
                        temppojo.getCreateTime(),temppojo.getUsername(),temppojo.getRegisterId(),
                        temppojo.getChannelName(),temppojo.getPassword());
            }
        });
        operator.execute();
        operator.PostUpdate();

        System.out.println("111111111");
        log.info("{} {}",1,20000000000000L);
    }
    @Test
    public void testCreate(){

        RolePOJO role=new RolePOJO();
        role.setTableName("t_role");
        role.setUid(3);
        role.setRoleName("test3");
        CreateRoleDBOperator operator=new CreateRoleDBOperator(role);

        operator.RegistCallBack((tempoperator)->{

                CreateRoleDBOperator op=(CreateRoleDBOperator)tempoperator;
                System.out.println(" create in example Called");
        });
        operator.execute();
        operator.PostUpdate();

    }

    @Test
    public void delele(){
        RolePOJO role=new RolePOJO();
        role.setTableName("t_role");
        role.setUid(3);
        role.setRoleName("test3");

        DeleteRoleDBOperator operator=new DeleteRoleDBOperator(null,role);

        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {

                DeleteRoleDBOperator op=(DeleteRoleDBOperator)operator;
                System.out.println(" delete in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();
    }

    @Test
    public void update(){
        RolePOJO role=new RolePOJO();
        role.setTableName("t_role");
        role.setUid(2);;
        role.setRoleName("test3");

        UpdateRoleDBOperator operator=new UpdateRoleDBOperator(role);

        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {

                UpdateRoleDBOperator op=(UpdateRoleDBOperator)operator;
                System.out.println(" update in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();
    }

}
