package jp.ceed.android.mylapslogger.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import javax.inject.Inject

class JsonApiKtorClientCreator @Inject constructor() {

    private val client: HttpClient = HttpClient(Android){
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                }
            )
        }
    }

    fun get() = client

}