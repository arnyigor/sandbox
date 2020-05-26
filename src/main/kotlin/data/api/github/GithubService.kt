package data.api.github

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface GithubService {
    @GET("repos/octodocs-test/test")
    fun test(@Header("Authorization") authorization: String): Single<String>
}