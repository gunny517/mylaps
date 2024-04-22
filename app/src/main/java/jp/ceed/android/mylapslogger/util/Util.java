package jp.ceed.android.mylapslogger.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class Util {

    public static String createTrainingTimeString(final int lap, final int trackDistance){
        int length = lap * trackDistance;
        float lengthF = Integer.valueOf(length).floatValue() / 1000.0f;
        return String.valueOf(lengthF);
    }

    public static void hideKeyboard(Context context, View root) {
        InputMethodManager manager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        manager.hideSoftInputFromWindow(root.getWindowToken(), 0);
    }

}
