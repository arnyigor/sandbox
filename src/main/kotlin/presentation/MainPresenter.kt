package presentation

import data.network.ApiHelper
import utils.isFileExist
import utils.launchAsync
import java.io.File
import java.text.DecimalFormat

class MainPresenter {
    private val apiHelper: ApiHelper = ApiHelper()
    fun downloadFiles() {
        val headers = listOf(
            Pair(
                "cookie",
                "_ga=GA1.2.1060281196.1605350617; _gid=GA1.2.160345043.1605350617; _ym_uid=1605350617976242495; _ym_d=1605350617; _ym_isad=1; _fbp=fb.1.1605350618184.1971129977; CloudFront-Key-Pair-Id=APKAJH3GITHJTCN7K4VQ; _ym_visorc_64600876=w; CloudFront-Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vc3RyZWFtLWxpdmUuanVncnUub3JnLyoiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE2MzY4OTA5Nzl9LCJEYXRlR3JlYXRlclRoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTYwNTI2ODU3OX19fV19; CloudFront-Signature=aX0i49h0WjZBD17ig8WS6HMx4Ocy4Eg6n-XzubukzdzboyFhl4jV4LjaglRxFhueWFPF6lBRfnqgk1qXSXHSGdDPQLsql0l5o5A6JowCSP1y4OSRJEt4p--PnV-EItO9eY~B1h-~Y4V~iTK6E2Y2TnOpmLLSluizTpmVPiSR~PSqRg6Z3BooRGs0ZENrj1qAMmIPRCvDdwIbmX5bvwz-NZg6Ne1zQP-hRMEn~GgoGl~7zDYdRbec16BFx0nOinSZNgenNGW1S75ET9LiRAIaw88MlOuwzfBd0ro-5g~xo2BIhJxsGBmcSEG2ibdlIEoYR1taooELsczn4y7wbyX4Fw__"
            )
        )
        val start = 1
        val end = 2550
        for (i in start..end) {
            val iter = DecimalFormat("00000").format(i)
            val fileName = "mobius20202-track3-day1_1920_$iter.ts"
            val url = "https://stream-live.jugru.org/" +
                    "mobius20202/" +
                    "mobius20202-track3-day1/" +
                    "mobius20202-track3-day1_1920/" +
                    "00000/$fileName"

            val file = File(fileName)
            if (file.isFileExist()) {
                launchAsync({
                    apiHelper.downloadFile(file, url, headers)
                }, {
                    println("download file:$fileName,OK")
                }, {
                    println("download file:$fileName,error")
                    it.printStackTrace()
                })
            }
        }
    }
}