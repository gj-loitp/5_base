package com.views.wwlmusic.utils;

/**
 * Created by thangn on 2/26/17.
 */
public class WWLMusicIllegal {
    public static void check(boolean ok) {
        if (!ok) {
            throw new IllegalArgumentException();
        }
    }

    public static void check(boolean ok, Object object) {
        if (!ok) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static Object check(Object object) {
        if (object != null) {
            return object;
        }
        throw new IllegalArgumentException();
    }

    public static Object check(Object object, Object objectMsg) {
        if (object != null) {
            return object;
        }
        throw new IllegalArgumentException(String.valueOf(objectMsg));
    }
}
