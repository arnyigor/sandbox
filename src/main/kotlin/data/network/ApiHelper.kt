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
import utils.formattedFileSize
import java.io.File
import kotlin.math.roundToInt


class ApiHelper {
    private val ktor = HttpClient()
    suspend fun downloadFile(file: File, url: String, headers: List<Pair<String, String>>): Flow<DownloadResult> {
        return ktor.downloadFile(file, url, headers)
    }

    private suspend fun HttpClient.downloadFile(
        file: File,
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

                val contentLength = response.contentLength()
                if (contentLength == null) {
                    response.close()
                    return@flow
                }
                val size = contentLength.toInt()
                if (size == 0) {
                    response.close()
                    return@flow
                }
                val data = ByteArray(size)
                var offset = 0
                do {
                    val currentRead = response.content.readAvailable(data, offset, data.size)
                    offset += currentRead
                    emit(DownloadResult.Progress((offset * 100f / data.size).roundToInt()))
                } while (currentRead > 0)

                response.close()

                if (response.status.isSuccess()) {
                    if (data.isNotEmpty()) {
                        withContext(Dispatchers.Unconfined) {
                            file.outputStream().use { it.write(data) }
                        }
                        val formattedFileSize = file.formattedFileSize()
                        emit(DownloadResult.Success("File:$file downloaded with size:$formattedFileSize"))
                    }
                    emit(DownloadResult.Success("File:$file downloaded with size:0"))
                } else {
                    emit(DownloadResult.Error("File not downloaded"))
                }
            } catch (e: TimeoutCancellationException) {
                emit(DownloadResult.Error("Connection timed out", e))
            } catch (t: Throwable) {
                emit(DownloadResult.Error("Failed to connect", Exception(t)))
            }
        }
    }
}