package rx

import interfaces.Testable
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class CompletableTests : Testable {
    @Volatile
    private var migrateResult: String = ""

    override fun runTest(args: Array<String>?) {
        println("Migrate test started")
        migrate()
                .andThen(runNext())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe({
                    println("Migrate test complete")
                    println("migrateResult:$migrateResult")
                }, { it.printStackTrace() })
    }

    private fun migratingProgress() {
        println("Migrating")
        migrateResult = when {
            migrateResult.isBlank() -> "Migrated"
            migrateResult == "Migrated" -> "Error"
            migrateResult == "Error" -> "Error"
            else -> ""
        }
        println("Migrating is complete")
    }

    private fun migrate(): Completable {
        return Completable.fromAction {
            migratingProgress()
        }
    }

    private fun runNext(): Completable {
        return Completable.fromAction {
            println("RunNext started")
            val b = when {
                migrateResult.isBlank() -> false
                migrateResult == "Migrated" -> true
                migrateResult == "Error" -> false
                else -> false
            }
            println("RunNext finished result:$b")
            println("----------------------------")
        }
    }

}