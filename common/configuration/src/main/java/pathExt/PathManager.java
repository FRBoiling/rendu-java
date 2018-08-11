package pathExt;

import configuration.dataManager.DataListManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-04
 * Time: 21:26
 */
@Slf4j
public class PathManager {
    private static volatile PathManager INSTANCE;
    public static PathManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataListManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PathManager();
                }
            }
        }
        return INSTANCE;
    }

    private String bashPath;
    private String dataPath;
    private String serverPath;

    public void initPath(String path){
        bashPath = path;
        if (bashPath.contains("Bin")){
            dataPath = pathCombine(bashPath,"Data");
            serverPath = pathCombine(bashPath,"Server");
        }else {
            dataPath = pathCombine(bashPath,"Bin","Data");
            serverPath = pathCombine(bashPath,"Bin","Server");
        }
    }

    public void initPath(){
        bashPath = getBashPath();
        initPath(bashPath);
    }

    /**
     *
     * @param path
     * @param folder
     * @return
     */
    private static String pathCombine(String path,String... folder){
        Path newPath = Paths.get(path,folder);
        File file = newPath.toFile();
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
            log.info("创建文件夹{}",file.toString());
        }
        return newPath.toString();
    }


    private String getBashPath()
    {
        File file = new File(System.getProperty("user.dir"));
        bashPath = file.getParent();
        log.info("bashPath:"+bashPath);
        return bashPath;
    }

    public String getXmlPath()
    {
        return pathCombine(dataPath,"XML");
    }

    public String getLogPath()
    {
        return pathCombine(dataPath,"LOG");
    }

    public String getDBPath()
    {
        return pathCombine(dataPath,"DB");
    }
}
