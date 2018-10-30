package vn.loitp.app.activity.security.fingerprint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFFingerprintPinCodeHelper;
import com.beautycoder.pflockscreen.security.PFSecurityException;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.LToast;

//customize UI style/FingerPrintTheme
public class FingerPrintActivity extends BaseFontActivity {
    //private RelativeLayout rlMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //rlMsg = (RelativeLayout) findViewById(R.id.rl_msg);
        //rlMsg.setVisibility(View.GONE);
        LScreenUtil.toggleFullscreen(activity, true);
        showLockScreenFragment();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_finger_print;
    }

    private PFLockScreenFragment.OnPFLockScreenCodeCreateListener mCodeCreateListener =
            new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
                @Override
                public void onCodeCreated(String encodedCode) {
                    LLog.d(TAG, "onCodeCreated encodedCode " + encodedCode);
                    LToast.show(activity, "Code created");
                    LPref.savePassCode(activity, encodedCode);
                    showMainFragment();
                }
            };

    private PFLockScreenFragment.OnPFLockScreenLoginListener mLoginListener =
            new PFLockScreenFragment.OnPFLockScreenLoginListener() {

                @Override
                public void onCodeInputSuccessful() {
                    LLog.d(TAG, "onCodeInputSuccessful");
                    //LToast.show(activity, "Code successfull");
                    showMainFragment();
                }

                @Override
                public void onFingerprintSuccessful() {
                    LLog.d(TAG, "onFingerprintSuccessful");
                    //LToast.show(activity, "Fingerprint successfull");
                    showMainFragment();
                }

                @Override
                public void onPinLoginFailed() {
                    LLog.d(TAG, "onPinLoginFailed");
                    LToast.show(activity, "Pin failed");
                }

                @Override
                public void onFingerprintLoginFailed() {
                    LLog.d(TAG, "onFingerprintLoginFailed");
                    LToast.show(activity, "Fingerprint failed");
                }
            };

    private void showLockScreenFragment() {
        try {
            final boolean isPinExist = PFFingerprintPinCodeHelper.getInstance().isPinCodeEncryptionKeyExist();
            final PFFLockScreenConfiguration.Builder builder = new PFFLockScreenConfiguration.Builder(this)
                    .setTitle(isPinExist ? "Unlock with your pin code or fingerprint" : "Create Code")
                    .setCodeLength(4)

                    //it works
                    /*.setLeftButton("Can't remeber", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LToast.show(activity, "onClick");
                        }
                    })*/

                    .setUseFingerprint(true);

            PFLockScreenFragment fragment = new PFLockScreenFragment();
            builder.setMode(isPinExist ? PFFLockScreenConfiguration.MODE_AUTH : PFFLockScreenConfiguration.MODE_CREATE);
            if (isPinExist) {
                fragment.setEncodedPinCode(LPref.getPassCode(this));
                fragment.setLoginListener(mLoginListener);
            }
            fragment.setConfiguration(builder.build());
            fragment.setCodeCreateListener(mCodeCreateListener);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).setCustomAnimations(android.R.anim.fade_in, 0).commit();

        } catch (PFSecurityException e) {
            LLog.e(TAG, "PFSecurityException " + e.toString());
            LToast.show(activity, "Cannot get pin code info");
            return;
        }
    }

    private void showMainFragment() {
        LScreenUtil.toggleFullscreen(activity, false);
        Fragment fragment = new MainFragment();
        FragmentTransaction transaction = ((BaseActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
        //transaction.replace(R.id.container, fragment).setCustomAnimations(R.anim.pdlg_anim_fade_out, 0).commit();
    }
}
