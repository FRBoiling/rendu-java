package util;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-06
 * Time: 10:32
 **/
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {
    /**
     * 从给定的文件夹出发，搜索文件夹下所有的子文件夹及文件，
     * 如果文件，则匹配，否则，进入队列
     * 队列不空，递归上述操作，否则，程序结束，返回结果
     *
     * @param baseDirName  查找的文件路径
     * @param targetFileName    需要查找的文件名
     * @param fileList  返回文件集合
     */
    public static void findFiles(String baseDirName,String targetFileName,List<File> fileList){
        String tempName = null;
        File baseDir = new File(baseDirName);
        if (!baseDir.exists()||!baseDir.isDirectory()){
            System.out.println("文件查找失败："+baseDirName+"不是目录！");
            return;
        }else {
            String[] dirList = baseDir.list();
            for (int i =0;i<dirList.length;i++){
                Path path = Paths.get(baseDirName,dirList[i]);
                File readFile = path.toFile();
//                System.out.println("--->"+readFile.toString());
                if (!readFile.isDirectory()){
                    tempName = readFile.getName();
                    if (wildcardMatch(targetFileName,tempName)){
                        fileList.add(readFile.getAbsoluteFile());
                    }
                }else if (readFile.isDirectory()){
                    findFiles(readFile.toString(),targetFileName,fileList);
                }
            }
        }
    }

    /**
     *
     * 通配符匹配
     *
     * @param p  待匹配的字符串
     * @param s  通配符模式
     * @return 匹配成功返回true 否则 false
     */
    private static boolean wildcardMatch(String p, String s) {
        int idxs = 0, idxp = 0, idxstar = -1, idxmatch = 0;
        while (idxs < s.length()) {
            // 当两个指针指向完全相同的字符时，或者p中遇到的是?时
            if (idxp < p.length() && (s.charAt(idxs) == p.charAt(idxp) || p.charAt(idxp) == '?')) {
                idxp++;
                idxs++;
                // 如果字符不同也没有?，但在p中遇到是*时，我们记录下*的位置，但不改变s的指针
            } else if (idxp < p.length() && p.charAt(idxp) == '*') {
                idxstar = idxp;
                idxp++;
                // 遇到*后，我们用idxmatch来记录*匹配到的s字符串的位置，和不用*匹配到的s字符串位置相区分
                idxmatch = idxs;
                // 如果字符不同也没有?，p指向的也不是*，但之前已经遇到*的话，我们可以从idxmatch继续匹配任意字符
            } else if (idxstar != -1) {
                // 用上一个*来匹配，那我们p的指针也应该退回至上一个*的后面
                idxp = idxstar + 1;
                // 用*匹配到的位置递增
                idxmatch++;
                // s的指针退回至用*匹配到位置
                idxs = idxmatch;
            } else {
                return false;
            }
        }
        // 因为1个*能匹配无限序列，如果p末尾有多个*，我们都要跳过
        while (idxp < p.length() && p.charAt(idxp) == '*') {
            idxp++;
        }
        // 如果p匹配完了，说明匹配成功
        return idxp == p.length();
    }

    public String getCanonicalPath(String path){
//        String aa = System.getProperty("logback.configurationFile");
        File file= new File(path);
        String strCanonicalPath="";
        try {
          return strCanonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



}
