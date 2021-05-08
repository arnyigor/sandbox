package presentation.firestore

interface FirebaseFormView {
    fun showError(error: String?)
    fun setData(data: String?)
    fun setLoading(loading: Boolean)
    fun showSuccess(message: String)
    fun setPathText(message: String)
    fun setCollections(list: List<String>)
    fun setDocs(docs: List<String>)
    fun setKeys(keys: List<String>)
    fun setKeyValue(keyValue: String?)
}