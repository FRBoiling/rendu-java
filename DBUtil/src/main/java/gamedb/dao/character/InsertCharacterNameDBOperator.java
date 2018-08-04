package gamedb.dao.character;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.CharMapper;
import gamedb.pojo.CharPOJO;
import org.apache.ibatis.session.SqlSession;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/1 10:39
 * CTEATED BY : JIANGYUNHUI
 */

public class InsertCharacterNameDBOperator extends AbstractDBOperator {
    String charName;
    int uid;
    int count;

    public InsertCharacterNameDBOperator(String char_name,int uid){
        this.charName=char_name;
        this.uid=uid;
    }
    @Override
    public boolean execute() {
        SqlSession sqlSession=null;
        CharPOJO pojo=new CharPOJO();
//        pojo.setTableName("character_name");
//        pojo.setCharName(charName);
//        pojo
//        try{
//            sqlSession = SqlSessionFactoryUtil.openSqlSession();
//            CharMapper charMapper=sqlSession.getMapper(CharMapper.class);
//
//            count=charMapper.insertCharacter(role);
//            sqlSession.commit();
//
//            if(count>0) {
//                m_result = 1;
//            }else{
//                m_result = 0;
//            }
//        }catch (Exception ex){
//            log.error(ex.getMessage());
//            m_result=-1;
//        }finally {
//            sqlSession.close();
//            return true;
//        }
        return true;
    }
}
