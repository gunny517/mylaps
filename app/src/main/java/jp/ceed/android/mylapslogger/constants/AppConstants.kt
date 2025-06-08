package jp.ceed.android.mylapslogger.constants

import jp.ceed.android.mylapslogger.BuildConfig

class AppConstants {

	class RequestCode{
		companion object{
			const val REQUEST_LOCATION: Int = 101
		}
	}

	companion object {
		const val API_KEY = "SpeedhiveAndroidApp-f3deaaed-2dbb-41be-a469-bb33be4de434"
		const val IMAGE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.image_provider"
		const val IMG_SUFFIX_JPG = ".jpg"
	}
}