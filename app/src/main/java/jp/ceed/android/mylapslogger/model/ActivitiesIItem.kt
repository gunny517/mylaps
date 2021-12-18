package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.util.Util

class ActivitiesIItem(dto: ActivitiesResponse.ActivityDto){

	var startTime: String = Util.convertTo(Util.API_TIME_FORMAT, FORMAT, dto.startTime)

	var place: String = dto.location.name


	companion object{
		const val FORMAT = "yyyy/MM/dd"
	}
}