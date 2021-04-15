package data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.formattedFileSize
import java.io.File
import kotlin.math.roundToInt

class ApiHelper {
    private val ktor = HttpClient()
    suspend fun downloadFile(
        file: File,
        url: String,
        headers: List<Pair<String, String>>
    ): Flow<DownloadResult> = ktor.downloadFile(file, url, headers)

    private suspend fun HttpClient.downloadFile(
        file: File,
        url: String,
        headersList: List<Pair<String, String>>
    ): Flow<DownloadResult> {
        return flow {
            try {
                /* val httpRequest = HttpRequestBuilder().apply {
                     url(url)
                     headers {
                         for (pair in headersList) {
                             append(pair.first, pair.second)
                         }
                     }
                 }*/
                val response = this@downloadFile.request<HttpResponse> {
                    url(url)
                    method = HttpMethod.Get
                    headers {
                        for (pair in headersList) {
                            append(pair.first, pair.second)
                        }
                    }
                }
                val size = response.contentLength()?.toInt() ?: return@flow
                if (size == 0) return@flow
                val data = ByteArray(size)
                var offset = 0
                do {
                    val currentRead = response.content.readAvailable(data, offset, data.size)
                    offset += currentRead
                    emit(DownloadResult.Progress((offset * 100f / data.size).roundToInt()))
                } while (currentRead > 0)

                if (response.status.isSuccess()) {
                    if (data.isNotEmpty()) {
                        file.writeBytes(data)
                        val formattedFileSize = file.formattedFileSize()
                        emit(DownloadResult.Success("File:$file downloaded with size:$formattedFileSize"))
                    } else {
                        emit(DownloadResult.Success("File:$file downloaded with size:0"))
                    }
                } else {
                    emit(DownloadResult.Error("File not downloaded,status not success"))
                }
            } catch (e: TimeoutCancellationException) {
                emit(DownloadResult.Error("Connection timed out", e))
            } catch (t: org.apache.http.HttpException) {
                emit(DownloadResult.Error("Failed to connect", Exception(t)))
            } catch (t: Throwable) {
                emit(DownloadResult.Error("Error", Exception(t)))
            }
        }
    }
}