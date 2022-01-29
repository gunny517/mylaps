package jp.ceed.android.mylapslogger.model

import android.os.Parcelable
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.util.DateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivitiesItem(
    val id: Int,
    val locationId: Int,
    val startTime: String?,
    val startTimeRaw: String,
    val endTimeRaw: String,
    val place: String,
    val trackLength: Int
): Parcelable {

    constructor(dto: ActivitiesResponse.ActivityDto): this(
        id = dto.id,
        locationId = dto.location.id,
        startTime = DateUtil.toYmdFormatFromDateTime(dto.startTime),
        startTimeRaw = dto.startTime,
        endTimeRaw = dto.endTime,
        place = dto.location.name,
        trackLength = dto.location.trackLength
    )

}