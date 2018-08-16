package gate.manager;

import constant.SystemConst;
import core.base.serviceframe.AbstractConnector;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class ManagerConnector extends AbstractConnector {
    @Override
    public void init(String[] args) {
        IOGroupCount = 1;

        sessionMng = ManagerServerSessionMng.getInstance();

        setName(getClass().getSimpleName());
    }
}
