 //------------------------------------------------------------------------------
 // <auto-generated>
 //     This code was generated by a tool.(The author is Boiling)
 //     Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 // </auto-generated>
 //------------------------------------------------------------------------------
package protocol.relation.relation;
import protocol.msgId.Id;
public class R2RIdGenerater{
     public static void GenerateId(){
          Id.getInst().SetMessage(R2R.MSG_R2R_HEARTBEAT.class, 0x550001);
     }
}
