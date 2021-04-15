package utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.coroutines.*
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext

/**
 * return items from second array wich not includes in first by custom diff in predicate
 * @param newList ArrayList of  T
 * @param second Collection of  T
 * @param predicate function equals
 */
fun <T> arraysDiff(
        newList: ArrayList<T>?,
        oldList: ArrayList<T>,
        fillAll: Boolean = false,
        predicate: (firstItem: T, secondItem: T) -> Boolean
): ArrayList<T> {
    if (newList.isNullOrEmpty()) return oldList
    if (newList.isEmpty() && oldList.isNotEmpty()) return oldList
    val result = arrayListOf<T>()
    val secondTemp = arrayListOf<T>()
    secondTemp.addAll(oldList)
    for (f in newList) {
        var equal = false
        for (s in oldList) {
            if (predicate.invoke(f, s)) {
                secondTemp.remove(s)
                equal = true
                break
            }
        }
        if (!equal) {
//            Log.i("arraysDiff", "getCalendarEvents diff: $f");
            result.add(f)
        }
    }
    if (fillAll) {
        result.addAll(secondTemp)
    }
    return result
}

fun <T> List<T>.isEquals(newList: List<T>?, predicate: ((old: T, new: T) -> Boolean?)? = null): Boolean {
    return arraysStrongEquals(newList, this, predicate)
}

/**
 * return is each item is same and is on the same positions
 * @param newList List of  T
 * @param oldList List of  T
 * @param predicate function equals
 */
fun <T> arraysStrongEquals(
        newList: List<T>?,
        oldList: List<T>,
        predicate: ((old: T, new: T) -> Boolean?)? = null
): Boolean {
    if (newList.isNullOrEmpty()) return false
    if (newList.isEmpty() && oldList.isNotEmpty()) return false
    val tmpNew = ArrayList<T>(newList.size)
    val tmpOld = ArrayList<T>(oldList.size)
    tmpNew.addAll(newList)
    tmpOld.addAll(oldList)
    for (indVal in oldList.withIndex()) {
        val index = indVal.index
        val old = indVal.value
        val new = newList.getOrNull(index)
        if (new != null) {
            if (predicate != null) {
                val invoke = predicate.invoke(old, new)
                if (invoke == true) {
                    tmpNew.remove(new)
                    tmpOld.remove(old)
                }
            } else {
                if (old == new) {
                    tmpNew.remove(new)
                    tmpOld.remove(old)
                }
            }
        }
    }
    tmpOld.trimToSize()
    tmpNew.trimToSize()
    if (tmpOld.isEmpty() && tmpNew.isEmpty()) {
        return true
    }
    return false
}

fun <T> Collection<T>.filterList(predicate: (T) -> Boolean): ArrayList<T> {
    return ArrayList(this.filter(predicate))
}

inline fun <reified T : Any> Collection<T>?.dump(predicate: (cls: T) -> String?): String {
    return dumpArray(this, predicate)
}

inline fun <reified T : Any> dumpArray(collection: Collection<T>?, predicate: (cls: T) -> String?): String {
    var res = ""
    if (collection == null) {
        res += "Collection is null"
        return res
    }
    if (collection.isEmpty()) {
        res += "Collection is empty"
        return res
    }
    for (ind in collection.withIndex()) {
        val index = ind.index
        val value = ind.value
        if (index == 0) {
            res += "${value.javaClass.simpleName}\n"
            res += predicate.invoke(value)
        } else {
            res += "\n"
            res += predicate.invoke(value)
        }
    }
    return res
}

/**
 * Универсальная функция окончаний
 * @param [count] число
 * @param [zero_other] слово с окончанием значения  [count] либо ноль,либо все остальные варианты включая от 11 до 19 (слов)
 * @param [one] слово с окончанием значения  [count]=1 (слово)
 * @param [two_four] слово с окончанием значения  [count]=2,3,4 (слова)
 */
fun getTermination(count: Int, zero_other: String, one: String, two_four: String, concat: Boolean = true): String {
    if (count % 100 in 11..19) {
        return if (concat) "$count $zero_other" else " $zero_other"
    }
    return when (count % 10) {
        1 -> if (concat) "$count $one" else one
        2, 3, 4 -> if (concat) "$count $two_four" else two_four
        else -> if (concat) "$count $zero_other" else zero_other
    }
}

fun String?.isEmpty(): Boolean = this.isNullOrBlank()

fun <T> Collection<T>.copy(): ArrayList<T> {
    val newList = ArrayList<T>()
    newList.addAll(this)
    return newList
}

fun Any?.toJson(): String? {
    return if (this != null) Gson().toJson(this) else null
}

fun <T> Any?.fromJson(cls: Class<T>): T? {
    return Gson().fromJson(this.toString(), cls)
}

fun <T> String?.fromJson(clazz: Class<*>, deserialize: (JsonElement) -> T): T {
    return GsonBuilder()
        .registerTypeAdapter(
            clazz,
            JsonDeserializer { json, _, _ -> deserialize.invoke(json) }
        )
        .create().fromJson<T>(this, clazz)
}

fun <T> Any?.fromJson(type: Type?): T? {
    return Gson().fromJson(this.toString(), type)
}

fun String?.parseLong(): Long? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toLong()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseDouble(): Double? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseInt(): Int? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }
}

/**
 * Extended function to check empty
 */
fun Any?.empty(): Boolean {
    return when {
        this == null -> true
        this is String && this == "null" -> true
        this is String -> this.isBlank()
        this is Iterable<*> -> this.asIterable().none()
        this is List<*> -> this.isEmpty()
        else -> false
    }
}

fun <T> launchAsync(
        block: suspend () -> T,
        onComplete: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onCanceled: (CancellationException) -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        name: String = "",
        context: CoroutineContext = Dispatchers.Default + SupervisorJob() + CoroutineName(name)
): Job {
    return CoroutineScope(context).launch {
        try {
            val result = withContext(dispatcher) { block.invoke() }
            onComplete.invoke(result)
        } catch (e: CancellationException) {
            val jobName: String = if (name.isBlank()) this@launch.toString() else name
            println("launchAsync: $jobName Job was cancelled")
            onCanceled.invoke(e)
        } catch (e: Exception) {
            onError.invoke(e)
        }
    }
}

fun Job.addTo(compositeJob: CompositeJob) {
    compositeJob.add(this)
}

fun <T> CoroutineScope.launch(
        block: suspend (CoroutineScope) -> T,
        onError: (Throwable) -> Unit = {},
        onCanceled: () -> Unit = {}
): Job {
    return this.launch {
        try {
            block.invoke(this)
        } catch (e: CancellationException) {
            onCanceled()
        } catch (e: Exception) {
            onError(e)
        }
    }
}


fun getThread(): String? {
    return Thread.currentThread().name
}

fun getHexColor(color: Int): String {
    return String.format("#%06X", (0xFFFFFF and color))
}

fun getPercent(current: Int, total: Int, start: Int? = null): Double {
    return getPercent(current.toDouble(), total.toDouble(), start?.toDouble(), 2)
}

private fun getPercent(
        current: Double,
        total: Double,
        start: Double? = null,
        scale: Int = 2
): Double {
    try {
        if (start != null) {
            return ((current - start) / (total.minus(start))) * 100.0
        }
        return (current / total) * 100.0
    } catch (e: Exception) {
        return 0.0
    }
}