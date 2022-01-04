package jp.ceed.android.mylapslogger.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uu023938 on 2017/04/26.
 * SessionsResponse
 */

public class SessionsResponse implements Serializable {

    @SerializedName("bestLap")
    public BestLap bestLap;

    @SerializedName("stats")
    public Stats stats;

    @SerializedName("sessions")
    public List<Sessions> sessions;

    @SerializedName("sections")
    public List<SectionsX> sections;

    public static class BestLap {

        /**
         * sessionId : 1
         * lapNr : 16
         * duration : 26.371
         * speed : {"kph":54.6054377915134,"mps":15.168177164309279}
         */

        @SerializedName("sessionId")
        public int sessionId;

        @SerializedName("lapNr")
        public int lapNr;

        @SerializedName("duration")
        public String duration;

        @SerializedName("speed")
        public Speed speed;

        public static class Speed {

            /**
             * kph : 54.6054377915134
             * mps : 15.168177164309279
             */

            @SerializedName("kph")
            public double kph;

            @SerializedName("mps")
            public double mps;
        }
    }

    public static class Stats {

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

        @SerializedName("lapCount")
        public int lapCount;

        @SerializedName("fastestTime")
        public String fastestTime;

        @SerializedName("averageTime")
        public String averageTime;

        @SerializedName("medianTime")
        public String medianTime;

        @SerializedName("totalTrainingTime")
        public String totalTrainingTime;

        @SerializedName("activeTrainingTime")
        public String activeTrainingTime;

        @SerializedName("averageSpeed")
        public AverageSpeed averageSpeed;

        @SerializedName("fastestSpeed")
        public FastestSpeed fastestSpeed;

        @SerializedName("chip")
        public Chip chip;

        public static class AverageSpeed {

            /**
             * kph : 49.00042856981444
             * mps : 13.611230158281789
             */

            @SerializedName("kph")
            public double kph;

            @SerializedName("mps")
            public double mps;
        }

        public static class FastestSpeed {

            /**
             * kph : 54.6054377915134
             * mps : 15.168177164309277
             */

            @SerializedName("kph")
            public double kph;

            @SerializedName("mps")
            public double mps;
        }

        public static class Chip {

            /**
             * code : 8761992
             * codeNr : 8761992
             * carId : 0
             * id : 87619920
             */

            @SerializedName("code")
            public String code;

            @SerializedName("codeNr")
            public int codeNr;

            @SerializedName("carId")
            public int carId;

            @SerializedName("id")
            public int id;
        }
    }

    /**
     * Sessions
     */
    public static class Sessions {

        @SerializedName("id")
        public int id;

        @SerializedName("chipId")
        public int chipId;

        @SerializedName("dateTimeStart")
        public String dateTimeStart;

        @SerializedName("bestLap")
        public BestLapX bestLap;

        @SerializedName("aveLapDuration")
        public String aveLapDuration;

        @SerializedName("medianLapDuration")
        public String medianLapDuration;

        @SerializedName("duration")
        public String duration;

        @SerializedName("laps")
        public List<Laps> laps;

        public static class BestLapX {

            /**
             * nr : 16
             * duration : 26.371
             * speed : {"kph":54.6054377915134,"mps":15.168177164309279}
             */

            @SerializedName("nr")
            public int nr;

            @SerializedName("duration")
            public String duration;

            @SerializedName("speed")
            public SpeedX speed;

            public static class SpeedX {

                /**
                 * kph : 54.6054377915134
                 * mps : 15.168177164309279
                 */

                @SerializedName("kph")
                public double kph;

                @SerializedName("mps")
                public double mps;
            }
        }

        public static class Laps {

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

            @SerializedName("nr")
            public int nr;

            @SerializedName("dateTimeStart")
            public String dateTimeStart;

            @SerializedName("duration")
            public String duration;

            @SerializedName("speed")
            public SpeedXX speed;

            @SerializedName("diffPrevLap")
            public String diffPrevLap;

            @SerializedName("sessionDuration")
            public String sessionDuration;

            @SerializedName("status")
            public String status;

            @SerializedName("sections")
            public List<Sections> sections;

            @SerializedName("dataAttributes")
            public List<?> dataAttributes;

            public static class SpeedXX {

                /**
                 * kph : 49.53560371517028
                 * mps : 13.759889920880633
                 */

                @SerializedName("kph")
                public float kph;

                @SerializedName("mps")
                public double mps;
            }

            public static class Sections {

                /**
                 * name :
                 * duration : 29.070
                 * diffPrevLap : null
                 * speed : {"kph":49.535603715170275,"mps":13.759889920880632}
                 */

                @SerializedName("name")
                public String name;

                @SerializedName("duration")
                public String duration;

                @SerializedName("diffPrevLap")
                public Object diffPrevLap;

                @SerializedName("speed")
                public SpeedXXX speed;

                public static class SpeedXXX {

                    /**
                     * kph : 49.535603715170275
                     * mps : 13.759889920880632
                     */

                    @SerializedName("kph")
                    public double kph;

                    @SerializedName("mps")
                    public double mps;
                }
            }
        }
    }

    public static class SectionsX {

        /**
         * name :
         * length : 400
         * speedTrap : false
         */

        @SerializedName("name")
        public String name;

        @SerializedName("length")
        public int length;

        @SerializedName("speedTrap")
        public boolean speedTrap;
    }
}
