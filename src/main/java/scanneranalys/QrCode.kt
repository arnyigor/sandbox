package scanneranalys

import java.nio.charset.Charset

fun main() {
    run()
}

private val code =
    "ST00011|Name=Ð£Ð¿Ñ\u0080Ð°Ð²Ð»ÐµÐ½Ð¸Ðµ Ð¤ÐµÐ´ÐµÑ\u0080Ð°Ð»Ñ\u008CÐ½Ð¾Ð³Ð¾ Ð\u009AÐ°Ð·Ð½Ð°Ñ\u0087Ð¹Ñ\u0081Ñ\u0082Ð²Ð° Ð¿Ð¾ Ð ÐµÑ\u0081Ð¿Ñ\u0083Ð±Ð»Ð¸ÐºÐµ Ð¡Ð°Ñ\u0085Ð° (Ð¯ÐºÑ\u0083Ñ\u0082Ð¸Ñ\u008F) (Ð\u0098Ð¤Ð\u009DÐ¡ Ð Ð¾Ñ\u0081Ñ\u0081Ð¸Ð¸ Ð¿Ð¾ Ð\u0090Ð»Ð´Ð°Ð½Ñ\u0081ÐºÐ¾Ð¼Ñ\u0083 Ñ\u0080Ð°Ð¹Ð¾Ð½Ñ\u0083 Ð ÐµÑ\u0081Ð¿Ñ\u0083Ð±Ð»Ð¸ÐºÐ¸ Ð¡Ð°Ñ\u0085Ð° (Ð¯ÐºÑ\u0083Ñ\u0082Ð¸Ñ\u008F))|PersonalAcc=03100643000000011600|BankName=Ð\u009EÐ¢Ð\u0094Ð\u0095Ð\u009BÐ\u0095Ð\u009DÐ\u0098Ð\u0095-Ð\u009DÐ\u0091 Ð Ð\u0095Ð¡Ð\u009FÐ£Ð\u0091Ð\u009BÐ\u0098Ð\u009AÐ\u0090 Ð¡Ð\u0090Ð¥Ð\u0090 (Ð¯Ð\u009AÐ£Ð¢Ð\u0098Ð¯) Ð\u0091Ð\u0090Ð\u009DÐ\u009AÐ\u0090 Ð Ð\u009EÐ¡Ð¡Ð\u0098Ð\u0098// Ð£Ð¤Ð\u009A Ð¿Ð¾ Ð ÐµÑ\u0081Ð¿Ñ\u0083Ð±Ð»Ð¸ÐºÐµ Ð¡Ð°Ñ\u0085Ð° (Ð¯ÐºÑ\u0083Ñ\u0082Ð¸Ñ\u008F) Ð³.Ð¯ÐºÑ\u0083Ñ\u0082Ñ\u0081Ðº|BIC=019805001|CorrespAcc=40102810345370000085|docIdx=18209965213722486565|DrawerStatus=09|lastName=Ð£Ð¨Ð\u009DÐ\u0098Ð¦Ð\u009AÐ\u0098Ð\u0099|firstName=Ð\u0092Ð\u009BÐ\u0090Ð\u0094Ð\u0098Ð\u009CÐ\u0098Ð |middleName=Ð\u0090Ð\u009DÐ\u0094Ð Ð\u0095Ð\u0095Ð\u0092Ð\u0098Ð§|payerAddress=Ð£Ð\u009B. Ð§Ð\u0095Ð¥Ð\u009EÐ\u0092Ð\u0090, Ð\u0094. 13, Ð\u009AÐ\u0092. 1, Ð\u0093. Ð¢Ð\u009EÐ\u009CÐ\u009CÐ\u009EÐ¢, Ð£. Ð\u0090Ð\u009BÐ\u0094Ð\u0090Ð\u009DÐ¡Ð\u009AÐ\u0098Ð\u0099, Ð Ð\u0095Ð¡Ð\u009FÐ£Ð\u0091Ð\u009BÐ\u0098Ð\u009AÐ\u0090 Ð¡Ð\u0090Ð¥Ð\u0090 (Ð¯Ð\u009AÐ£Ð¢Ð\u0098Ð¯)|PayerINN=140210045398|Sum=210650|PayeeINN=1402003388|KPP=140201001|CBC=18210202103081013160|OKTMO=98603105|PaytReason=Ð¢Ð\u009F|TaxPeriod=Ð\u009AÐ\u0092.02.2021"
private val GOOD_REGEX = "[a-zA-Zа-яА-Я0-9\\W]+".toRegex()
fun run() {
    val codingString = code.substringBefore("|")
    val coding = codingString.substring(codingString.length - 1, codingString.length)
    val charset = getCharset(coding)
    val keys = code.split("|")
    val toString = StringBuilder().apply {
        keys.forEach { keyValues ->
            val keyValue = keyValues.split("=")
            val k = keyValue.getOrNull(0)
            val v = keyValue.getOrNull(1)
            append(k)
            append("|")
            if (v != null) {
                val string = String(v.toByteArray(charset))
                val matches = v.matches(GOOD_REGEX)
                println("$k->$v->$string")
            }
        }
    }.toString()
    println(toString)

}

private fun getCharset(coding: String): Charset {
    return when (coding) {
        "1" -> charset("Windows-1251")
        "2" -> Charsets.UTF_8
        "3" -> charset("KOI8-R")
        else -> Charsets.UTF_8
    }
}