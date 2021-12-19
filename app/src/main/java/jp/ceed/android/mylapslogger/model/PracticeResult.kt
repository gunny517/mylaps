package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.dto.LapDto

data class PracticeResult(
	val sessionData: List<LapDto>,
	val sessionSummary: List<LapDto>
) {
}