package utils

import java.awt.Component
import javax.swing.JFileChooser

fun Component.fileChoose(onChoose: (path: String?) -> Unit = {}) {
    val chooser = JFileChooser()
    val returnVal = chooser.showOpenDialog(this)
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        onChoose(chooser.selectedFile.absolutePath)
    } else {
        onChoose(null)
    }
}