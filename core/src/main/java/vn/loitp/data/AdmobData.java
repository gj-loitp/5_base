package vn.loitp.data;

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
            throw new NullPointerException("idAdmobFull cannot be null or empty");
        }
        return idAdmobFull;
    }

    public void setIdAdmobFull(String idAdmobFull) {
        this.idAdmobFull = idAdmobFull;
    }
}

