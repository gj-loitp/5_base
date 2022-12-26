package com.loitp.views.piano.entity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.gson.annotations.SerializedName;
import com.loitp.R;

import java.util.ArrayList;

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class Piano {
    public final static int PIANO_NUMS = 88;
    private final static int BLACK_PIANO_KEY_GROUPS = 8;
    private final static int WHITE_PIANO_KEY_GROUPS = 9;
    private final ArrayList<PianoKey[]> blackPianoKeys = new ArrayList<>(BLACK_PIANO_KEY_GROUPS);
    private final ArrayList<PianoKey[]> whitePianoKeys = new ArrayList<>(WHITE_PIANO_KEY_GROUPS);
    private int blackKeyWidth;
    private int blackKeyHeight;
    private int whiteKeyWidth;
    private int whiteKeyHeight;
    private int pianoWith = 0;
    private final float scale;
    private final Context context;

    public Piano(Context context, float scale) {
        this.context = context;
        this.scale = scale;
        initPiano();
    }

    private void initPiano() {
        if (scale > 0) {
            Drawable blackDrawable = ContextCompat.getDrawable(context, R.drawable.black_piano_key);
            Drawable whiteDrawable = ContextCompat.getDrawable(context, R.drawable.white_piano_key);
            assert blackDrawable != null;
            blackKeyWidth = blackDrawable.getIntrinsicWidth();
            blackKeyHeight = (int) ((float) blackDrawable.getIntrinsicHeight() * scale);
            assert whiteDrawable != null;
            whiteKeyWidth = whiteDrawable.getIntrinsicWidth();
            whiteKeyHeight = (int) ((float) whiteDrawable.getIntrinsicHeight() * scale);

            for (int i = 0; i < BLACK_PIANO_KEY_GROUPS; i++) {
                PianoKey[] keys;
                if (i == 0) {
                    keys = new PianoKey[1];
                } else {
                    keys = new PianoKey[5];
                }
                for (int j = 0; j < keys.length; j++) {
                    keys[j] = new PianoKey();
                    Rect[] areaOfKey = new Rect[1];
                    keys[j].setType(PianoKeyType.BLACK);
                    keys[j].setGroup(i);
                    keys[j].setPositionOfGroup(j);
                    keys[j].setVoiceId(getVoiceFromResources("b" + i + j));
                    keys[j].setPressed(false);
                    keys[j].setKeyDrawable(new ScaleDrawable(ContextCompat.getDrawable(context, R.drawable.black_piano_key), Gravity.NO_GRAVITY, 1, scale).getDrawable());
                    setBlackKeyDrawableBounds(i, j, keys[j].getKeyDrawable());
                    areaOfKey[0] = keys[j].getKeyDrawable().getBounds();
                    keys[j].setAreaOfKey(areaOfKey);
                    if (i == 0) {
                        keys[j].setVoice(PianoVoice.LA);
                        break;
                    }
                    switch (j) {
                        case 0:
                            keys[j].setVoice(PianoVoice.DO);
                            break;
                        case 1:
                            keys[j].setVoice(PianoVoice.RE);
                            break;
                        case 2:
                            keys[j].setVoice(PianoVoice.FA);
                            break;
                        case 3:
                            keys[j].setVoice(PianoVoice.SO);
                            break;
                        case 4:
                            keys[j].setVoice(PianoVoice.LA);
                            break;
                    }
                }
                blackPianoKeys.add(keys);
            }
            for (int i = 0; i < WHITE_PIANO_KEY_GROUPS; i++) {
                PianoKey[] mKeys;
                switch (i) {
                    case 0:
                        mKeys = new PianoKey[2];
                        break;
                    case 8:
                        mKeys = new PianoKey[1];
                        break;
                    default:
                        mKeys = new PianoKey[7];
                        break;
                }
                for (int j = 0; j < mKeys.length; j++) {
                    mKeys[j] = new PianoKey();
                    mKeys[j].setType(PianoKeyType.WHITE);
                    mKeys[j].setGroup(i);
                    mKeys[j].setPositionOfGroup(j);
                    mKeys[j].setVoiceId(getVoiceFromResources("w" + i + j));
                    mKeys[j].setPressed(false);
                    mKeys[j].setKeyDrawable(new ScaleDrawable(ContextCompat.getDrawable(context, R.drawable.white_piano_key), Gravity.NO_GRAVITY, 1, scale).getDrawable());
                    setWhiteKeyDrawableBounds(i, j, mKeys[j].getKeyDrawable());
                    pianoWith += whiteKeyWidth;
                    if (i == 0) {
                        switch (j) {
                            case 0:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                                mKeys[j].setVoice(PianoVoice.LA);
                                mKeys[j].setLetterName("A0");
                                break;
                            case 1:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                                mKeys[j].setVoice(PianoVoice.SI);
                                mKeys[j].setLetterName("B0");
                                break;
                        }
                        continue;
                    }
                    if (i == 8) {
                        Rect[] areaOfKey = new Rect[1];
                        areaOfKey[0] = mKeys[j].getKeyDrawable().getBounds();
                        mKeys[j].setAreaOfKey(areaOfKey);
                        mKeys[j].setVoice(PianoVoice.DO);
                        mKeys[j].setLetterName("C8");
                        break;
                    }
                    switch (j) {
                        case 0:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                            mKeys[j].setVoice(PianoVoice.DO);
                            mKeys[j].setLetterName("C" + i);
                            break;
                        case 1:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                            mKeys[j].setVoice(PianoVoice.RE);
                            mKeys[j].setLetterName("D" + i);
                            break;
                        case 2:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                            mKeys[j].setVoice(PianoVoice.MI);
                            mKeys[j].setLetterName("E" + i);
                            break;
                        case 3:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                            mKeys[j].setVoice(PianoVoice.FA);
                            mKeys[j].setLetterName("F" + i);
                            break;
                        case 4:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                            mKeys[j].setVoice(PianoVoice.SO);
                            mKeys[j].setLetterName("G" + i);
                            break;
                        case 5:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                            mKeys[j].setVoice(PianoVoice.LA);
                            mKeys[j].setLetterName("A" + i);
                            break;
                        case 6:
                            mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                            mKeys[j].setVoice(PianoVoice.SI);
                            mKeys[j].setLetterName("B" + i);
                            break;
                    }
                }
                whitePianoKeys.add(mKeys);
            }
        }
    }

    public enum PianoVoice {
        DO, RE, MI, FA, SO, LA, SI
    }

    public enum PianoKeyType {
        @SerializedName("0") BLACK(0), @SerializedName("1") WHITE(1);
        private final int value;

        PianoKeyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @NonNull
        @Override
        public String toString() {
            return "PianoKeyType{" + "value=" + value + '}';
        }
    }

    private enum BlackKeyPosition {
        LEFT, LEFT_RIGHT, RIGHT
    }

    private int getVoiceFromResources(String voiceName) {
        return context.getResources().getIdentifier(voiceName, "raw", context.getPackageName());
    }

    private Rect[] getWhitePianoKeyArea(int group, int positionOfGroup, BlackKeyPosition blackKeyPosition) {
        int offset = 0;
        if (group == 0) {
            offset = 5;
        }
        switch (blackKeyPosition) {
            case LEFT:
                Rect[] left = new Rect[2];
                left[0] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, blackKeyHeight, (7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2, whiteKeyHeight);
                left[1] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2, 0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
                return left;
            case LEFT_RIGHT:
                Rect[] leftRight = new Rect[3];
                leftRight[0] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, blackKeyHeight, (7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2, whiteKeyHeight);
                leftRight[1] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2, 0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2, whiteKeyHeight);
                leftRight[2] = new Rect((7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2, blackKeyHeight, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
                return leftRight;
            case RIGHT:
                Rect[] right = new Rect[2];
                right[0] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, 0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2, whiteKeyHeight);
                right[1] = new Rect((7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2, blackKeyHeight, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
                return right;
        }
        return null;
    }

    private void setWhiteKeyDrawableBounds(int group, int positionOfGroup, Drawable drawable) {
        int offset = 0;
        if (group == 0) {
            offset = 5;
        }
        drawable.setBounds((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, 0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
    }

    private void setBlackKeyDrawableBounds(int group, int positionOfGroup, Drawable drawable) {
        int whiteOffset = 0;
        int blackOffset = 0;
        if (group == 0) {
            whiteOffset = 5;
        }
        if (positionOfGroup == 2 || positionOfGroup == 3 || positionOfGroup == 4) {
            blackOffset = 1;
        }
        drawable.setBounds((7 * group - 4 + whiteOffset + blackOffset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2, 0, (7 * group - 4 + whiteOffset + blackOffset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2, blackKeyHeight);
    }

    public ArrayList<PianoKey[]> getWhitePianoKeys() {
        return whitePianoKeys;
    }

    public ArrayList<PianoKey[]> getBlackPianoKeys() {
        return blackPianoKeys;
    }

    public int getPianoWith() {
        return pianoWith;
    }
}
