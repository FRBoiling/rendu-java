package pathExt;

import configuration.datamanager.DataListManager;

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
        dataPath = pathCombine(bashPath,"Data");
    }

    private String pathCombine(String path,String folder){
        Path newPath = Paths.get(path,folder);
        File file = newPath.toFile();
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
            System.out.println("创建文件夹");
        } else {
            System.out.println("文件夹已存在");
        }
        return newPath.toString();
    }
}
