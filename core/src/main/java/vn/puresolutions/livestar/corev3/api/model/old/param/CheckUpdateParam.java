package vn.puresolutions.livestar.corev3.api.model.old.param;

import vn.puresolutions.livestar.corev3.api.model.old.def.AppType;
import vn.puresolutions.livestar.corev3.api.model.old.def.Platform;

/**
 * @author hoangphu
 * @since 12/21/16
 */

public class CheckUpdateParam {
    private String version;
    private Platform os;
    private AppType app;

    public CheckUpdateParam(String version) {
        this.version = version;
        this.os = Platform.ANDROID;
        this.app = AppType.CLIENT;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Platform getOs() {
        return os;
    }

    public void setOs(Platform os) {
        this.os = os;
    }

    public AppType getApp() {
        return app;
    }

    public void setApp(AppType app) {
        this.app = app;
    }
}
