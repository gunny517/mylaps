package jp.ceed.android.mylapslogger.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.ceed.android.mylapslogger.model.ActivitiesIItem;
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse.ActivityDto;

/**
 * Created by ARAKI on 2017/04/28.
 *
 */

public class Util {

	public static final String API_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	public static final String API_TIME_FORMAT_WITH_SEC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


	public static List<ActivitiesIItem> convertToActivitiesItem(final List<ActivityDto> list){
		List<ActivitiesIItem> results = new ArrayList<>(list.size());
		for(ActivityDto entry : list){
			results.add(new ActivitiesIItem(entry));
		}
		return results;
	}



	/**
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr){
		try{
			SimpleDateFormat format = new SimpleDateFormat(API_TIME_FORMAT, Locale.JAPAN);
			return format.parse(dateStr);
		}catch (ParseException e){
			LogUtil.e(e);
			return null;
		}

	}


	/**
	 *
	 * @param fromFormat
	 * @param toFormat
	 * @param orgDateStr
	 * @return
	 */
	public static String convertTo(String fromFormat, String toFormat, String orgDateStr){
		try{
			SimpleDateFormat format = new SimpleDateFormat(fromFormat, Locale.JAPANESE);
			Date date = format.parse(orgDateStr);
			return new SimpleDateFormat(toFormat, Locale.JAPANESE).format(date);
		}catch (ParseException e){
			LogUtil.e(e);
			return orgDateStr;
		}
	}


}
