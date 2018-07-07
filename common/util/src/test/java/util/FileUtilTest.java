package util; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** 
* FileUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>$Today</pre> 
* @version 1.0 
*/ 
public class FileUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findFiles(String baseDirName, String targetFileName, List fileList) 
* 
*/ 
@Test
public void testFindFiles() throws Exception { 
//TODO: Test goes here...
    List<File> list = new ArrayList<>();
    FileUtil.findFiles("D:/ProjectA/Bin/Data","*.xml",list);
    for (Object obj: list){
        File f = (File)obj;
        System.out.println(f.toString());
    }
} 

/** 
* 
* Method: widcardMatch(String targetFileName, String tempName) 
* 
*/ 
@Test
public void testWidcardMatch() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = FileUtil.getClass().getMethod("widcardMatch", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
