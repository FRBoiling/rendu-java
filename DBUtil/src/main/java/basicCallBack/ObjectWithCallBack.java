package basicCallBack;

import java.util.HashSet;

public class ObjectWithCallBack {
    protected Object arg;
    protected HashSet<ObjectBeCalled> callbacks=new HashSet<>();

    public void RegistCallBack(ObjectBeCalled callBack) {
        callbacks.add(callBack);
    }

    public void UnRegistCallBack(ObjectBeCalled callBack){
        callbacks.remove(callBack);
    }

    public void OnCall() {
        for (ObjectBeCalled obj:callbacks) {
            obj.call(this,arg);
        }
    }
}
