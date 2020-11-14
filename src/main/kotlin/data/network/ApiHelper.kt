package data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.OutputStream
import kotlin.math.roundToInt


class ApiHelper {
    private val ktor = HttpClient()
    suspend fun downloadFile(file: File, url: String, headers: List<Pair<String, String>>) {
        ktor.downloadFile(file.outputStream(), url, headers)
    }

    suspend fun HttpClient.downloadFile(
        file: OutputStream,
        url: String,
        headersList: List<Pair<String, String>>
    ): Flow<DownloadResult> {
        return flow {
            try {
                val response = call {
                    url {
                        takeFrom(url)
                    }
                    method = HttpMethod.Get
                    headers {
                        for (pair in headersList) {
                            append(pair.first, pair.second)
                        }
                    }
                }.response

                val data = ByteArray(response.contentLength()!!.toInt())
                var offset = 0

                do {
                    val currentRead = response.content.readAvailable(data, offset, data.size)
                    offset += currentRead
                    val progress = (offset * 100f / data.size).roundToInt()
                    emit(DownloadResult.Progress(progress))
                } while (currentRead > 0)

                response.close()

                if (response.status.isSuccess()) {
                    withContext(Dispatchers.IO) {
                        file.write(data)
                    }
                    emit(DownloadResult.Success)
                } else {
                    emit(DownloadResult.Error("File not downloaded"))
                }
            } catch (e: TimeoutCancellationException) {
                emit(DownloadResult.Error("Connection timed out", e))
            } catch (t: Throwable) {
                emit(DownloadResult.Error("Failed to connect"))
            }
        }
    }
}