package jp.ceed.android.mylapslogger.util;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class Util {

    public static String createTrainingTimeString(final int lap, final int trackDistance){
        int length = lap * trackDistance;
        float lengthF = Integer.valueOf(length).floatValue() / 1000.0f;
        return String.valueOf(lengthF);
    }
}
