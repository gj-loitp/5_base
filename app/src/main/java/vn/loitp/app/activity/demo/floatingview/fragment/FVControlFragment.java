package vn.loitp.app.activity.demo.floatingview.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.views.floatingview.FloatingViewManager;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.floatingview.service.FVChatHeadService;
import vn.loitp.app.activity.demo.floatingview.service.FVCustomFloatingViewService;
import vn.loitp.app.activity.demo.floatingview.service.FVCustomUZVideoService;

public class FVControlFragment extends Fragment {
    private final String TAG = "FloatingViewControl";
    private final int REQUEST_CODE = 100;

    private enum SV {ONE, TWO, THREE}

    private SV sv;

    public static FVControlFragment newInstance() {
        final FVControlFragment fragment = new FVControlFragment();
        return fragment;
    }

    public FVControlFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fv_fragment_floating_view_control, container, false);
        rootView.findViewById(R.id.show_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv = SV.ONE;
                showFloatingView(getActivity());
            }
        });
        rootView.findViewById(R.id.show_customized_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv = SV.TWO;
                showFloatingView(getActivity());
            }
        });
        rootView.findViewById(R.id.show_customized_demo_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv = SV.THREE;
                showFloatingView(getActivity());
            }
        });
        return rootView;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            showFloatingView(getActivity());
        }
    }

    @SuppressLint("NewApi")
    private void showFloatingView(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService(getActivity());
            return;
        }
        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService(getActivity());
            return;
        }
        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void startFloatingViewService(Activity activity) {
        // *** You must follow these rules when obtain the cutout(FloatingViewManager.findCutoutSafeArea) ***
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 1. 'windowLayoutInDisplayCutoutMode' do not be set to 'never'
            if (activity.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                throw new RuntimeException("'windowLayoutInDisplayCutoutMode' do not be set to 'never'");
            }
            // 2. Do not set Activity to landscape
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                throw new RuntimeException("Do not set Activity to landscape");
            }
        }
        // launch service
        Class<? extends Service> service = null;
        String key = null;
        if (sv == SV.ONE) {
            service = FVCustomFloatingViewService.class;
            key = FVCustomFloatingViewService.EXTRA_CUTOUT_SAFE_AREA;
        } else if (sv == SV.TWO) {
            service = FVChatHeadService.class;
            key = FVChatHeadService.EXTRA_CUTOUT_SAFE_AREA;
        } else if (sv == SV.THREE) {
            service = FVCustomUZVideoService.class;
            key = FVCustomFloatingViewService.EXTRA_CUTOUT_SAFE_AREA;
        }
        if (service != null || key != null) {
            final Intent intent = new Intent(activity, service);
            intent.putExtra(key, FloatingViewManager.findCutoutSafeArea(activity));
            ContextCompat.startForegroundService(activity, intent);
        }
    }
}