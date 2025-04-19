package jp.ceed.android.mylapslogger.datasource

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.ceed.android.mylapslogger.constants.AppConstants
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.network.response.Sessions
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class SessionsApiDataSource @Inject constructor(
    private val apiPathCreator: MylapsApiPathCreator,
    private val jsonApiKtorClientCreator: JsonApiKtorClientCreator,
) {

    suspend fun getSession(activityId: Long, token: String): Sessions =
        try {
            jsonApiKtorClientCreator.getDefaultClient().get<Sessions>(
                urlString = apiPathCreator.createSessionRequestPath(activityId)
            ) {
                header("apiKey", AppConstants.API_KEY)
                header("Authorization", String.format(Locale.JAPAN, AUTH_FORMAT, token))
                parameter("optimized", "false")
            }
        } catch (e: Exception) {
            throw IOException(e)
        }

    companion object {
        const val AUTH_FORMAT = "bearer %s"
    }
}