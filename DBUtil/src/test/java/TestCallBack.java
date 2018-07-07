import basicCallBack.ArgObject;
import basicCallBack.ObjectBeCalled;
import gamedb.dao.character.*;
import gamedb.pojo.Role;
import org.junit.Test;

public class TestCallBack {

    @Test
    public void callBack(){

        String name="";
        ArgObject object=new ArgObject();
        object.argName=name;
        object.argCount=1;


        SelectCharacterDBOperator operator=new SelectCharacterDBOperator(object,1);


        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator,Object arg) {
                SelectCharacterDBOperator op=(SelectCharacterDBOperator)operator;
                ArgObject obj=(ArgObject)arg;
                obj.argCount=2;
                obj.argName="test";
                String roleName=op.getRole().getRoleName();
                System.out.println(roleName+" in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();

        System.out.println(object.argCount+" "+object.argName);
    }

    @Test
    public void testCreate(){

        Role role=new Role();
        role.setTableName("t_role");
        role.setId(3);
        role.setRoleName("test3");
        role.setNote("asfdsaf");

        CreateCharacterDBOperator operator=new CreateCharacterDBOperator(null, role);

        operator.RegistCallBack(new ObjectBeCalled(){
            @Override
            public void call(Object operator,Object arg) {

                CreateCharacterDBOperator op=(CreateCharacterDBOperator)operator;
                System.out.println(" create in example Called");
            }
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
            public void call(Object operator,Object arg) {

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
            public void call(Object operator,Object arg) {

                UpdateCharacterDBOperator op=(UpdateCharacterDBOperator)operator;
                System.out.println(" update in example Called");
            }
        });
        operator.execute();
        operator.PostUpdate();
    }

}
