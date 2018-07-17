package core.base.sequence;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.network.codec.Packet;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-04
 * Time: 15:33
 */

public interface IResponseHandler{
       void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException;
}
