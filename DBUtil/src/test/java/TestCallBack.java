import basicCallBack.ArgObject;
import basicCallBack.ObjectBeCalled;
import gamedb.dao.account.SelectAccountDBOperator;
import gamedb.dao.character.*;
import gamedb.pojo.Role;
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


        SelectCharacterDBOperator operator=new SelectCharacterDBOperator(1);


        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {
                SelectCharacterDBOperator op=(SelectCharacterDBOperator)operator;
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
        pojo.setAccountName("root");
        pojo.setChannelName("default");
        SelectAccountDBOperator operator=new SelectAccountDBOperator(pojo);


        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {
                SelectAccountDBOperator op=(SelectAccountDBOperator)operator;
                AccountPOJO temppojo=op.account;
                log.info("result {}",op.getResult());
                log.info("pojo root default createTime {} {} {} {} {}",
                        temppojo.getTimeCreated(),temppojo.getAccountName(),temppojo.getRegisterId(),
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

        Role role=new Role();
        role.setTableName("t_role");
        role.setId(3);
        role.setRoleName("test3");
        role.setNote("asfdsaf");

        CreateRoleDBOperator operator=new CreateRoleDBOperator(null, role);

        operator.RegistCallBack((tempoperator)->{

                CreateRoleDBOperator op=(CreateRoleDBOperator)tempoperator;
                System.out.println(" create in example Called");
        });
        operator.execute();
        operator.PostUpdate();

    }

    @Test
    public void delele(){
        Role role=new Role();
        role.setTableName("t_role");
        role.setId(3);
        role.setRoleName("test3");
        role.setNote("asfdsaf");

        DeleteCharacterDBOperator operator=new DeleteCharacterDBOperator(null,role);

        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {

                DeleteCharacterDBOperator op=(DeleteCharacterDBOperator)operator;
                System.out.println(" delete in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();
    }

    @Test
    public void update(){
        Role role=new Role();
        role.setTableName("t_role");
        role.setId(2);
        role.setRoleName("test3");
        role.setNote("asfdsaf");

        UpdateCharacterDBOperator operator=new UpdateCharacterDBOperator(null,role);

        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator) {

                UpdateCharacterDBOperator op=(UpdateCharacterDBOperator)operator;
                System.out.println(" update in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();
    }

}
