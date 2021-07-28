package ui.firebase

import androidx.compose.animation.animateColorAsState
import androidx.compose.desktop.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.firestore.FirebaseFirestorePresenter
import presentation.firestore.FirebaseFormView
import utils.fileChoose
import java.awt.image.BufferedImage
import javax.swing.JOptionPane


fun openFireabaseComposeWindow() {
    Window(
        title = "FirebaseFirestore",
        resizable = false,
        size = IntSize(1200, 800),
    ) {
        val window = LocalAppWindow.current
        val current = AppManager.focusedWindow
        val composePanel = ComposePanel()
        var errorMsg by mutableStateOf("")
        var successMsg by mutableStateOf("")
        var showLoading by mutableStateOf(true)
        var collections by mutableStateOf<List<String>>(emptyList())
        var docsCollection by mutableStateOf<List<String>>(emptyList())
        var docFields by mutableStateOf<List<String>>(emptyList())
        val onlyDocId: MutableState<Boolean> = remember { mutableStateOf(false) }
        val docid: MutableState<String> = remember { mutableStateOf("") }
        val dataResult: MutableState<String> = remember { mutableStateOf("") }
        val edtFieldValue: MutableState<String> = remember { mutableStateOf("") }
        val edtFieldKeyValue: MutableState<String> = remember { mutableStateOf("") }
        val pathString: MutableState<String> = remember { mutableStateOf("Path:") }
        val selectedCollectionsIndex: MutableState<Int> = remember { mutableStateOf(0) }
        val selectedDocsIndex: MutableState<Int> = remember { mutableStateOf(0) }
        val selectedDocFieldsIndex: MutableState<Int> = remember { mutableStateOf(0) }
        val enabled: MutableState<Boolean> = remember { mutableStateOf(false) }
        val view = object : FirebaseFormView {
            override fun showError(error: String?) {
                println("!!!!Error!!!!:$error")
                showLoading = false
//                errorMsg = error ?: ""
                JOptionPane.showMessageDialog(composePanel, error, "Внимание!", JOptionPane.ERROR_MESSAGE)
            }

            override fun setData(data: String?) {
                dataResult.value = ""
                dataResult.value = data ?: ""
            }

            override fun setLoading(loading: Boolean) {
                showLoading = loading
                enabled.value = !loading
                current?.window?.isEnabled = !loading
            }

            override fun showSuccess(message: String) {
                println("showSuccess:$message")
                showLoading = false
                successMsg = message
//                JOptionPane.showMessageDialog(composePanel, message, "Успешно!", JOptionPane.INFORMATION_MESSAGE)
            }

            override fun setPathText(message: String) {
                pathString.value = "Path:$message"
            }

            override fun setCollections(list: List<String>) {
                collections = list
            }

            override fun setDocs(docs: List<String>) {
                docsCollection = docs
            }

            override fun setKeys(keys: List<String>) {
                docFields = keys
            }

            override fun setKeyValue(keyValue: String?) {
                edtFieldValue.value = keyValue ?: ""
            }
        }
        val presenter = FirebaseFirestorePresenter(view)
        MaterialTheme {
            val baseModifier = Modifier.padding(8.dp)
            val textSize = 12.sp
            //error
            AlertDialog(
                errorMsg.isBlank().not(),
                content = errorMsg,
                undecorated = false,
            )
            //loading
            AlertDialog(
                showLoading,
                content = "Подождите,идет загрузка",
            )
            ConfirmDialog(
                mutableStateOf(successMsg.isNotBlank()),
                content = successMsg,
                onConfirmBtnTitle = "Ok"
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // path
                Row {
                    btnClickable(
                        btnName = "Save",
                        enabled = enabled,
                        modifier = baseModifier,
                        textUnit = textSize
                    ) {
                        presenter.savePathSettings()
                    }
                    SimpleText(
                        displayText = pathString.value,
                        modifier = baseModifier
                            .clickable {
                                composePanel.fileChoose { path ->
                                    if (path != null) {
                                        presenter.initFirestore(path)
                                    }
                                }
                            },
                        textUnit = textSize
                    )
                }
                // collections and docs
                Row {
                    Column {
                        Spacer(Modifier.size(8.dp))
                        Row {
                            btnClickable(
                                btnName = "Load collection",
                                enabled = enabled,
                                modifier = baseModifier.align(Alignment.CenterVertically),
                                textUnit = 14.sp
                            ) {
                                collections.getOrNull(selectedCollectionsIndex.value)
                                    ?.let { presenter.loadCollectionData(it, onlyDocId.value) }
                            }
                            SimpleText(
                                displayText = "Collection:",
                                modifier = baseModifier.align(Alignment.CenterVertically),
                                textUnit = textSize
                            )
                            Dropdown(
                                items = collections,
                                selectedIndex = selectedCollectionsIndex,
                                modifier = baseModifier.align(Alignment.CenterVertically).background(Color.White),
                                textUnit = textSize
                            )
                        }
                        Spacer(Modifier.size(8.dp))
                        Row {
                            btnClickable(
                                btnName = "Load doc",
                                enabled = enabled,
                                modifier = baseModifier.align(Alignment.CenterVertically),
                                textUnit = 14.sp
                            ) {
                                presenter.loadDocumentData(
                                    collections.getOrNull(selectedCollectionsIndex.value) ?: "",
                                    docsCollection.getOrNull(selectedDocsIndex.value) ?: ""
                                )
                            }
                            SimpleText(
                                displayText = "Docs:",
                                modifier = baseModifier.align(Alignment.CenterVertically),
                                textUnit = 14.sp
                            )
                            Dropdown(
                                items = docsCollection,
                                selectedIndex = selectedDocsIndex,
                                modifier = baseModifier.align(Alignment.CenterVertically).background(Color.White),
                                textUnit = 14.sp
                            )
                        }
                        Spacer(Modifier.size(8.dp))
                        Row {
                            CheckboxComponent(
                                state = onlyDocId,
                                modifier = baseModifier.align(Alignment.CenterVertically)
                            )
                            SimpleText(
                                displayText = "Только id документа",
                                modifier = baseModifier.align(Alignment.CenterVertically),
                                textUnit = 14.sp
                            )
                            Spacer(Modifier.size(8.dp))
                            SimpleTextField(
                                label = "DocId",
                                text = docid,
                                modifier = baseModifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Spacer(Modifier.size(8.dp))
                    Column {
                        Row {
                            Column {
                                Row {
                                    SimpleText(
                                        displayText = "Fields:",
                                        modifier = baseModifier,
                                        textUnit = 14.sp
                                    )
                                    Dropdown(
                                        items = docFields,
                                        selectedIndex = selectedDocFieldsIndex,
                                        modifier = baseModifier.background(Color.White),
                                        textUnit = textSize,
                                        onChange = {
                                            presenter.onKeyChanged(it)
                                        }
                                    )
                                }
                                Spacer(Modifier.size(8.dp))
                                SimpleTextField(
                                    "FieldKey",
                                    edtFieldKeyValue,
                                    modifier = baseModifier
                                )
                            }
                            Spacer(Modifier.size(8.dp))
                            SimpleTextField(
                                "",
                                edtFieldValue,
                                modifier = baseModifier.height(200.dp)
                            )
                        }
                    }
                }
                Row {
                    btnClickable("Send", enabled, modifier = baseModifier, textUnit = textSize) {
                        sendData(
                            presenter = presenter,
                            collection = collections.getOrNull(selectedCollectionsIndex.value),
                            docId = docid.value,
                            doc = docsCollection.getOrNull(selectedDocsIndex.value),
                            edtFieldKey = edtFieldKeyValue.value,
                            edtFieldValue = edtFieldValue.value,
                            docField = docFields.getOrNull(selectedDocFieldsIndex.value)
                        )
                    }
                    btnClickable("Duplicate", enabled, modifier = baseModifier, textUnit = textSize) {
                        duplicateData(
                            presenter = presenter,
                            collection = collections.getOrNull(selectedCollectionsIndex.value),
                            docId = docid.value,
                            doc = docsCollection.getOrNull(selectedDocsIndex.value),
                            onlyDocId = onlyDocId.value
                        )
                    }
                    btnClickable("Remove Doc", enabled, modifier = baseModifier, textUnit = textSize) {
                        removeDoc(
                            presenter = presenter,
                            collection = collections.getOrNull(selectedCollectionsIndex.value),
                            docId = docid.value,
                            onlyDocId = onlyDocId.value
                        )
                    }
                    btnClickable("Remove Field", enabled, modifier = baseModifier, textUnit = textSize) {
                        removeField(
                            presenter = presenter,
                            collection = collections.getOrNull(selectedCollectionsIndex.value),
                            docId = docid.value,
                            edtKey = edtFieldKeyValue.value
                        )
                    }
                }
                SimpleTextField(
                    "Data",
                    dataResult,
                    modifier = baseModifier.fillMaxSize()
                )
            }
        }
        presenter.loadSettings()
    }
}

private fun sendData(
    presenter: FirebaseFirestorePresenter,
    collection: String?,
    docId: String?,
    doc: String?,
    edtFieldKey: String,
    edtFieldValue: String,
    docField: String?
) {
    if (!collection.isNullOrBlank()) {
        var edtKeyText: String = edtFieldKey
        val edtValueText: String = edtFieldValue
        if (edtKeyText.isEmpty()) {
            if (!docField.isNullOrBlank()) {
                edtKeyText = docField
            }
        }
        var document = docId
        if (docId.isNullOrBlank() && !doc.isNullOrBlank()) {
            document = doc
        }
        val dialogResult = JOptionPane.showConfirmDialog(
            null,
            String.format("Отправить данные ключ:%s->значение:%s для документа %s?", edtKeyText, edtValueText, document)
        )
        if (dialogResult == JOptionPane.YES_OPTION) {
            presenter.sendData(
                edtCollectionText = collection,
                document = document ?: "",
                edtKeyText = edtKeyText,
                edtValueText = edtValueText
            )
        }
    }
}

private fun duplicateData(
    presenter: FirebaseFirestorePresenter,
    collection: String?,
    docId: String?,
    doc: String?,
    onlyDocId: Boolean,
) {
    if (!collection.isNullOrBlank()) {
        val dialogResult = JOptionPane.showConfirmDialog(
            null,
            String.format("Дублировать данные? коллекция->%s, Документ:%s,новый документ:%s", collection, doc, docId)
        )
        if (dialogResult == JOptionPane.YES_OPTION) {
            presenter.duplicateData(
                edtCollectionText = collection, onlyDocId, docId ?: ""
            )
        }
    }
}

private fun removeDoc(
    presenter: FirebaseFirestorePresenter,
    collection: String?,
    docId: String?,
    onlyDocId: Boolean,
) {
    if (!collection.isNullOrBlank()) {
        val documentText: String = docId ?: ""
        val dialogResult = JOptionPane.showConfirmDialog(null, String.format("Удалить документ %s?", documentText))
        if (dialogResult == JOptionPane.YES_OPTION) {
            presenter.removeDocument(collection, documentText, onlyDocId)
        }
    }
}

private fun removeField(
    presenter: FirebaseFirestorePresenter,
    collection: String?,
    docId: String?,
    edtKey: String?,
) {
    if (!collection.isNullOrBlank()) {
        val documentText: String = docId ?: ""
        val key: String = edtKey ?: ""
        val dialogResult =
            JOptionPane.showConfirmDialog(null, String.format("Удалить поле %s документа в id=%s?", key, documentText))
        if (dialogResult == JOptionPane.YES_OPTION) {
            presenter.removeField(collection, documentText, key)
        }
    }
}

@Composable
fun CheckboxComponent(state: MutableState<Boolean>, modifier: Modifier) {
    Checkbox(
        modifier = modifier,
        checked = state.value,
        onCheckedChange = { checked ->
            state.value = checked
        }
    )
}

@Composable
fun SimpleTextField(label: String, text: MutableState<String>, modifier: Modifier) {
    OutlinedTextField(
        modifier = modifier,
        value = text.value,
        onValueChange = { text.value = it },
        label = {
            if (label.isNotBlank()) {
                Text(label)
            }
        }
    )
}

@Composable
fun SimpleText(displayText: String, modifier: Modifier, textUnit: TextUnit) {
    Text(text = displayText, modifier = modifier, fontSize = textUnit)
}

@Composable
fun Dropdown(
    items: List<String> = emptyList(),
    selectedIndex: MutableState<Int>,
    modifier: Modifier,
    textUnit: TextUnit,
    onChange: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.wrapContentSize(Alignment.BottomCenter)
        .clickable {
            expanded = true
        }) {
        Card(modifier = Modifier.border(1.dp, Color.Black)) {
            Text(
                text = items.getOrNull(selectedIndex.value) ?: "-",
                modifier = modifier.padding(10.dp), fontSize = textUnit
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
        ) {
            items.forEachIndexed { index, s ->
                if (index != 0) {
                    Divider()
                }
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                    onChange(s)
                }) {
                    Text(text = s, fontSize = textUnit)
                }
            }
        }
    }
}

@Composable
private fun btnClickable(
    btnName: String,
    enabled: MutableState<Boolean>,
    modifier: Modifier,
    textUnit: TextUnit,
    onClick: () -> Unit = {}
) {
    Button(
        enabled = enabled.value,
        modifier = modifier,
        onClick = {
            onClick()
        }) {
        Text(btnName, fontSize = textUnit)
    }
}

private fun dialog(error: String) {
    AppWindow(
        size = IntSize(250, 250),
        icon = getMyAppIcon(),
        resizable = false
    ).show {
        Text(
            error,
            style = typography.h4,
            modifier = Modifier.padding(8.dp).clickable {
                AppManager.focusedWindow?.close()
            })
    }
}

fun getMyAppIcon(): BufferedImage {
    val size = 256
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.color = java.awt.Color(125, 125, 125)
    graphics.fillOval(0, 0, size, size)
    graphics.dispose()
    return image
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialog(
    showDialog: Boolean,
    title: String = "",
    content: String = "",
    undecorated: Boolean = true,
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = {},
            title = { Text(title) },
            confirmButton = {
            },
            dismissButton = {
            },
            text = {
                Text(content, modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
            },
//            properties = DialogProperties(undecorated = undecorated, size = IntSize(300, 250), resizable = false)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmDialog(
    showDialog: MutableState<Boolean>,
    title: String = "",
    content: String = "",
    size: IntSize = IntSize(500, 250),
    onConfirmBtnTitle: String = "Ok",
    onConfirm: () -> Unit = {},
    onCancelBtnTitle: String = "Cancel",
    onCancel: () -> Unit = {}
) {
    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = {},
            title = { Text(title) },
            confirmButton = {
                if (onConfirmBtnTitle.isNotBlank()) {
                    Button(
                        onClick = {
                            onConfirm.invoke()
                            showDialog.value = false
                        }) {
                        Text(onConfirmBtnTitle)
                    }
                }
            },
            dismissButton = {
                if (onCancelBtnTitle.isNotBlank()) {
                    Button(
                        onClick = {
                            onCancel.invoke()
                            showDialog.value = false
                        }) {
                        Text(onCancelBtnTitle)
                    }
                }
            },
            text = {
                Text(content, modifier = Modifier.padding(8.dp), textAlign = TextAlign.Center)
            },
//            properties = DialogProperties(size = size, resizable = false)
        )
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)
    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected }),
        content = {
            Text(
                text = "Hello $name!",
                modifier = Modifier.padding(8.dp)
            )
            Divider(color = Color.Black)
        })
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    val counterState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.wrapContentHeight()) {
        NameList(names)
        Counter(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
                if (newCount >= 10) {
                    counterState.value = 0
                }
            }
        )
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text("I've been clicked $count times")
    }
}