package Utils;

public class GuidUtil {

    public long latestGuid = 1000;
    private static final GuidUtil instance = new GuidUtil();

    public GuidUtil(){}

    public static GuidUtil getInstance(){
        return instance;
    }

    public long getNewGuid(){
        return ++instance.latestGuid;
    }
}
