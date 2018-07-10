package gate.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Administrator
 * Date: 2018-07-07
 * Time: 17:35
 **/
@Slf4j
public class ResponseConnectCommand implements IResponseHandler {
    @Override
    public void onResponse(byte[] msg, AbstractSession session) throws InvalidProtocolBufferException {
        log.info("recv connect info !");
    }
}
