package presentation.videoencoding

interface VideoEncodingView {
    fun setUIEnabled(enabled: Boolean)
    fun setButton3Title(title: String)
    fun showResult(result: String)
    fun setLabel1Text(text: String)
    fun setLabel2Text(text: String)
    fun showError(message: String?)
}
