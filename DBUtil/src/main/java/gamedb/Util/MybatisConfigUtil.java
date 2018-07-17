package gamedb.Util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MybatisConfigUtil {

    static SqlSessionFactory sqlSessionFactory=null;

    public static SqlSessionFactory InitWithFile(File file){
        //String resource = path;
        FileInputStream inputStream=null;
        if(sqlSessionFactory==null){
            try{
                inputStream=new FileInputStream(file);
            }catch (IOException ex){
                Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE,null,ex);
            }

            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSqlSession(){

        if(sqlSessionFactory==null){
            return null;
        }
        return sqlSessionFactory.openSession();
    }
}
