package vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer;

import android.widget.ImageView;
import android.widget.TextView;

import com.views.placeholderview.annotations.Layout;
import com.views.placeholderview.annotations.NonReusable;
import com.views.placeholderview.annotations.Resolve;
import com.views.placeholderview.annotations.View;

import loitp.basemaster.R;

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
