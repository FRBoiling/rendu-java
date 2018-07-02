package configuration.datamanager;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* DataListManager Tester. 
* 
* @author <Authors name> 
* @since <pre>07/02/2018</pre> 
* @version 1.0 
*/ 
public class DataListManagerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getInstance() 
* 
*/ 
@Test
public void testGetInstance() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: Parse(String fileName) 
* 
*/ 
@Test
public void testParse() throws Exception {
    DataListManager.getInstance().Parse("D:/GitHub/ServerCluster-CSharp/Bin/Data/XML/ServerConfig.xml");

    DataList dataList = DataListManager.getInstance().getDataList("ServerConfig");
} 


} 
