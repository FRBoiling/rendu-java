package core.base.sequence;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-04
 * Time: 15:33
 */

public interface IResponseHandler{
       void onResponse(byte[] msg,AbstractSession session) throws InvalidProtocolBufferException;
}
