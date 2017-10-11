package vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer;

import android.widget.ImageView;
import android.widget.TextView;

import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.NonReusable;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {
    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    @Resolve
    private void onResolved() {
        nameTxt.setText("Loitp");
        emailTxt.setText("www.muathu@gmail.com\nfreuss47@gmail.com");
    }
}
