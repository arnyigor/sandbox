import ui.MainForm

fun main(args: Array<String>) {
    runMainform()
}

 fun runMainform() {
    val dialog = MainForm()
    dialog.pack()
    dialog.isVisible = true
}
