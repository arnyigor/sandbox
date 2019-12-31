import utils.FileStringUtils

fun main(args: Array<String>) {
    val inFilePath = "../sandbox/input.txt"
    val outFilePath = "moxy.zip"
    FileStringUtils.stringToFile(inFilePath,outFilePath)
}
