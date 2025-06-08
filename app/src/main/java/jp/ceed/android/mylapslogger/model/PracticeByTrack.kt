package jp.ceed.android.mylapslogger.model

data class PracticeByTrack(
    val activityId: Long,
    val displayTime: String?,
    val eventName: String?,
    val totalLap: Int,
    val bestTime: String,
    val trackId: Int,
    val trackName: String,
    val trackLength: Int,
)
