package Models;

import Utils.GuidUtil;

import java.io.Serializable;

public class GameObject implements Serializable{

    protected final long GUID;

    public GameObject(){
        GUID = GuidUtil.getInstance().getNewGuid();
    }

    public long getGUID() {
        return GUID;
    }

}
