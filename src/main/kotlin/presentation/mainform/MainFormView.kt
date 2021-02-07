package presentation.mainform

interface MainFormView {
    fun setUIEnabled(enabled: Boolean)
    fun showFirestoreFilePath(path: String)
}