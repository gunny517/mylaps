package jp.ceed.android.mylapslogger.model

import android.os.Parcelable
import jp.ceed.android.mylapslogger.network.response.Activities
import jp.ceed.android.mylapslogger.util.DateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivitiesItem(
    val id: Long,
    val locationId: Int,
    val displayTime: String?,
    val startTime: String,
    val endTimeRaw: String,
    val place: String,
    val trackLength: Int
): Parcelable {
    constructor(dto: Activities.ActivityDto): this (
        id = dto.id,
        locationId = dto.location.id,
        displayTime = DateUtil.toYmdFormatFromDateTime(dto.startTime),
        startTime = dto.startTime,
        endTimeRaw = dto.endTime,
        place = dto.location.name,
        trackLength = dto.location.trackLength
    )
}