package gate.manager.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.DBOperateType;
import gamedb.DBTableParamType;
import gamedb.dao.character.CreateCharacterDBOperator;
import gamedb.dao.character.InsertCharacterNameDBOperator;
import gamedb.pojo.CharPOJO;
import gate.GateService;
import gate.client.ClientSession;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;
import protocol.manager.gate.M2G;

@Slf4j
public class ResponseMaxCharUid implements IResponseHandler {

    private MSG_GC_CREATE_CHARACTER_RESULT.Builder response= MSG_GC_CREATE_CHARACTER_RESULT.newBuilder();
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        M2G.MSG_M2G_MAX_UID msg=M2G.MSG_M2G_MAX_UID.parseFrom(packet.getMsg());
        String account=msg.getAccountName();
        String channel=msg.getChannelName();
        int result =msg.getResult();
        int maxUid=msg.getMaxUid();

        //正式开始创建角色
        createCharacter(msg,(ClientSession) session);
    }

    private void createCharacter(M2G.MSG_M2G_MAX_UID msg, ClientSession session) {

        if (session.reqCreateMsg == null)
        {
            log.warn("account {0} create character failed: ReqCreateMsg is null", msg.getAccountName());
            response.setResultCode(ErrorCode.NotCreating.getValue());
            session.sendMessage(response.build());
            return;
        }
        if (msg.getResult() !=ErrorCode.SUCCESS.getValue())
        {
            log.warn("account {} create character failed: no watch dog manager", msg.getAccountName());
            response.setResultCode(ErrorCode.ServerNotOpen.getValue());
            session.sendMessage(response.build());
            return;
        }

        CHARACTER_INFO.Builder character_info = CHARACTER_INFO.newBuilder();

        character_info.setUid(msg.getMaxUid());
        response.setCharacterInfo(character_info);

       // try to insert character name
        InsertCharacterNameDBOperator operator=new InsertCharacterNameDBOperator(msg.getAccountName(), msg.getMaxUid());
        GateService.context.db.Call(operator, "character_name", DBOperateType.Write,operator1 -> {
            InsertCharacterNameDBOperator op=(InsertCharacterNameDBOperator)operator1;
            if(op.getResult()!=1){
                //DuplicatedName
                response.setResultCode(ErrorCode.DuplicatedName.getValue());
                session.sendMessage(response.build());
            }
            else {
                //Create character
                createCharacterWithTransaction(session,response.build());
            }
        });
    }

    private void createCharacterWithTransaction(ClientSession session,MSG_GC_CREATE_CHARACTER_RESULT response) {
        CharPOJO pojo=new CharPOJO();
        String tableName=GateService.context.db.GetTableName("character",response.getCharacterInfo().getUid() ,DBTableParamType.Character);
        pojo.setTableName(tableName);

        //TODO:设置各种数据到pojo

        CreateCharacterDBOperator operator=new CreateCharacterDBOperator(pojo);
        GateService.context.db.Call(operator, tableName, DBOperateType.Write ,(op)-> {
            //回调内容
            //TODO 所有的需要的默认角色信息，插入到DB
        });
    }
}
