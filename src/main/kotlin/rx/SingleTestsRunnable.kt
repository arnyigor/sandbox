package rx

import interfaces.Testable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import rx.testmodels.Account
import rx.testmodels.CreditRequest
import java.io.File

class SingleTestsRunnable : Testable {
    private val activeClientObservable = BehaviorSubject.create<Any>()
    override fun runTest(args: Array<String>?) {
        meybeTest()
    }

    private fun returnNullable(): String? {
        val random = (0..1).random()
        if (random == 1) {
            return "random:$random, Not nullable"
        }
        return null
    }

    private fun meybeTest() {
        Maybe.fromCallable { val returnNullable = returnNullable()
            println("returnNullable:$returnNullable")
            returnNullable
        }
                .subscribe({
                    println("subscribe result:$it")
                }, {
                    println("error result:" + it.message)
                    it.printStackTrace()
                }, {
                    println("complete result:")
                })
    }

    private fun createPhotoFile(): Single<File?> {
        return Single.fromCallable {
            val storageDir = File(System.getProperty("user.dir"))
            if (false) {
                File.createTempFile("doc_", ".jpg", storageDir)
            } else {
                null
            }
        }.subscribeOn(Schedulers.io())

    }

    private fun prepareBkiRequest(): Maybe<CreditRequest> {
        return waitActiveClient()
                .toMaybe<Any>()
                .flatMap { Maybe.fromCallable(this::getAccounts) }
                .filter { it.isNotEmpty() }
                .map { accounts ->
                    val account = accounts[0]
                    CreditRequest(account.number, account.code)
                }
    }

    private fun filterTest(): Single<String> {
        return Single.just(listOf("11", "23", "344"))
                .flatMapObservable { Observable.fromIterable(it) }
                .filter { it.length == 4 }
                .firstOrError()

    }

    private fun waitActiveClient(): Completable {
        return activeClientObservable
                .firstOrError()
                .ignoreElement();
    }

    private fun getAccounts(): List<Account> {
        return emptyList()
    }
}