package Models;

import Utils.GuidUtil;

public class GameObject {

    protected final long GUID;

    public GameObject(){
        GUID = GuidUtil.getInstance().getNewGuid();
    }

    public long getGUID() {
        return GUID;
    }

}
