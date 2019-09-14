package vn.loitp.app.activity.function.recolor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.function.recolor.OnReColorFinish;
import com.function.recolor.ReColor;

import loitp.basemaster.R;

//https://github.com/SIMMORSAL/ReColor?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6603
public class RecolorActivity extends BaseFontActivity {
    private Activity context;
    private LinearLayout rootView, linReColorBackground;
    private LinearLayout linRecolorStatusBar, linRecolorNavigationBar, linRecolorBoth;
    private LinearLayout linPulseStatusBar, linPulseNavigationBar, linPulseBoth;
    private CardView theCardView;
    private ImageView imageView;
    private TextView textView;

    private int imageViewColorSetNumber = 0;
    private int textViewColorSetNumber = 0;
    private boolean isRootViewColorChanged = false;
    private boolean isCardColorChanged = false;
    private boolean isStatusBarColorChanged = false;
    private boolean isNavigationBarColorChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializer();
        onClicks();
        logAListOfColors();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_recolor;
    }

    private void initializer() {
        context = this;
        rootView = findViewById(R.id.rootView);
        linReColorBackground = findViewById(R.id.linReColorBackground);
        theCardView = findViewById(R.id.theCardView);
        linRecolorStatusBar = findViewById(R.id.linRecolorStatusBar);
        linRecolorNavigationBar = findViewById(R.id.linRecolorNavigationBar);
        linRecolorBoth = findViewById(R.id.linRecolorBoth);
        linPulseStatusBar = findViewById(R.id.linPulseStatusBar);
        linPulseNavigationBar = findViewById(R.id.linPulseNavigationBar);
        linPulseBoth = findViewById(R.id.linPulseBoth);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
    }

    private void logAListOfColors() {
        int[] colorList = new ReColor(context).getColorIntArray("E91E63", "1E88E5", 20);
        for (int i = 0; i < colorList.length; i++) LLog.INSTANCE.d("Color #" + i, colorList[i] + "");
    }

    private void onClicks() {
        // changing space_invaders' color
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (imageViewColorSetNumber) {
                    case 0:
                        new ReColor(context).setImageViewColorFilter(imageView, "ffffff", "388E3C", 300);
                        imageViewColorSetNumber = 1;
                        break;
                    case 1:
                        new ReColor(context).setImageViewColorFilter(imageView, "388E3C", "00838F", 300);
                        imageViewColorSetNumber = 2;
                        break;
                    case 2:
                        new ReColor(context).setImageViewColorFilter(imageView, "00838F", "F4511E", 300);
                        imageViewColorSetNumber = 3;
                        break;
                    case 3:
                        new ReColor(context).setImageViewColorFilter(imageView, "F4511E", "FFFFFF", 300);
                        imageViewColorSetNumber = 0;
                        break;
                }
            }
        });

        //changing the color on the text view
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (textViewColorSetNumber) {
                    case 0:
                        new ReColor(context).setTextViewColor(textView, "ffffff", "81D4FA", 300);
                        textViewColorSetNumber = 1;
                        break;
                    case 1:
                        new ReColor(context).setTextViewColor(textView, "81D4FA", "ef9a9a", 300);
                        textViewColorSetNumber = 2;
                        break;
                    case 2:
                        new ReColor(context).setTextViewColor(textView, "ef9a9a", "FFAB91", 300);
                        textViewColorSetNumber = 3;
                        break;
                    case 3:
                        new ReColor(context).setTextViewColor(textView, "FFAB91", "ffffff", 300);
                        textViewColorSetNumber = 0;
                        break;
                }
            }
        });

        linReColorBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRootViewColorChanged) {
                    new ReColor(context).setViewBackgroundColor(rootView, "004D40", "#880E4F", 10000);
                    isRootViewColorChanged = !isRootViewColorChanged;
                } else {
                    new ReColor(context).setViewBackgroundColor(rootView, "880E4F", "#004D40", 10000);
                    isRootViewColorChanged = !isRootViewColorChanged;
                }
            }
        });

        final ReColor reColorCardView = new ReColor(context);
        reColorCardView.setOnReColorFinish(new OnReColorFinish() {
            @Override
            public void onFinish() {
                LLog.INSTANCE.d("onReColorFinishCallBack", "It listens");
            }
        });
        theCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardColorChanged) {
                    reColorCardView.setCardViewColor(theCardView, "1E88E5", "f44336", 3000);
                    isCardColorChanged = !isCardColorChanged;
                } else {
                    reColorCardView.setCardViewColor(theCardView, "f44336", "1E88E5", 3000);
                    isCardColorChanged = !isCardColorChanged;
                }
            }
        });

        // changing statusBar color
        linRecolorStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStatusBarColorChanged) {
                    // if starting color is null, color will be automatically retrieved from status bar
                    // same is true for navigation bar
                    new ReColor(context).setStatusBarColor(null, "880E4F", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                } else {
                    new ReColor(context).setStatusBarColor(null, "004D40", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                }
            }
        });

        // changing navigationBar color
        linRecolorNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNavigationBarColorChanged) {
                    new ReColor(context).setNavigationBarColor(null, "880E4F", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                } else {
                    new ReColor(context).setNavigationBarColor(null, "000000", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                }
            }
        });

        // changing both statusBar and navigationBar colors
        linRecolorBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStatusBarColorChanged) {
                    new ReColor(context).setStatusBarColor(null, "880E4F", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                } else {
                    new ReColor(context).setStatusBarColor(null, "004D40", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                }

                if (!isNavigationBarColorChanged) {
                    new ReColor(context).setNavigationBarColor(null, "880E4F", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                } else {
                    new ReColor(context).setNavigationBarColor(null, "000000", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                }
            }
        });

        // pulsing statusBar to an orange color for 2 times
        linPulseStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReColor(context).pulseStatusBar("E64A19", 200, 2);
            }
        });

        // pulsing navigationBar to an orange color for 2 times
        linPulseNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReColor(context).pulseNavigationBar("e64a19", 200, 2);
            }
        });


        // pulsing both colors' to an orange color for 5 times really fast
        linPulseBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReColor(context).pulseStatusBar("E64A19", 80, 5);
                new ReColor(context).pulseNavigationBar("e64a19", 80, 5);
            }
        });
    }
}
