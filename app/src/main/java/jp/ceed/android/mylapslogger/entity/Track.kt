package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.model.ActivitiesItem

@Entity
data  class Track(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "length") val length: Int
){
    constructor(activitiesItem: ActivitiesItem): this(
        id = activitiesItem.locationId,
        name = activitiesItem.place,
        length = activitiesItem.trackLength
    )
}