package jp.ceed.android.mylapslogger.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activities(

    @SerialName("activityCount")
    val activityCount: Int = 0,

    @SerialName("activities")
    val activities: List<ActivityDto>

) {
    @Serializable
    data class ActivityDto (

        @SerialName("id")
        val id: Long = 0,

        @SerialName("name")
        val name: String,

        @SerialName("startTime")
        val startTime: String,

        @SerialName("endTime")
        val endTime: String,

        @SerialName("frameType")
        val frameType: Int,

        @SerialName("location")
        val location: Location,

        @SerialName("accountId")
        val accountId: Int,

        @SerialName("gaUId")
        val gaUId: String,

        @SerialName("userId")
        val userId: String,

        @SerialName("chipLabel")
        val chipLabel: String,

        @SerialName("chipListDto")
        val chipListDto: String? = null,

        @SerialName("chipCode")
        val chipCode: String,

        @SerialName("practiceSessionsDto")
        val practiceSessionsDto: String? = null,
    ) {
        @Serializable
        data class Location (

            @SerialName("name")
            val name: String,

            @SerialName("id")
            val id: Int = 0,

            @SerialName("description")
            val description: String? = null,

            @SerialName("url")
            val url: String? = null,

            @SerialName("trackLength")
            val trackLength: Int = 0,

            @SerialName("status")
            val status: String,

            @SerialName("sport")
            val sport: String,

            @SerialName("country")
            val country: String,

            @SerialName("x2ServiceId")
            val x2ServiceId: Int = 0,

            @SerialName("ipAddress")
            val ipAddress: String? = null,

            @SerialName("sections")
            val sections: List<Sections>,

            ) {
            @Serializable
            data class Sections (

                @SerialName("name")
                val name: String,

                @SerialName("length")
                val length: Int = 0,

                @SerialName("speedTrap")
                val speedTrap: Boolean = false,
            )            
        }
    }
}
