package zone.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.RegisterResult;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;
import zone.ZoneService;
import zone.global.GlobalServerSessionMng;
import zone.manager.ManagerServerSessionMng;
import zone.relation.RelationServerSessionMng;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-07
 * Time: 16:42
 */
@Slf4j
public class ResponseRegisterReturn implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ServerRegister.MSG_Server_Register_Return msg = ServerRegister.MSG_Server_Register_Return.parseFrom(packet.getMsg());

        //注册反馈 MSG_Server_Register_Return
        ServerType serverType = ServerType.values()[msg.getTag().getServerType()];
        int groupId = msg.getTag().getGroupId();
        int subId = msg.getTag().getSubId();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType, groupId, subId);
        session.setTag(tag);

        if (msg.getResult() == RegisterResult.SUCCESS.ordinal()) {
            RegisterResult registerResult =RegisterResult.SUCCESS ;
            switch (serverType) {
                case Global:
                    registerResult = GlobalServerSessionMng.getInstance().register(session);
                    break;
                case Manager:
                    registerResult = ManagerServerSessionMng.getInstance().register(session);
                    break;
                case Relation:
                    registerResult = RelationServerSessionMng.getInstance().register(session);
                    break;
                default:
                    break;
            }
            switch (registerResult) {
                case SUCCESS:
                    //TODO:这里添加具体注册逻辑
                    break;
                case REPEATED_REGISTER:
                case FAIL:
                default:
                    log.error("SERIOUS ERROR: get register result from {} success ,but register here fail :{} ", tag.getStrTag(),registerResult.toString() );
                    ZoneService.context.stop();
                    break;
            }
        }else {
            log.error("SERIOUS ERROR: register result from {} fail :{}", tag.getStrTag(), RegisterResult.values()[msg.getResult()]);
            switch (serverType) {
                case Global:
                case Manager:
                case Relation:
                    ZoneService.context.stop();
                    break;
                default:
                    break;
            }
        }
    }
}
