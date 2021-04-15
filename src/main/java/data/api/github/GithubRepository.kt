package data.api.github

import data.api.github.GithubAPI.Companion.provideApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.eclipse.egit.github.core.Authorization
import org.eclipse.egit.github.core.service.GistService
import org.eclipse.egit.github.core.service.OAuthService

class GithubRepository {
    private var auth: Authorization? = null
    private var token: String = "ef1473eabb0e38e9ace56dc5512d594b31b9f712"
    private var headerToken: String = "token $token"

    init {
       // auth = createGistAuth()
    }

    private fun createGistAuth(): Authorization {
        val oauthService = OAuthService()
        oauthService.client.setOAuth2Token(token)
        var auth = Authorization()
        auth.scopes = listOf("gist", "repo", "user")
        auth = oauthService.createAuthorization(auth)
        return auth
    }

    fun test(): Single<String> {
        return provideApi(GithubAPI.BASE_URL, GithubService::class.java)
            .test(headerToken)
            .subscribeOn(Schedulers.io())
    }

    fun getGists() {
        val gistService = GistService()
        gistService.client.setOAuth2Token(auth?.token)
        val gists = gistService.starredGists
        for (gist in gists) {
            println(gist.htmlUrl)
        }
    }
}