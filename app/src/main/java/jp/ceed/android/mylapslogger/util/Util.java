package jp.ceed.android.mylapslogger.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.ceed.android.mylapslogger.model.ActivitiesItem;
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse.ActivityDto;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class Util {

    public static List<ActivitiesItem> convertToActivitiesItem(final List<ActivityDto> list) {
        List<ActivitiesItem> results = new ArrayList<>(list.size());
        for (ActivityDto entry : list) {
            results.add(new ActivitiesItem(entry));
        }
        return results;
    }


    public static void checkThread(final Context context, final String name){
        LogUtil.d("[" + name + " is main thread] : " + Thread.currentThread().equals(context.getMainLooper().getThread()));
    }


    public static String createTrainingTimeString(final int lap, final int trackDistance){
        int length = lap * trackDistance;
        return String.format(Locale.JAPAN, "%,d", length);
    }
}
