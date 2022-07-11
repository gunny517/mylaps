package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.entity.ActivityInfoTrack

data class FuelConsumptionListItem (
    val dateTime: String,
    val trackName: String,
    val fuelConsumption: String,
) {
    constructor(activityInfoTrack: ActivityInfoTrack) : this(
        dateTime = activityInfoTrack.trackName,
        trackName = activityInfoTrack.trackName,
        fuelConsumption = activityInfoTrack.fuelConsumption?.toString() ?: ""
    )
}