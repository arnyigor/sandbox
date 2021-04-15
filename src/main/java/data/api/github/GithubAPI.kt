package data.api.github

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class GithubAPI {
    companion object {
        const val BASE_URL = "https://api.github.com"

        fun <T> provideApi(
            baseUrl: String,
            clazz: Class<T>,
            httpLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC,
            timeout: Long = 30L
        ): T {
            val httpClient = OkHttpClient.Builder()
            val gson = GsonBuilder().setLenient().create()
            httpClient.connectTimeout(timeout, TimeUnit.SECONDS)
            httpClient.readTimeout(timeout, TimeUnit.SECONDS)
            httpClient.writeTimeout(timeout, TimeUnit.SECONDS)
            httpClient.followRedirects(true)
            httpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1))
            httpClient.addInterceptor(HttpLoggingInterceptor().apply { level = httpLevel })
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
        }
    }
}
