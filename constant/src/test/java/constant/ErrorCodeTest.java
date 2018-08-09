package constant; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* ErrorCode Tester. 
* 
* @author <Authors name> 
* @since <pre>$Today</pre> 
* @version 1.0 
*/ 
public class ErrorCodeTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getValue() 
* 
*/ 
@Test
public void testGetValue() throws Exception { 
//TODO: Test goes here...
    int i = ErrorCode.SUCCESS.ordinal();
    int value = ErrorCode.SUCCESS.getValue();
} 


} 
