package jp.ceed.android.mylapslogger.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uu023938 on 2017/04/26.
 *
 */

public class ActivitiesResponse implements Serializable {


	/**
	 * activities : [{"id":686369883,"name":"Practice","startTime":"2017-04-22T12:20:34+09:00","endTime":"2017-04-22T16:53:31+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686286320,"name":"Practice","startTime":"2017-04-02T09:23:54+09:00","endTime":"2017-04-02T12:54:51+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686279740,"name":"Practice","startTime":"2017-04-01T13:09:52+09:00","endTime":"2017-04-01T17:39:56+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686253272,"name":"Practice","startTime":"2017-03-25T11:49:17+09:00","endTime":"2017-03-25T16:59:34+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686225095,"name":"Practice","startTime":"2017-03-18T11:10:58+09:00","endTime":"2017-03-18T16:54:37+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686194148,"name":"Practice","startTime":"2017-03-11T12:45:09+09:00","endTime":"2017-03-11T17:02:31+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686159969,"name":"Practice","startTime":"2017-03-04T12:45:41+09:00","endTime":"2017-03-04T16:59:57+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686133186,"name":"Practice","startTime":"2017-02-26T09:18:14+09:00","endTime":"2017-02-26T12:32:53+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686127445,"name":"Practice","startTime":"2017-02-25T12:59:43+09:00","endTime":"2017-02-25T18:19:58+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":686096168,"name":"Practice","startTime":"2017-02-18T12:20:32+09:00","endTime":"2017-02-18T16:59:13+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":683348632,"name":"Practice","startTime":"2017-02-04T15:46:34+09:00","endTime":"2017-02-04T17:00:23+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":683295894,"name":"Practice","startTime":"2017-01-21T13:20:40+09:00","endTime":"2017-01-21T17:01:29+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null},{"id":683271767,"name":"Practice","startTime":"2017-01-15T11:28:31+09:00","endTime":"2017-01-15T16:58:44+09:00","frameType":0,"location":{"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]},"accountId":0,"gaUId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","userId":"MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d","chipLabel":"MasaruAraki","chipListDto":null,"practiceSessionsDto":null}]
	 * activityCount : 13
	 */

	@SerializedName("activityCount")
	public int activityCount;

	@SerializedName("activities")
	public List<ActivityDto> activities;

	public static class ActivityDto {

		/**
		 * id : 686369883
		 * name : Practice
		 * startTime : 2017-04-22T12:20:34+09:00
		 * endTime : 2017-04-22T16:53:31+09:00
		 * frameType : 0
		 * location : {"name":"中井インターサーキット","id":1374,"description":"","url":"","trackLength":400,"status":"OFFLINE","sport":"Karting","country":"jp","x2ServiceId":0,"ipAddress":"","sections":[{"name":"","length":400,"speedTrap":false}]}
		 * accountId : 0
		 * gaUId : MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d
		 * userId : MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d
		 * chipLabel : MasaruAraki
		 * chipListDto : null
		 * practiceSessionsDto : null
		 */

		@SerializedName("id")
		public int id;

		@SerializedName("name")
		public String name;

		@SerializedName("startTime")
		public String startTime;

		@SerializedName("endTime")
		public String endTime;

		@SerializedName("frameType")
		public int frameType;

		@SerializedName("location")
		public Location location;

		@SerializedName("accountId")
		public int accountId;

		@SerializedName("gaUId")
		public String gaUId;

		@SerializedName("userId")
		public String userId;

		@SerializedName("chipLabel")
		public String chipLabel;

		@SerializedName("chipListDto")
		public Object chipListDto;

		@SerializedName("practiceSessionsDto")
		public Object practiceSessionsDto;

		public static class Location {

			/**
			 * name : 中井インターサーキット
			 * id : 1374
			 * description :
			 * url :
			 * trackLength : 400
			 * status : OFFLINE
			 * sport : Karting
			 * country : jp
			 * x2ServiceId : 0
			 * ipAddress :
			 * sections : [{"name":"","length":400,"speedTrap":false}]
			 */

			@SerializedName("name")
			public String name;

			@SerializedName("id")
			public int id;

			@SerializedName("description")
			public String description;

			@SerializedName("url")
			public String url;

			@SerializedName("trackLength")
			public int trackLength;

			@SerializedName("status")
			public String status;

			@SerializedName("sport")
			public String sport;

			@SerializedName("country")
			public String country;

			@SerializedName("x2ServiceId")
			public int x2ServiceId;

			@SerializedName("ipAddress")
			public String ipAddress;

			@SerializedName("sections")
			public List<Sections> sections;

			public static class Sections {

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
	}
}
