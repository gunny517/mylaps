package jp.ceed.android.mylapslogger.datasource

import io.ktor.client.request.*
import jp.ceed.android.mylapslogger.constants.AppConstants
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.network.response.Activities
import java.io.IOException
import javax.inject.Inject

class ActivitiesApiDataSource @Inject constructor(
    private val apiPathCreator: MylapsApiPathCreator,
    private val jsonApiKtorClientCreator: JsonApiKtorClientCreator,
) {

    suspend fun getActivities(userId: String): Activities =
        try {
            val jsonApiClient = jsonApiKtorClientCreator.getDefaultClient()
            jsonApiClient.get(
                urlString = apiPathCreator.createActivitiesRequestPath(
                    uerId = userId
                )
            ) {
                header("apiKey", AppConstants.API_KEY)
            }
        } catch (e: Exception) {
            throw IOException("Activities loading failed.")
        }
}