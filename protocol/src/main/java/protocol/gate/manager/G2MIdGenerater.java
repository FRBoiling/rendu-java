 //------------------------------------------------------------------------------
 // <auto-generated>
 //     This code was generated by a tool.(The author is Boiling)
 //     Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 // </auto-generated>
 //------------------------------------------------------------------------------
package protocol.gate.manager;
import protocol.msgId.Id;
public class G2MIdGenerater{
     public static void GenerateId(){
          Id.getInst().SetMessage(G2M.MSG_G2M_HEARTBEAT.class, 0x130001);
          Id.getInst().SetMessage(G2M.MSG_G2M_REPEAT_LOGIN.class, 0x130002);
          Id.getInst().SetMessage(G2M.MSG_G2M_MAX_UID.class, 0x130003);
     }
}
