package regex

import interfaces.Testable

class ExpressionTests : Testable {
    private fun regexBetwenTwoString(start: String, end: String): Regex {
        return "(?<=$start)([\\s\\S]+?)(?=$end)".toRegex()
    }


    override fun runTest(args: Array<String>?) {
        val s =
            "/^https?://(?:mm|app?iw*).(delivembed.cc|buildplayer.com|embedstorage.net|mir-dikogo-zapada.com|multikland.net|placehere.link|synchroncode.com)/"
        
    }


    private fun hostsTest() {
        val newHost = "api.placehere.link"
        val oldHost = "lord-filmds23s.lordfilm1.zone"
        val url = "http://pfjeroegerg.e.rge.rg.e.rge/embed/kp/86193?host=$oldHost"
        val replace = url.replace(regexBetwenTwoString("//", "/"), newHost)
        println(replace)
        println(replace == "https://api.placehere.link/embed/kp/86193?host=lord-filmds23s.lordfilm1.zone")
    }
}