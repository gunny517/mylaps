package jp.ceed.android.mylapslogger.args

import jp.ceed.android.mylapslogger.dto.LapDto
import java.io.Serializable

class PracticeSummaryFragmentParams (
	val sessionSummary: ArrayList<LapDto>,
	val sessionDate: String
) :Serializable {
}