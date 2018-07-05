package pathExt; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.io.File;

/** 
* PathManager Tester. 
* 
* @author <Authors name> 
* @since <pre>07/04/2018</pre> 
* @version 1.0 
*/ 
public class PathManagerTest { 

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
* Method: initPath(String path) 
* 
*/ 
@Test
public void testInitPath() throws Exception { 
//TODO: Test goes here...
    File file=new File("");
    String abspath=file.getAbsolutePath();
    String filePath = System.getProperty("java.class.path");
    String pathSplit = System.getProperty("path.separator");
    PathManager.getInstance().initPath(abspath);
} 


/** 
* 
* Method: pathCombine(String path, String folder) 
* 
*/ 
@Test
public void testPathCombine() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PathManager.getClass().getMethod("pathCombine", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
