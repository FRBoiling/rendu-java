 //------------------------------------------------------------------------------
 // <auto-generated>
 //     This code was generated by a tool.(The author is Boiling)
 //     Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 // </auto-generated>
 //------------------------------------------------------------------------------
package protocol.relation.global;
import protocol.msgId.Id;
public class R2GMIdGenerater{
     public static void GenerateId(){
          Id.getInst().SetMessage(protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.class, 0x520001);
     }
}
