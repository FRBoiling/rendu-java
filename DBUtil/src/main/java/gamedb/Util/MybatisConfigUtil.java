package gamedb.Util;

import gamedb.DBManager;
import gamedb.dao.CheckDBOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class MybatisConfigUtil {

    private static SqlSessionFactory sqlSessionFactory=null;

    private static SqlSessionFactory universalSqlSessionFactory=null;

    public static void InitWithFile(File file){
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

        if(universalSqlSessionFactory==null){
            try{
                inputStream=new FileInputStream(file);
            }catch (IOException ex){
                Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE,null,ex);
            }

            universalSqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream,"universalEnv");
        }

    }

    public static SqlSession openSqlSession(){

        if(sqlSessionFactory==null){
            return null;
        }
        return sqlSessionFactory.openSession();
    }

    public static SqlSession openUniversalSqlSession(){
        if(universalSqlSessionFactory==null){
            return null;
        }
        return universalSqlSessionFactory.openSession();
    }

    public static void checkConnections(DBManager dbManager) {
        CheckDBOperator operator1=new CheckDBOperator(openSqlSession());
        operator1.Init(dbManager);
        operator1.execute();
        operator1.PostUpdate();

        CheckDBOperator operator2=new CheckDBOperator(openUniversalSqlSession());
        operator2.Init(dbManager);
        operator2.execute();
        operator2.PostUpdate();

        log.info("checking connection with CheckDBOperator");
    }

}
