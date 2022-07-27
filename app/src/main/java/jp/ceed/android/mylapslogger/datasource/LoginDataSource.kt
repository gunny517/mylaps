package jp.ceed.android.mylapslogger.datasource

import android.content.Context
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.network.response.LoginResponse
import java.io.IOException
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    val jsonApiKtorClientCreator: JsonApiKtorClientCreator,
)  {

    suspend fun requestLogin(userName: String, password: String): LoginResponse {
        return try {
            jsonApiKtorClientCreator.getDefaultClient().submitForm<LoginResponse>(
                url = context.getString(R.string.oauth_url),
                formParameters = Parameters.build {
                    append("grant_type", GRANT_TYPE_PASSWORD)
                    append("username", userName)
                    append("password", password)
                }
            ) {
                header("Authorization", createAuthHeader())
            }
        } catch (e: Exception) {
            throw IOException("login failed.")
        }
    }

    private fun createAuthHeader(): String {
        val user = context.getString(R.string.oauth_basic_auth_user)
        val pass = context.getString(R.string.oauth_basic_auth_pass)
        val credentials = "$user:$pass"
        return "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
    }

    companion object {
        const val GRANT_TYPE_PASSWORD = "password"
    }
}