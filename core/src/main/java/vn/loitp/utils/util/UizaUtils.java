package vn.loitp.utils.util;

import vn.loitp.core.common.Constants;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay;

public class UizaUtils {
    public static void initWorkspace(String domainApi, String token, String urlLinkPlayEnvironment) {
        UZRestClient.init(Constants.PREFIXS + domainApi, token);
        UZRestClientGetLinkPlay.init(urlLinkPlayEnvironment);
    }
}
