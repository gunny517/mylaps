package jp.ceed.android.mylapslogger.util;

import java.util.Locale;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class Util {

    public static String createTrainingTimeString(final int lap, final int trackDistance){
        int length = lap * trackDistance;
        return String.format(Locale.JAPAN, "%,d", length);
    }
}
