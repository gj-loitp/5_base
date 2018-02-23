package vn.loitp.app.activity.api.retrofit2;

/**
 * Created by LENOVO on 2/23/2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://api.stackexchange.com/2.2/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}