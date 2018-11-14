package vn.loitp.data;

import vn.loitp.core.common.Constants;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay;

public class UZData {
    private static final UZData ourInstance = new UZData();

    public static UZData getInstance() {
        return ourInstance;
    }

    private UZData() {
    }

    public void initWorkspace(String domainApi, String appId, String token, String urlLinkPlayEnvironment) {
        this.appId = appId;
        UZRestClient.init(Constants.PREFIXS + domainApi, token);
        UZRestClientGetLinkPlay.init(urlLinkPlayEnvironment);
    }

    private String appId;

    public String getAppId() {
        return appId;
    }
}
