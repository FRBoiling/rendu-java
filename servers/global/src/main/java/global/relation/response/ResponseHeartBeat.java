package global.relation.response;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 15:56
 */
@Slf4j
public class ResponseHeartBeat implements IResponseHandler{
    @Override
    public void onResponse(byte[] msg, AbstractSession session) throws InvalidProtocolBufferException {
        //        Session session = (Session) this.session;
        log.info("ResponseHeartBeat");
//        UserManager.getInstance().login(session, message.getLoginName());
    }
}
