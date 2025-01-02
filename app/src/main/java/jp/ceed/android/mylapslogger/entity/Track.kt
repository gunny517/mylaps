package jp.ceed.android.mylapslogger.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.model.ActivitiesItem

@Entity
data  class Track(
    @PrimaryKey val id: Int,
    val name: String,
    val length: Int,
    val created: Long

){
    constructor(activitiesItem: ActivitiesItem, created: Long): this(
        id = activitiesItem.locationId,
        name = activitiesItem.place,
        length = activitiesItem.trackLength,
        created = created
    )
}