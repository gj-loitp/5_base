package vn.loitp.core.loitp.uiza;

import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class UZD {
    private static final UZD ourInstance = new UZD();

    public static UZD getInstance() {
        return ourInstance;
    }

    private UZD() {
    }

    private Data metadata;

    public Data getMetadata() {
        return metadata;
    }

    public void setMetadata(Data metadata) {
        this.metadata = metadata;
    }
}
