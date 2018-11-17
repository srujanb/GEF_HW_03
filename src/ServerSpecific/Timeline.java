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
//        System.out.println("Timeline.java getNextTick with size: " + tickSize);
//        System.out.println("Timeline: ServerGameTimeTicks: " + serverGameTimeTicks);
        if (tickSize >= 1000) {
            try {
                realTimeAtLastTick = System.currentTimeMillis();
                Thread.sleep(UniversalConstants.DEFAULT_TICK_SIZE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }
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

    public static void setServerGameTimeTicks(long time){
        serverGameTimeTicks = time;
    }

    public static void setSpeed(float speed){
        if (speed == 0) tickSize = 1000;
        else tickSize = (int) (UniversalConstants.DEFAULT_TICK_SIZE / speed);
        System.out.println("New tick size: " + tickSize);
    }
}
