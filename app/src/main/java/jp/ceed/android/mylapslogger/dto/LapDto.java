package jp.ceed.android.mylapslogger.dto;

import java.io.Serializable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import jp.ceed.android.mylapslogger.R;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.BestLapX;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.Laps;
import jp.ceed.android.mylapslogger.util.Util;
import androidx.constraintlayout.widget.Guideline;

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

	public int cellBgColor;

	public int diffTextColor;

	public float speedLevel;

	public long sessionId;


	public LapDto(){
		// Nothing to do.
	}


	public LapDto(Laps laps){
		number = String.valueOf(laps.nr);
		duration = laps.duration;
		status = laps.status;
		cellBgColor = getCellBgColor(status);
		diffTextColor = getDiffTextColor(status);
		diffPrevLap = laps.diffPrevLap;
	}


	public LapDto(Sessions sessions){
		sessionTime = Util.toHmsFromDateTimeWithMilliSec(sessions.dateTimeStart);
		sectionTitle = String.valueOf(sessions.id);
		sessionId = Util.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart);
	}


	public LapDto(BestLapX bestLapx){
		number = String.valueOf(bestLapx.nr);
		duration = bestLapx.duration;
		cellBgColor = R.color.window_back_ground;
		diffTextColor = R.color.text_default;
	}

	private int getCellBgColor(final String status){
		if(LapDto.REPORT_BEST.equals(status)){
			return R.color.bg_lap_list_report_best;
		}else if(LapDto.SESSION_BEST.equals(status)){
			return R.color.bg_lap_list_session_best;
		}else{
			return R.color.window_back_ground;
		}
	}


	public int getDiffTextColor(final String status){
		if(LapDto.REPORT_BEST.equals(status)
			|| LapDto.SESSION_BEST.equals(status)
			|| LapDto.FASTER.equals(status)){
			return R.color.text_faster;
		}else if(LapDto.SLOWER.equals(status)){
			return R.color.text_slower;
		}else{
			return R.color.text_default;
		}
	}

	@BindingAdapter("layout_constraintGuide_percent")
	public static void setLayoutConstraintGuidePercent(Guideline guideline, float percent) {
		ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
		params.guidePercent = percent;
		guideline.setLayoutParams(params);
	}


}
