package gamedb.dao.character;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.CharMapper;
import gamedb.pojo.CharPOJO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/1 9:41
 * CTEATED BY : JIANGYUNHUI
 */

@Slf4j
public class CreateCharacterDBOperator extends AbstractDBOperator {
    private CharPOJO role=null;
    public int id;
    public int count;

    public CreateCharacterDBOperator(CharPOJO role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            CharMapper charMapper=sqlSession.getMapper(CharMapper.class);

            count=charMapper.insertCharacter(role);
            sqlSession.commit();

            if(count>0) {
                m_result = 1;
            }else{
                m_result = 0;
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            m_result=-1;
        }finally {
            sqlSession.close();
            return true;
        }
    }
}
