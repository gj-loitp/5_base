package com.loitpcore.function.pump.download.android;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.loitpcore.function.pump.download.core.DownloadListener;
import com.loitpcore.function.pump.download.utils.LogUtil;

public class ViewLifecycleHandler {
    public static void handleLifecycle(Lifecycle lifecycle, DownloadListener downloadListener) {
        if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED)) {
            LogUtil.w(lifecycle + " is " + lifecycle.getCurrentState() + ", so disable " + downloadListener);
            downloadListener.disable();
            return;
        }
        observerLifecycle(lifecycle, downloadListener);
    }

    public static void handleLifecycleForFragment(final Fragment fragment, final DownloadListener downloadListener) {
        final Lifecycle lifecycle = fragment.getLifecycle();
        if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED)) {
            LogUtil.w(lifecycle + " is " + lifecycle.getCurrentState() + ", so disable " + downloadListener);
            downloadListener.disable();
            return;
        }
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == ON_START) {
                    lifecycle.removeObserver(this);
                    if (fragment.getView() == null) {
                        downloadListener.disable();
                        throw new RuntimeException("fragment " + fragment
                                + " 's view is null after onStart, listen download progress at a non-ui fragment is prohibited.");
                    }
                }
            }
        });
        fragment.getViewLifecycleOwnerLiveData().observe(fragment, new Observer<LifecycleOwner>() {
            @Override
            public void onChanged(LifecycleOwner lifecycleOwner) {
                observerLifecycle(lifecycleOwner.getLifecycle(), downloadListener);
                fragment.getViewLifecycleOwnerLiveData().removeObserver(this);
            }
        });
    }

    private static void observerLifecycle(Lifecycle lifecycle, final DownloadListener downloadListener) {
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                switch (event) {
                    case ON_START:
                        downloadListener.enable();
                        break;
                    case ON_DESTROY:
                        downloadListener.disable();
                        break;
                }
            }
        });
    }
}
