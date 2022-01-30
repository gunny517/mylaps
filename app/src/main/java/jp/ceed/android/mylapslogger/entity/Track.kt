package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.model.ActivitiesItem

@Entity
data  class Track(
    @PrimaryKey val id: Int,
    val name: String,
    val length: Int,
    val created: Long = System.currentTimeMillis()

){
    constructor(activitiesItem: ActivitiesItem): this(
        id = activitiesItem.locationId,
        name = activitiesItem.place,
        length = activitiesItem.trackLength
    )
}