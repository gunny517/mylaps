package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.dto.PracticeResultsItem

data class PracticeResult(
    val sessionData: List<PracticeResultsItem>,
    val sessionSummary: List<PracticeResultsItem>,
    val dateStartTime: String?,
    val bestLap: String,
    val totalLap: String,
    val totalDistance: String,
    val totalTime: String
)