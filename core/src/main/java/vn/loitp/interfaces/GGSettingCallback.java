package vn.loitp.interfaces;

import java.io.IOException;

import okhttp3.Call;
import vn.loitp.model.App;

public interface GGSettingCallback {
    public void onFailure(Call call, IOException e);

    public void onResponse(App app);
}
