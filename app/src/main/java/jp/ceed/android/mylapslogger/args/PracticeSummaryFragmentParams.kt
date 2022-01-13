package jp.ceed.android.mylapslogger.args

import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import java.io.Serializable

class PracticeSummaryFragmentParams(
    val sessionSummary: ArrayList<PracticeResultsItem>
) : Serializable {
}