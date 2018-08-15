package util;


import lombok.Getter;

public class Time {
    private long prev;
    @Getter
    private long now;

    public long init() {
        now = System.currentTimeMillis();
        return now;
    }

    public long update() {
        now = System.currentTimeMillis();
        long time = now - prev;
        prev = now;
        return time;
    }
}
