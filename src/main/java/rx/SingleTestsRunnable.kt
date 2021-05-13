package rx

import interfaces.Testable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import rx.testmodels.Account
import rx.testmodels.CreditRequest
import java.io.File

class SingleTestsRunnable : Testable {
    private val activeClientObservable = BehaviorSubject.create<Any>()
    override fun runTest(args: Array<String>?) {
        Single.just("1")
            .observeOn(Schedulers.computation())
            .doOnSuccess{ println("thread:${Thread.currentThread()}") }
            .subscribeOn(Schedulers.io())
            .subscribe({
                println("res:$it")
            },{

            })
    }

    fun getDebitAccount(accountNumber: String, list: List<Account>): Single<Account> {
        return Observable.fromIterable(list)
            .filter { account: Account -> account.number == accountNumber }
            .firstElement()
            .switchIfEmpty(Single.error(Throwable("error no account found")))
            .subscribeOn(Schedulers.io())
    }

    fun schedullersTest() {
        Single.just("Start value")
            .doOnSuccess {
                println("Thread 1:${Thread.currentThread()}")
            }
            .subscribeOn(Schedulers.single())
            .subscribe({
                println(it)
            }, {
                it.printStackTrace()
            })
    }

    private fun longTimeEventWithMaybeError(task: String, time: Long, error: String? = null): String {
        println("longTimeEventWithMaybeError task:$task ${printThread()}, sleep time:$time,has error:${!error.isNullOrBlank()}")
        Thread.sleep(time)
        error.takeIf { it.isNullOrBlank().not() }?.let { error(it) }
        return task
    }

    private fun printThread() = "current thread:${Thread.currentThread().name}"

    private fun mergeTest() {
        Single.merge(firstSingle(), secondSingle(), errorSingle("error from merge"))
            .observeOn(Schedulers.computation())
            .subscribe({ result ->
                println("mergeTest result ${printThread()}->:$result complete")
            }, {
                it.printStackTrace()
                println("mergeTest error:${it.message}")
            })
    }

    private fun errorTest() {
        getDebitAccount("0", listOf(Account("1"), Account("2")))
            .observeOn(Schedulers.computation())
            .subscribe({ result ->
                println("mergeTest result ${printThread()}->:$result complete")
            }, {
                it.printStackTrace()
                println("mergeTest error:${it.message}")
            })
    }

    private fun zipTest() {
        firstSingle()
            .zipWith(secondSingle())
            .zipWith(errorSingle("error from zip"))
            .observeOn(Schedulers.computation())
            .subscribe({ result ->
                println("mergeTest result ${printThread()}->:$result complete")
            }, {
                println("mergeTest error:${it.message}")
            })
    }

    private fun errorSingle(err: String? = null) =
        Single.fromCallable { longTimeEventWithMaybeError("error task", 6000, err) }
            .observeOn(Schedulers.io())

    private fun secondSingle() = Single.fromCallable { longTimeEventWithMaybeError("second task", 4000) }
        .observeOn(Schedulers.io())

    private fun firstSingle() = Single.just(longTimeEventWithMaybeError("first task", 2000))
        .observeOn(Schedulers.io())

    private fun returnNullable(): String? {
        val random = (0..1).random()
        if (random == 1) {
            return "random:$random, Not nullable"
        }
        return null
    }

    private fun meybeTest() {
        Maybe.fromCallable {
            val returnNullable = returnNullable()
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