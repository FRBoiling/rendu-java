package util;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;

/**
 * StringUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>$Today</pre>
 */
public class StringUtilTest {

    @Before
    public void before() throws Exception {


    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getProbability(String resourceString)
     */
    @Test
    public void testGetProbability() throws Exception {
        String sour = "|key1:value1|key2:value2|key3:value3|";
        HashMap<String, String> values = StringUtil.getProbability(sour);
        int cout = values.size();
    }

    /**
     * Method: getProbability(String resourceString)
     */
    @Test
    public void testToHexString() throws Exception {
        String hexStr = StringUtil.toHexString(16711681);
    }

} 
