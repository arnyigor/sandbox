package presentation.mainform

interface MainFormView {
    fun setUIEnabled(enabled: Boolean)
    fun showFirestoreFilePath(path: String)
    fun setData(absolutePath: String?, cookie: String?, start: String, end: String, url: String?)
}