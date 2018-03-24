package vn.loitp.data;

import vn.loitp.core.utilities.LLog;

/**
 * Created by www.muathu@gmail.com on 12/26/2017.
 */

public class AdmobData {
    private static final AdmobData ourInstance = new AdmobData();

    public static AdmobData getInstance() {
        return ourInstance;
    }

    private AdmobData() {
    }

    private String idAdmobFull = "";

    public String getIdAdmobFull() {
        if (idAdmobFull == null || idAdmobFull.isEmpty()) {
            LLog.e(getClass().getSimpleName(), "idAdmobFull == null || idAdmobFull.isEmpty()");
            return "";
        }
        return idAdmobFull;
    }

    public void setIdAdmobFull(String idAdmobFull) {
        this.idAdmobFull = idAdmobFull;
    }
}

