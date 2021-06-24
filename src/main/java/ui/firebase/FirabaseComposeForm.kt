package ui.firebase

import androidx.compose.animation.animateColorAsState
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.ComposePanel
import androidx.compose.desktop.Window
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import presentation.firestore.FirebaseFirestorePresenter
import presentation.firestore.FirebaseFormView
import utils.fileChoose
import java.awt.image.BufferedImage

fun openFireabaseComposeWindow() = Window(
    title = "FirebaseFirestore"
) {
    val composePanel = ComposePanel()
    var dataResult by mutableStateOf("")
    var errorMsg by mutableStateOf("")
    var successMsg by mutableStateOf("")
    var showLoading by mutableStateOf(true)
    var collections by mutableStateOf(emptyList<String>())
    var docsCollection by mutableStateOf(emptyList<String>())
    var keysList by mutableStateOf(emptyList<String>())
    var key by mutableStateOf("")
    val pathString: MutableState<String> = remember { mutableStateOf("Path:") }
    val selectedCollectionsIndex: MutableState<Int> = remember { mutableStateOf(0) }
    val selectedDocsIndex: MutableState<Int> = remember { mutableStateOf(0) }
    val enabled: MutableState<Boolean> = remember { mutableStateOf(false) }
    val view = object : FirebaseFormView {
        override fun showError(error: String?) {
            errorMsg = error ?: ""
        }

        override fun setData(data: String?) {
            dataResult = ""
            dataResult = data ?: ""
        }

        override fun setLoading(loading: Boolean) {
            println("loading:$loading")
            showLoading = loading
            enabled.value = !loading
        }

        override fun showSuccess(message: String) {
            successMsg = message
        }

        override fun setPathText(message: String) {
            println("setPathText:$message")
            pathString.value = "Path:$message"
        }

        override fun setCollections(list: List<String>) {
            collections = list
        }

        override fun setDocs(docs: List<String>) {
            println("docs:$docs")
            docsCollection = docs
        }

        override fun setKeys(keys: List<String>) {
            keysList = keys
        }

        override fun setKeyValue(keyValue: String?) {
            key = keyValue ?: ""
        }
    }
    val presenter = FirebaseFirestorePresenter(view)
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AlertDialog(
                errorMsg.isBlank().not(),
                content = errorMsg,
                undecorated = false,
            )
            AlertDialog(
                showLoading,
                content = "Подождите,идет загрузка",
            )
            Row {
                btnClickable("Save", enabled) {
                    presenter.savePathSettings()
                }
                SimpleText(pathString.value,
                    Modifier.padding(8.dp)
                        .clickable {
                            composePanel.fileChoose { path ->
                                if (path != null) {
                                    presenter.initFirestore(path)
                                }
                            }
                        })
            }
            Row {
                btnClickable("Load collection", enabled) {
                    collections.getOrNull(selectedCollectionsIndex.value)?.let { presenter.loadCollectionData(it) }
                }
                SimpleText("Collection:", Modifier.padding(8.dp))
                Dropdown(
                    items = collections,
                    selectedIndex = selectedCollectionsIndex,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White)
                )
            }
            Row {
                btnClickable("Load doc", enabled) {
                    presenter.loadDocumentData(
                        collections.getOrNull(selectedCollectionsIndex.value) ?: "",
                        ""
                    )
                }
                SimpleText("Docs:", Modifier.padding(8.dp))
                Dropdown(
                    items = docsCollection,
                    selectedIndex = selectedDocsIndex,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White)
                )
            }
        }
    }
    presenter.loadSettings()
}

@Composable
fun SimpleText(displayText: String, modifier: Modifier) {
    Text(text = displayText, modifier = modifier)
}

@Composable
fun Dropdown(items: List<String> = emptyList(), selectedIndex: MutableState<Int>, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart).clickable {
        expanded = true
    }) {
        Card(modifier = Modifier.border(1.dp, Color.Black), border = BorderStroke(1.dp, Color.Black)) {
            Text(
                text = items.getOrNull(selectedIndex.value) ?: "-",
                modifier = modifier
                    .padding(10.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                }) {
                    Text(text = s)
                }
            }
        }
    }
}

@Composable
private fun btnClickable(btnName: String, enabled: MutableState<Boolean>, onClick: () -> Unit = {}) {
    Button(
        enabled = enabled.value,
        modifier = Modifier.padding(8.dp),
        onClick = {
            onClick()
        }) {
        Text(btnName)
    }
}

private fun dialog(error: String) {
    AppWindow(
        size = IntSize(250, 250),
        icon = getMyAppIcon(),
        resizable = false
    ).also { window ->
        window.keyboard.setShortcut(Key.Escape) {
            window.close()
        }
    }.show {
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
            properties = DialogProperties(undecorated = undecorated, size = IntSize(200, 100))
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