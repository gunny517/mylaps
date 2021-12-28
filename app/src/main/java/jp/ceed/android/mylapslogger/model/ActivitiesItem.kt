package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.util.Util

class ActivitiesItem(dto: ActivitiesResponse.ActivityDto){

	val sessionId: Int = dto.id

	val startTime: String = Util.toYmdFormatFromDateTime(dto.startTime)

	val place: String = dto.location.name


	companion object{
		const val FORMAT = "yyyy/MM/dd"
	}
}