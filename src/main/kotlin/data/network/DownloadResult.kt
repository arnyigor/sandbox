package data.network

sealed class DownloadResult {
    data class Success(val message: String) : DownloadResult()

    data class Error(val message: String, val cause: Exception? = null) : DownloadResult()

    data class Progress(val progress: Int): DownloadResult()
}