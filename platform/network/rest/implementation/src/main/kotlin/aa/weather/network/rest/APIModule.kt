package aa.weather.network.rest

import aa.weather.network.rest.api.APIConfiguration
import aa.weather.network.rest.api.APIFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object APIModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideAPIFactory(apiConfiguration: () -> APIConfiguration): APIFactory =
        apiConfiguration()
            .let { configValue ->
                Retrofit.Builder()
                    .baseUrl(
                        configValue.baseUrl
                            .toHttpUrl()
                            .newBuilder()
                            .encodedPath("/v1/")
                            .build()
                    )
                    .addConverterFactory(
                        Json { ignoreUnknownKeys = true }
                            .asConverterFactory("application/json".toMediaType())
                    )
                    .client(createClient(configValue.key))
                    .build()
            }
            .let {
                object : APIFactory {
                    override fun <T> createRESTAPI(apiClass: Class<T>): T =
                        it.create(apiClass)
                }
            }

    private fun createClient(apiKey: String) =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .url(
                        chain
                            .request()
                            .url
                            .newBuilder()
                            .addQueryParameter("key", apiKey)
                            .build()
                    )
                    .build()
                    .let(chain::proceed)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.HEADERS) }
            )
            .build()
}
