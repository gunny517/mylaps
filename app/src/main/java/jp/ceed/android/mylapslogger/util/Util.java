package jp.ceed.android.mylapslogger.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jp.ceed.android.mylapslogger.model.ActivitiesItem;
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse.ActivityDto;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class Util {

    public static final String API_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final String API_TIME_FORMAT_WITH_MILLI_SEC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static final String HMS_FORMAT = "HH:mm:ss";

    private static final String YMD_FORMAT = "yyyy/MM/dd";

    private static final SimpleDateFormat YMD_SIMPLE_DATE_FORMAT = new SimpleDateFormat(YMD_FORMAT, Locale.JAPAN);

    private static final SimpleDateFormat HMS_SIMPLE_DATE_FORMAT = new SimpleDateFormat(HMS_FORMAT, Locale.JAPAN);

    private static final SimpleDateFormat API_SIMPLE_DATE_FORMAT = new SimpleDateFormat(API_TIME_FORMAT, Locale.JAPAN);

    private static final SimpleDateFormat API_SIMPLE_DATE_FORMAT_W_MILLI_SEC = new SimpleDateFormat(API_TIME_FORMAT_WITH_MILLI_SEC, Locale.JAPAN);


    public static List<ActivitiesItem> convertToActivitiesItem(final List<ActivityDto> list) {
        List<ActivitiesItem> results = new ArrayList<>(list.size());
        for (ActivityDto entry : list) {
            results.add(new ActivitiesItem(entry));
        }
        return results;
    }


    public static long toTimeFromDateTimeWithMilliSec(String dateTimeStr) {
        try {
            return Objects.requireNonNull(API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateTimeStr)).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }


    public static String toHmsFromDateTimeWithMilliSec(String dateStr) {
        try {
            return HMS_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateStr)).getTime());
        } catch (ParseException e) {
            return null;
        }
    }


    public static String toYmdFormatFromDateTime(String dateTimeStr) {
        try {
            return YMD_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT.parse(dateTimeStr)).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
