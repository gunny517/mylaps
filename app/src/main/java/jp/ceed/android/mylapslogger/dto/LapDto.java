package jp.ceed.android.mylapslogger.dto;

import android.se.omapi.Session;

import java.io.Serializable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import jp.ceed.android.mylapslogger.R;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.BestLapX;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.Laps;
import jp.ceed.android.mylapslogger.util.DateUtil;
import jp.ceed.android.mylapslogger.util.Util;
import androidx.constraintlayout.widget.Guideline;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class LapDto implements Serializable {

    @BindingAdapter("layout_constraintGuide_percent")
    public static void setLayoutConstraintGuidePercent(Guideline guideline, float percent) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        params.guidePercent = percent;
        guideline.setLayoutParams(params);
    }

}
