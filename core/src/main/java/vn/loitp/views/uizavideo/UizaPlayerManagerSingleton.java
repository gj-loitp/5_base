package vn.loitp.views.uizavideo;

/**
 * Created by LENOVO on 4/26/2018.
 */

public class UizaPlayerManagerSingleton {
    private static final UizaPlayerManagerSingleton ourInstance = new UizaPlayerManagerSingleton();

    public static UizaPlayerManagerSingleton getInstance() {
        return ourInstance;
    }

    private UizaPlayerManagerSingleton() {
    }

}
