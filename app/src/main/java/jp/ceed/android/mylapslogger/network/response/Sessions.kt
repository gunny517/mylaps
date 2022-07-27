package jp.ceed.android.mylapslogger.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sessions(

    @SerialName("bestLap")
    val bestLap: BestLap,

    @SerialName("stats")
    val stats: Stats,

    @SerialName("sessions")
    val sessions: List<Sessions>,

    @SerialName("sections")
    val sections: List<Sections>,
) {

    @Serializable
    data class Speed (
        /**
         * kph : 54.6054377915134
         * mps : 15.168177164309279
         */
        @SerialName("kph")
        val kph: Double,

        @SerialName("mps")
        val mps: Double,
    )

    @Serializable
    data class Sections(

        /**
         * name :
         * length : 400
         * speedTrap : false
         */
        @SerialName("name")
        val name: String,

        @SerialName("length")
        val length: Int,
    
        @SerialName("speedTrap")
        val speedTrap: Boolean,
    )

    @Serializable
    data class BestLap(
        
        @SerialName("sessionId")
        val sessionId: Int,

        @SerialName("lapNr")
        val lapNr: Int,
    
        @SerialName("duration")
        val duration: String,
    
        @SerialName("speed")
        val speed: Speed,
    )

    @Serializable
    data class Sessions(

        @SerialName("id")
        val id: Int,

        @SerialName("chipId")
        val chipId: Int,

        @SerialName("dateTimeStart")
        val dateTimeStart: String,

        @SerialName("bestLap")
        val bestLap: BestLap,

        @SerialName("aveLapDuration")
        val aveLapDuration: String,

        @SerialName("medianLapDuration")
        val medianLapDuration: String,

        @SerialName("duration")
        val duration: String,

        @SerialName("laps")
        val laps: List<Laps>,
    ) {

        @Serializable
        data class BestLap(
            /**
             * nr : 16
             * duration : 26.371
             * speed : {"kph":54.6054377915134,"mps":15.168177164309279}
             */
            @SerialName("nr")
            val nr: Int,

            @SerialName("duration")
            val duration: String,

            @SerialName("speed")
            val speed: Speed,
        )

        @Serializable
        data class Laps(

            /**
             * nr : 1
             * dateTimeStart : 2017-04-22T12:20:34.553+09:00
             * duration : 29.070
             * speed : {"kph":49.53560371517028,"mps":13.759889920880633}
             * diffPrevLap : null
             * sessionDuration : 29.070
             * status : NONE
             * sections : [{"name":"","duration":"29.070","diffPrevLap":null,"speed":{"kph":49.535603715170275,"mps":13.759889920880632}}]
             * dataAttributes : []
             */
            @SerialName("nr")
            val nr: Int,

            @SerialName("dateTimeStart")
            val dateTimeStart: String,

            @SerialName("duration")
            val duration: String,

            @SerialName("speed")
            val speed: Speed,

            @SerialName("diffPrevLap")
            val diffPrevLap: String?,

            @SerialName("sessionDuration")
            val sessionDuration: String,

            @SerialName("status")
            val status: String,

            @SerialName("sections")
            val sections: List<Sections>,

            @SerialName("dataAttributes")
            val dataAttributes: List<String>,
        ) {

            @Serializable
            data class Sections(

                /**
                 * name :
                 * duration : 29.070
                 * diffPrevLap : null
                 * speed : {"kph":49.535603715170275,"mps":13.759889920880632}
                 */
                @SerialName("name")
                val name: String,

                @SerialName("duration")
                val duration: String,

                @SerialName("diffPrevLap")
                val diffPrevLap: String?,

                @SerialName("speed")
                val speed: Speed,
            )
        }
    }

    @Serializable
    data class Stats(

        /**
         * lapCount : 259
         * fastestTime : 26.371
         * averageTime : 29.387
         * medianTime : 27.189
         * totalTrainingTime : 4:32:57.116
         * activeTrainingTime : 2:06:51.362
         * averageSpeed : {"kph":49.00042856981444,"mps":13.611230158281789}
         * fastestSpeed : {"kph":54.6054377915134,"mps":15.168177164309277}
         * chip : {"code":"8761992","codeNr":8761992,"carId":0,"id":87619920}
         */
        @SerialName("lapCount")
        val lapCount: Int,

        @SerialName("fastestTime")
        val fastestTime: String,

        @SerialName("averageTime")
        val averageTime: String,

        @SerialName("medianTime")
        val medianTime: String,

        @SerialName("totalTrainingTime")
        val totalTrainingTime: String,

        @SerialName("activeTrainingTime")
        val activeTrainingTime: String,

        @SerialName("averageSpeed")
        val averageSpeed: Speed,

        @SerialName("fastestSpeed")
        val fastestSpeed: Speed,

        @SerialName("chip")
        val chip: Chip,
    ) {

        @Serializable
        data class Chip(

            /**
             * code : 8761992
             * codeNr : 8761992
             * carId : 0
             * id : 87619920
             */
            @SerialName("code")
            val code: String,

            @SerialName("codeNr")
            val codeNr: Int,

            @SerialName("carId")
            val carId: Int,

            @SerialName("id")
            val id: Int,
        )
    }
}
