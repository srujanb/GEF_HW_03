package ServerSpecific;

import Utils.UniversalConstants;

public class Timeline {

    private static long serverGameTimeTicks;
    private static long realTimeAtLastTick;
    private static int tickSize = UniversalConstants.DEFAULT_TICK_SIZE;

    public static int getTickSize() {
        return tickSize;
    }

    public static void setTickSize(int tickSize) {
        Timeline.tickSize = tickSize;
    }

    public static int getNextTick(){
        if (tickSize >= 1000) return 0;
        if (realTimeAtLastTick == 0) {
            serverGameTimeTicks = 1;
            realTimeAtLastTick = System.currentTimeMillis();
            return 1;
        }
        long currentTime = System.currentTimeMillis();
        long timeSinceLastTick = currentTime - realTimeAtLastTick;
        int ticks = (int) ((timeSinceLastTick - 1) / tickSize) + 1;
        serverGameTimeTicks += ticks;
        realTimeAtLastTick += tickSize*ticks;
        try {
            Thread.sleep(tickSize - timeSinceLastTick%tickSize);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ticks;
    }

    public static long getServerGameTimeTicks() {
        return serverGameTimeTicks;
    }

    public void setSpeed(int speed){
        if (speed == 0) tickSize = 1000;
        else tickSize = UniversalConstants.DEFAULT_TICK_SIZE / speed;
    }
}
