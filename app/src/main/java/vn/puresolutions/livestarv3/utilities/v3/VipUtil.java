package vn.puresolutions.livestarv3.utilities.v3;

import vn.puresolutions.livestar.R;

/**
 * File created on 8/25/2017.
 *
 * @author Muammil
 */

public class VipUtil {
    public final static int EXP_LV1 = 100;
    public final static int EXP_LV2 = 300;
    public final static int EXP_LV3 = 500;
    public final static int EXP_LV4 = 900;
    public final static int EXP_LV5 = 1700;
    public final static int EXP_LV6 = 3300;
    public final static int EXP_LV7 = 6500;
    public final static int EXP_LV8 = 12900;
    public final static int EXP_LV9 = 25700;
    public final static int EXP_LV10 = 51300;

    public static int progressLevel(int level, int exp) {
        int remainExp = 0;
        switch (level) {
            case 0:
                break;
            case 1:
                remainExp = EXP_LV2 - exp;
                break;
            case 2:
                remainExp = EXP_LV3 - exp;
                break;
            case 3:
                remainExp = EXP_LV4 - exp;
                break;
            case 4:
                remainExp = EXP_LV5 - exp;
                break;
            case 5:
                remainExp = EXP_LV6 - exp;
                break;
            case 6:
                remainExp = EXP_LV7 - exp;
                break;
            case 7:
                remainExp = EXP_LV8 - exp;
                break;
            case 8:
                remainExp = EXP_LV9 - exp;
                break;
            case 9:
                remainExp = EXP_LV10 - exp;
                break;
            case 10:
                remainExp =   exp;
                break;
        }
        return remainExp;
    }

    public static int getLevelIcon(int lv) {
        int res = 0;
        if (lv >= 1 && lv <= 3) {
            res = R.drawable.chick_green;
            //lthLevelInfo.setImage(R.drawable.chick_green);
        } else if (lv >= 4 && lv <= 7) {
            res = R.drawable.chick_orange;
            //lthLevelInfo.setImage(R.drawable.chick_orange);
        } else if (lv >= 8 && lv <= 10) {
            res = R.drawable.chick_pink;
            //lthLevelInfo.setImage(R.drawable.chick_pink);
        }
        return res;
    }
}
