package core.base.model; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* ServerTag Tester. 
* 
* @author <Authors name> 
* @since <pre>$Today</pre> 
* @version 1.0 
*/ 
public class ServerTagTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: hashCode() 
* 
*/ 
@Test
public void testHashCode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: equals(Object obj) 
* 
*/ 
@Test
public void testEquals() throws Exception { 
    ServerTag tag = new ServerTag();
    tag.setTag(ServerType.Global,1001,1);
    String strTag = tag.toString();
    strTag = tag.toString();
}

/** 
* 
* Method: initTag(Object[] params) 
* 
*/ 
@Test
public void testInitTag() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setTag(ServerType type, int groupId, int subId) 
* 
*/ 
@Test
public void testSetTag() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getGroupId() 
* 
*/ 
@Test
public void testGetGroupId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSubId() 
* 
*/ 
@Test
public void testGetSubId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getType() 
* 
*/ 
@Test
public void testGetType() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getStrTag() 
* 
*/ 
@Test
public void testGetStrTag() throws Exception { 
//TODO: Test goes here... 
} 


} 
