package presentation.firebasefirestore

interface FirebaseFormView {
    fun showError(error: String?)
    fun setData(data: String?)
    fun setLoading(loading: Boolean)
    fun showSuccess(message: String)
    fun setPathText(message: String)
}