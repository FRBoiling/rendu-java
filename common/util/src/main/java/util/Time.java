package util;


public class Time {
    private long prev;

    public long init() {
        prev = System.currentTimeMillis();
        return prev;
    }

    public long update() {
        long now = System.currentTimeMillis();
        long time = now - prev;
        prev = now;
        return time;
    }
}
