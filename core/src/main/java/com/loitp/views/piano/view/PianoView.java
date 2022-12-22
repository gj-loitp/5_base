package com.loitp.views.piano.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.loitpcore.R;
import com.loitp.views.piano.entity.AutoPlayEntity;
import com.loitp.views.piano.entity.Piano;
import com.loitp.views.piano.entity.PianoKey;
import com.loitp.views.piano.listener.OnLoadAudioListener;
import com.loitp.views.piano.listener.OnPianoAutoPlayListener;
import com.loitp.views.piano.listener.OnPianoListener;
import com.loitp.views.piano.utils.AudioUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import kotlin.Suppress;

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class PianoView extends View {
    private Piano piano = null;
    private ArrayList<PianoKey[]> whitePianoKeys;
    private ArrayList<PianoKey[]> blackPianoKeys;
    private final CopyOnWriteArrayList<PianoKey> pressedKeys = new CopyOnWriteArrayList<>();
    private final Paint paint;
    private final RectF square;
    private String[] pianoColors = {
            "#C0C0C0", "#A52A2A", "#FF8C00", "#FFFF00", "#00FA9A", "#00CED1", "#4169E1", "#FFB6C1",
            "#FFEBCD"
    };
    private AudioUtils utils = null;
    private final Context context;
    private int layoutWidth = 0;
    private float scale = 1;
    private OnLoadAudioListener loadAudioListener;
    private OnPianoAutoPlayListener autoPlayListener;
    private OnPianoListener pianoListener;
    private int progress = 0;
    private boolean canPress = true;
    private boolean isAutoPlaying = false;
    private boolean isInitFinish = false;
    private int minRange = 0;
    private int maxRange = 0;
    private int maxStream;
    private final Handler autoPlayHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            handleAutoPlay(msg);
        }
    };
    private static final int HANDLE_AUTO_PLAY_START = 0;
    private static final int HANDLE_AUTO_PLAY_END = 1;
    private static final int HANDLE_AUTO_PLAY_BLACK_DOWN = 2;
    private static final int HANDLE_AUTO_PLAY_WHITE_DOWN = 3;
    private static final int HANDLE_AUTO_PLAY_KEY_UP = 4;

    public PianoView(Context context) {
        this(context, null);
    }

    public PianoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PianoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        square = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable whiteKeyDrawable = ContextCompat.getDrawable(context, R.drawable.white_piano_key);
        assert whiteKeyDrawable != null;
        int whiteKeyHeight = whiteKeyDrawable.getIntrinsicHeight();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = Math.min(height, whiteKeyHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = whiteKeyHeight;
                break;
            default:
                break;
        }
        scale = (float) (height - getPaddingTop() - getPaddingBottom()) / (float) (whiteKeyHeight);
        layoutWidth = width - getPaddingLeft() - getPaddingRight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (piano == null) {
            minRange = 0;
            maxRange = layoutWidth;
            piano = new Piano(context, scale);
            whitePianoKeys = piano.getWhitePianoKeys();
            blackPianoKeys = piano.getBlackPianoKeys();
            if (utils == null) {
                if (maxStream > 0) {
                    utils = AudioUtils.getInstance(getContext(), loadAudioListener, maxStream);
                } else {
                    utils = AudioUtils.getInstance(getContext(), loadAudioListener);
                }
                try {
                    utils.loadMusic(piano);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (whitePianoKeys != null) {
            for (int i = 0; i < whitePianoKeys.size(); i++) {
                for (PianoKey key : whitePianoKeys.get(i)) {
                    paint.setColor(Color.parseColor(pianoColors[i]));
                    key.getKeyDrawable().draw(canvas);
                    Rect r = key.getKeyDrawable().getBounds();
                    int sideLength = (r.right - r.left) / 2;
                    int left = r.left + sideLength / 2;
                    int top = r.bottom - sideLength - sideLength / 3;
                    int right = r.right - sideLength / 2;
                    int bottom = r.bottom - sideLength / 3;
                    square.set(left, top, right, bottom);
                    canvas.drawRoundRect(square, 6f, 6f, paint);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(sideLength / 1.8f);
                    Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                    int baseline =
                            (int) ((square.bottom + square.top - fontMetrics.bottom - fontMetrics.top) / 2);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(key.getLetterName(), square.centerX(), baseline, paint);
                }
            }
        }
        if (blackPianoKeys != null) {
            for (int i = 0; i < blackPianoKeys.size(); i++) {
                for (PianoKey key : blackPianoKeys.get(i)) {
                    key.getKeyDrawable().draw(canvas);
                }
            }
        }
        if (!isInitFinish && piano != null && pianoListener != null) {
            isInitFinish = true;
            pianoListener.onPianoInitFinish();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (!canPress) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                handleDown(event.getActionIndex(), event);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    handleMove(i, event);
                }
                for (int i = 0; i < event.getPointerCount(); i++) {
                    handleDown(i, event);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                handlePointerUp(event.getPointerId(event.getActionIndex()));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handleUp();
                return false;
            default:
                break;
        }
        return true;
    }

    private void handleDown(int which, MotionEvent event) {
        int x = (int) event.getX(which) + this.getScrollX();
        int y = (int) event.getY(which);
        for (int i = 0; i < whitePianoKeys.size(); i++) {
            for (PianoKey key : whitePianoKeys.get(i)) {
                if (!key.isPressed() && key.contains(x, y)) {
                    handleWhiteKeyDown(which, event, key);
                }
            }
        }
        for (int i = 0; i < blackPianoKeys.size(); i++) {
            for (PianoKey key : blackPianoKeys.get(i)) {
                if (!key.isPressed() && key.contains(x, y)) {
                    handleBlackKeyDown(which, event, key);
                }
            }
        }
    }

    private void handleWhiteKeyDown(int which, MotionEvent event, PianoKey key) {
        key.getKeyDrawable().setState(new int[]{android.R.attr.state_pressed});
        key.setPressed(true);
        if (event != null) {
            key.setFingerID(event.getPointerId(which));
        }
        pressedKeys.add(key);
        invalidate(key.getKeyDrawable().getBounds());
        utils.playMusic(key);
        if (pianoListener != null) {
            pianoListener.onPianoClick(key.getType(), key.getVoice(), key.getGroup(),
                    key.getPositionOfGroup());
        }
    }

    private void handleBlackKeyDown(int which, MotionEvent event, PianoKey key) {
        key.getKeyDrawable().setState(new int[]{android.R.attr.state_pressed});
        key.setPressed(true);
        if (event != null) {
            key.setFingerID(event.getPointerId(which));
        }
        pressedKeys.add(key);
        invalidate(key.getKeyDrawable().getBounds());
        utils.playMusic(key);
        if (pianoListener != null) {
            pianoListener.onPianoClick(key.getType(), key.getVoice(), key.getGroup(),
                    key.getPositionOfGroup());
        }
    }

    private void handleMove(int which, MotionEvent event) {
        int x = (int) event.getX(which) + this.getScrollX();
        int y = (int) event.getY(which);
        for (PianoKey key : pressedKeys) {
            if (key.getFingerID() == event.getPointerId(which)) {
                if (!key.contains(x, y)) {
                    key.getKeyDrawable().setState(new int[]{-android.R.attr.state_pressed});
                    invalidate(key.getKeyDrawable().getBounds());
                    key.setPressed(false);
                    key.resetFingerID();
                    pressedKeys.remove(key);
                }
            }
        }
    }

    private void handlePointerUp(int pointerId) {
        for (PianoKey key : pressedKeys) {
            if (key.getFingerID() == pointerId) {
                key.setPressed(false);
                key.resetFingerID();
                key.getKeyDrawable().setState(new int[]{-android.R.attr.state_pressed});
                invalidate(key.getKeyDrawable().getBounds());
                pressedKeys.remove(key);
                break;
            }
        }
    }

    private void handleUp() {
        if (pressedKeys.size() > 0) {
            for (PianoKey key : pressedKeys) {
                key.getKeyDrawable().setState(new int[]{-android.R.attr.state_pressed});
                key.setPressed(false);
                invalidate(key.getKeyDrawable().getBounds());
            }
            pressedKeys.clear();
        }
    }

    public void autoPlay(final List<AutoPlayEntity> autoPlayEntities) {
        if (isAutoPlaying) {
            return;
        }
        isAutoPlaying = true;
        setCanPress(false);
        new Thread() {
            @Override
            public void run() {
                autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_START);
                try {
                    if (autoPlayEntities != null) {
                        for (AutoPlayEntity entity : autoPlayEntities) {
                            if (entity != null) {
                                if (entity.getType() != null) {
                                    switch (entity.getType()) {
                                        case BLACK:
                                            PianoKey blackKey = null;
                                            if (entity.getGroup() == 0) {
                                                if (entity.getPosition() == 0) {
                                                    blackKey = blackPianoKeys.get(0)[0];
                                                }
                                            } else if (entity.getGroup() > 0 && entity.getGroup() <= 7) {
                                                if (entity.getPosition() >= 0 && entity.getPosition() <= 4) {
                                                    blackKey = blackPianoKeys.get(entity.getGroup())[entity.getPosition()];
                                                }
                                            }
                                            if (blackKey != null) {
                                                Message msg = Message.obtain();
                                                msg.what = HANDLE_AUTO_PLAY_BLACK_DOWN;
                                                msg.obj = blackKey;
                                                autoPlayHandler.sendMessage(msg);
                                            }
                                            break;
                                        case WHITE:
                                            PianoKey whiteKey = null;
                                            if (entity.getGroup() == 0) {
                                                if (entity.getPosition() == 0) {
                                                    whiteKey = whitePianoKeys.get(0)[0];
                                                } else if (entity.getPosition() == 1) {
                                                    whiteKey = whitePianoKeys.get(0)[1];
                                                }
                                            } else if (entity.getGroup() >= 0 && entity.getGroup() <= 7) {
                                                if (entity.getPosition() >= 0 && entity.getPosition() <= 6) {
                                                    whiteKey = whitePianoKeys.get(entity.getGroup())[entity.getPosition()];
                                                }
                                            } else if (entity.getGroup() == 8) {
                                                if (entity.getPosition() == 0) {
                                                    whiteKey = whitePianoKeys.get(8)[0];
                                                }
                                            }
                                            if (whiteKey != null) {
                                                Message msg = Message.obtain();
                                                msg.what = HANDLE_AUTO_PLAY_WHITE_DOWN;
                                                msg.obj = whiteKey;
                                                autoPlayHandler.sendMessage(msg);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                Thread.sleep(entity.getCurrentBreakTime() / 2);
                                autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_KEY_UP);
                                Thread.sleep(entity.getCurrentBreakTime() / 2);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_END);
            }
        }.start();
    }

    public void releaseAutoPlay() {
        if (utils != null) {
            utils.stop();
        }
    }

    public int getPianoWidth() {
        if (piano != null) {
            return piano.getPianoWith();
        }
        return 0;
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    @Suppress(names = "unused")
    public void setPianoColors(String[] pianoColors) {
        if (pianoColors.length == 9) {
            this.pianoColors = pianoColors;
        }
    }

    public void setCanPress(boolean canPress) {
        this.canPress = canPress;
    }

    public void scroll(int progress) {
        int x;
        switch (progress) {
            case 0:
                x = 0;
                break;
            case 100:
                x = getPianoWidth() - getLayoutWidth();
                break;
            default:
                x = (int) (((float) progress / 100f) * (float) (getPianoWidth() - getLayoutWidth()));
                break;
        }
        minRange = x;
        maxRange = x + getLayoutWidth();
        this.scrollTo(x, 0);
        this.progress = progress;
    }

    public void setSoundPollMaxStream(int maxStream) {
        this.maxStream = maxStream;
    }

    public void setPianoListener(OnPianoListener pianoListener) {
        this.pianoListener = pianoListener;
    }

    public void setLoadAudioListener(OnLoadAudioListener loadAudioListener) {
        this.loadAudioListener = loadAudioListener;
    }

    public void setAutoPlayListener(OnPianoAutoPlayListener autoPlayListener) {
        this.autoPlayListener = autoPlayListener;
    }

    @Suppress(names = "unused")
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void handleAutoPlay(Message msg) {
        switch (msg.what) {
            case HANDLE_AUTO_PLAY_BLACK_DOWN:
                if (msg.obj != null) {
                    try {
                        PianoKey key = (PianoKey) msg.obj;
                        autoScroll(key);
                        handleBlackKeyDown(-1, null, key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HANDLE_AUTO_PLAY_WHITE_DOWN:
                if (msg.obj != null) {
                    try {
                        PianoKey key = (PianoKey) msg.obj;
                        autoScroll(key);
                        handleWhiteKeyDown(-1, null, key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HANDLE_AUTO_PLAY_KEY_UP:
                handleUp();
                break;
            case HANDLE_AUTO_PLAY_START:
                if (autoPlayListener != null) {
                    autoPlayListener.onPianoAutoPlayStart();
                }
                break;
            case HANDLE_AUTO_PLAY_END:
                isAutoPlaying = false;
                setCanPress(true);
                if (autoPlayListener != null) {
                    autoPlayListener.onPianoAutoPlayEnd();
                }
                break;
        }
    }

    private void autoScroll(PianoKey key) {
        if (isAutoPlaying) {
            if (key != null) {
                Rect[] areas = key.getAreaOfKey();
                if (areas != null && areas.length > 0 && areas[0] != null) {
                    int left = areas[0].left, right = key.getAreaOfKey()[0].right;
                    for (int i = 1; i < areas.length; i++) {
                        if (areas[i] != null) {
                            if (areas[i].left < left) {
                                left = areas[i].left;
                            }
                            if (areas[i].right > right) {
                                right = areas[i].right;
                            }
                        }
                    }
                    if (left < minRange || right > maxRange) {
                        int progress = (int) ((float) left * 100 / (float) getPianoWidth());
                        scroll(progress);
                    }
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        postDelayed(() -> scroll(progress), 200);
    }
}
