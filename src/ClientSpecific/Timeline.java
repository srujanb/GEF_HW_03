package ClientSpecific;

public class Timeline {

    private static long latestServerGameTime;
    private static long latestLocalGameTime;

    public static long getLatestServerGameTime() {
        return latestServerGameTime;
    }

    public static void setLatestServerGameTime(long latestServerGameTime) {
        Timeline.latestServerGameTime = latestServerGameTime;
    }

    public static long getLatestLocalGameTime() {
        return latestLocalGameTime;
    }

    public static void setLatestLocalGameTime(long latestLocalGameTime) {
        Timeline.latestLocalGameTime = latestLocalGameTime;
    }

    public static long getLocalGameLagAndReset() {
        long diff = latestServerGameTime - latestLocalGameTime;
        latestLocalGameTime = latestServerGameTime;
        return diff;
    }
}
