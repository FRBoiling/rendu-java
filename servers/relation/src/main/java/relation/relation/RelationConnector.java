package relation.relation;

import constant.SystemConst;
import core.base.serviceframe.AbstractClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class RelationConnector extends AbstractClient {
    @Override
    public void init(String[] args) {
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;
        responseMng = new RelationServerResponseMng();
        sessionMng = RelationServerSessionMng.getInstance();
    }
}
