package jp.ceed.android.mylapslogger.datasource

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.R
import javax.inject.Inject

class MylapsApiPathCreator @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun createActivitiesRequestPath(uerId: String): String =
        Uri.parse(context.getString(R.string.practice_api_end_point))
            .buildUpon()
            .path(context.getString(R.string.activities_request_path, uerId))
            .build()
            .toString()
}