package jp.ceed.android.mylapslogger.dto;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.BindingAdapter;

import java.io.Serializable;

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
