package util;


public class Time {
    private long prev;

    public void Init(){
        prev=System.currentTimeMillis();
    }

    public long Update(){
        long now=System.currentTimeMillis();
        long time=now-prev;
        prev=now;
        return time;
    }
}
