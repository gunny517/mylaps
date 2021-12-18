package jp.ceed.android.mylapslogger.dto;

import java.io.Serializable;

import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.BestLapX;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.Laps;
import jp.ceed.android.mylapslogger.util.Util;

/**
 * Created by ARAKI on 2017/04/28.
 *
 */

public class LapDto implements Serializable {

	public static final String REPORT_BEST = "REPORTBEST";

	public static final String SESSION_BEST = "SESSIONBEST";

	public static final String FASTER = "FASTER";

	public static final String SLOWER = "SLOWER";

	public String number;

	public String duration;

	public String status;

	public String diffPrevLap;

	public String sectionTitle;

	public String sessionTime;


	public LapDto(){

	}


	public LapDto(Laps laps){
		number = String.valueOf(laps.nr);
		duration = laps.duration;
		status = laps.status;
		diffPrevLap = laps.diffPrevLap;
	}


	public LapDto(Sessions sessions){
		sessionTime = Util.convertTo(Util.API_TIME_FORMAT_WITH_SEC, "HH:mm:ss", sessions.dateTimeStart);
		sectionTitle = String.format("セッション：%s", sessions.id);
	}


	public LapDto(BestLapX bestLapx){
		number = String.valueOf(bestLapx.nr);
		duration = bestLapx.duration;
	}


}
