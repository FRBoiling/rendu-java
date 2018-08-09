import org.junit.Test;

public class TestDruid {
    @Test
    public void test1() {
        ClassLoader tempLoader=TestDruid.class.getClassLoader();
        try {
            Class.forName("test.java.TestDruid");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
