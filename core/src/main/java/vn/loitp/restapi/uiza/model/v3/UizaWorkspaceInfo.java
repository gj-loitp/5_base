package vn.loitp.restapi.uiza.model.v3;

/**
 * Created by loitp on 6/21/2018.
 */

public class UizaWorkspaceInfo {
    private String username;
    private String password;
    private String appId;
    private String urlApi;
    private String urlLive;
    private String urlVod;
    private String urlStatic;
    private String urlDashboard;
    private String domain;//workspace

    public UizaWorkspaceInfo(String username, String password, String urlApi, String domain) {
        this.username = username;
        this.password = password;
        this.urlApi = urlApi;
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrlApi() {
        return urlApi;
    }

    public void setUrlApi(String urlApi) {
        this.urlApi = urlApi;
    }

    public String getUrlLive() {
        return urlLive;
    }

    public void setUrlLive(String urlLive) {
        this.urlLive = urlLive;
    }

    public String getUrlVod() {
        return urlVod;
    }

    public void setUrlVod(String urlVod) {
        this.urlVod = urlVod;
    }

    public String getUrlStatic() {
        return urlStatic;
    }

    public void setUrlStatic(String urlStatic) {
        this.urlStatic = urlStatic;
    }

    public String getUrlDashboard() {
        return urlDashboard;
    }

    public void setUrlDashboard(String urlDashboard) {
        this.urlDashboard = urlDashboard;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
