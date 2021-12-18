package jp.ceed.android.mylapslogger.util;

import android.util.Log;

import jp.ceed.android.mylapslogger.BuildConfig;


/**
 * The type Log util.
 *
 * @author ARAKI
 */
public class LogUtil {

	/**
	 * The constant TAG.
	 */
	public static final String TAG = "MyLapsLogger";


	/**
	 * E.
	 *
	 * @param e the e
	 */
	public static void e(Exception e){
		e(e.getMessage());
	}


	/**
	 * D.
	 *
	 * @param msg the msg
	 */
	public static void d(String msg){
		d(TAG, msg);
	}


	/**
	 * D.
	 *
	 * @param tag tag.
	 * @param msg msg.
	 */
	public static void d(String tag, String msg){
		if(!BuildConfig.DEBUG){
			return;
		}
		Log.d(tag, defaultString(msg, ""));
	}


	/**
	 * E.
	 *
	 * @param msg the msg
	 */
	public static void e(String msg){
		if(!BuildConfig.DEBUG){
			return;
		}
		Log.e(TAG, defaultString(msg, ""));
	}


	private static String defaultString(String msg, String defaultString){
		return msg == null ? defaultString : msg;
	}


	private LogUtil() {
	}

}
