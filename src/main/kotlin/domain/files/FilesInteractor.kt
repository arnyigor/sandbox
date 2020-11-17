package domain.files

import utils.compress
import utils.decompress
import utils.hexStringToByteArray
import utils.toHex

class FilesInteractor {
    private val data = "[[80, 75, 3, 4, 20, 0, 0, 0, 8, 0, 106, 3, 114, 81, 124, -70, 110, -66, 92, 58, 70, 0, -92, -98, 78, 0, 22, 0, 0, 0, 74, 70, 111, 114, 109, 68, 101, 115, 105, 103, 110, 101, 114, 45, 73, 100, 101, 97, 46, 106, 97, 114, -44, -3, 5, 112, -100, -55, -78, 46, 0, 118, -117, -103, -103, 44, 102, -106, 69, 22, -77, -59, -52, -52, -52, -52, 100, 49, 91, -52, 96, 89, -78, -104, -55, -78, -104, 25, 45, 102, 102, 102, 92, -51, -103, 51, -105, 125, -34, -8, -66, -73, 27, -79, -118, -88, -120, 118, 119, -3, -43, -43, -103, 95, 126, -103, 85, -107, 89, -106, -109, 4, 5, -125, 1, 0, -96, -96, 0, -109, -11, -52, -14, -128, -73, 63, 16, -64, -97, 127, -48, 111, 77, 90, 68, 73, -128, 78, 92, 70, -108, 1, 20, -16, -97, 59, -6, -63, -98, -58, 5, -65, 117, 24, 124, 107, 104, -1, -79, -93, -76, -128, -116, -72, -88, -120, -94, 18, -67, -76, -88, -11, 72, 11, 76, 39, 35, 26, -8, 26, 79, -84, 107, -29, 23, 4, -24, 89, 66, -104, -7, 124, -111, -61, 53, -46, 70, 49, -113, 18, -1, -89, -124, 116, -71, -13, 99, -12, 16, 122, 23, 46, -68, -47, -11, -26, -58, 45, 72, 106, -27, -27, -48, -120, -79, -127, 78, -47, 47, 104, -69, 28, 16, -31, 9, -59, 37, -85, 10, -84, -18, 94, 53, -66, -36, 37, 65, -100, -99, -59, -22, -127, 2, 75, -44, -127, 16, -21, 41, -100, -11, -17, 59, 88, 17, -81, -73, 83, -42, -48, 78, 21, -8, 11, -41, -111, 64, -98, 8, -64, 83, 96, -50, -123, 91, 94, 80, 77, 124, 42, -28, 113, 91, 52, -79, -14, 121, 107, -76, -99, 62, 80, -27, -33, -3, -121, 121, 127, -56, 28, -108, -109, -76, -23, 74, 33, 126, -5, 101, 59, -40, 0, 0, -22, 127, -100, -73, -99, -107, -77, -87, -71, 13, -67, -101, -75, 85, -99, -70, -90, -29, 98, 47, -70, 15, -122, 79, 79, 26, 67, -94, -119, 112, -114, 11, -86, -23, 103, -123, 12, 53, -52, 69, 5, 107, 85, -61, 1, 103, -106, -44, 72, 83, 104, -68, -49, 52, 64, 53, 35, 87, -94, 52, -97, 65, -49, 23, 99, 95, -15, -74, 26, -40, 88, -65, -113, -13, 73, 75, 38, -52, -47, 85, -55, -114, -9, -93, -82, -12, 67, -111, 4, -8, -81, -105, 123, 23, -55, -105, -88, -71, -52, -97, -69, -88, 43, 76, -117, -47, 118, 79, 17, 116, 51, 9, -62, 58, -102, 94, -91, -94, 25, 98, 32, 13, 24, 93, -53, 31, -88, -60, 59, -111, 113, 75, 38, -27, -77, 74, 74, 26, 73, 103, 105, 123, 83, -121, 32, 54, -51, -90, -47, -107, -45, -97, -38, 117, -21, 26, -101, -43, 104, 55, -118, -40, 68, 68, -39, -19, -117, 35, -74, -88, -67, -44, 88, 105, 6, 72, 27, -65, -77, 80, 27, 11, 108, -42, -20, -86, -15, -39, -52, -120, -123, -23, 118, 101, 13, -38, -100, -22, -127, 31, -78, 27, -107, 78, 110, -33, -123, -36, 85, -100, -57, -33, -19, 83, -102, 99, 86, 66, -18, 23, -55, 39, -62, 102, 115, 125, 17, 107, 35, -64, -25, 107, -10, -70, -79, 124, 95, -36, 59, 86, -115, -85, 94, -112, 16, 22, 17, 68, 109, -70, -47, -45, -84, 56, -127, 67, -126, 49, 56, 66, -94, -51, 117, 104, -108, 122, 94, 94, 86, 34, -127, 75, -95, 87, -19, 86, 102, 82, -79, -123, -46, -4, -72, 113, 20, 52, -109, 74, 76, 51, 87, -23, 114, 30, 52, -31, -116, -50, -37, 125, -30, 40, 68, 26, 48, -32, -7, -72, -95, 32, -29, 0, -93, -119, 76, -70, -7, 109, 16, 97, 91, 98, -55, -96, 124, -78, -119, 114, -117, -107, -108, -102, -30, -29, -62, 87, 59, -115, -4, -32, 124, 18, 121, 108, 30, -78, -95, -14, 1, -83, 45, 13, -35, 26, -3, -56, -36, 40, 49, 84, -48, -83, -5, -104, -95, -90, -22, 60, -127, -43, -15, -45, 58, -26, -40, -60, -110, 4, 9, -55, -115, -76, 36, 21, -87, 40, -6, -18, -113, -31, 77, -71, -5, -77, 16, -90, 10, 12, -56, 57, 126, 47, 81, -115, -29, -118, 13, -79, -63, -94, 97, 120, -80, -115, -56, -58, 56, 18, 18, -57, -35, -126, -23, -106, -120, 89, 28, 36, -82, 102, -92, -33, -120, 117, 36, 29, -91, -53, 7, -115, 37, -54, -120, -40, -94, 87, 58, 50, -32, 73, 52, 92, -76, -29, -46, -17, -122, 86, 18, 42, -45, -72, 125, 46, -82, 6, 54, -11, -57, -54, -11, -85, -110, 120, -14, -91, -20, 51, 66, 8, 21, 98, 40, 54, -8, 124, -97, -102, 22, 89, 55, 72, -15, -106, 46, 119, -40, -27, -20, -75, -38, -12, 46, 40, -117, 122, -27, -33, -89, -67, 90, 63, 33, 106, -124, 83, 9, 42, -128, 46, -54, 7, -128, -45, -50, 114, -66, -96, 25, 36, -108, -100, -101, 49, -21, -56, -25, -109, -115, 82, -90, 70, 122, 123, -51, 112, -123, -58, 111, 89, 51, -57, 123, 81, -91, -44, 78, 22, 101, 96, -55, -55, -17, -125, -89, -102, 103, -62, 106, -53, 115, -42, 126, 103, -77, 18, 111, 84, -19, 87, 24, -19, -17, -98, 40, 74, 102, 2, 13, 10, 70, 53, -89, 90, -60, 68, -75, 26, 90, 58, 80, 41, 65, 79, -107, -31, -111, 86, -37, -117, 116, 15, 67, 116, 3, -95, -31, -102, 89, 17, -97, -125, 63, 4, 127, -39, -49, -78, -103, 50, 48, 32, -100, 21, -75, -111, 49, 24, 71, 102, 68, 74, 124, -49, -84, 88, 19, 96, -18, -123, 99, -47, -101, -54, -63, -61, -21, 78, -18, 66, -58, -95, 19, -50, 14, -56, 36, 101, 20, 123, 17, 9, -87, 27, 100, 97, -10, -67, 45, 37, 7, 85, 27, -115, 106, -51, -101, 38, 34, -106, 81, -105, -12, -84, -70, 19, -103, 37, 78, 36, -5, 66, 52, 7, 54, -89, 47, -120, 17, 58, -64, 92, 93, -4, -51, 46, 74, -88, -49, 38, -112, 55, -111, -66, 8, 12, 37, -126, 19, 123, 99, -54, -128, 72, -110, 66, -49, 2, 57, -62, -105, 74, -109, 36, -43, -124, 53, 92, -91, 92, -94, 58, -82, -52, -108, -91, 26, -95, -99, 75, 89, 111, 72, 69, 103, -110, -41, 46, 72, 8, 92, -40, -99, 22, -39, -3, 58, -116, 117, -49, 92, 76, -100, -106, -29, 57, 47, 10, 12, -63, -21, -104, -20, -84, 82, 90, -17, -50, -102, -99, -72, -6, 107, -71, 91, 93, -26, 67, 21, 114, -6, 16, 39, 81, -31, 9, 57, -36, 94, -46, -82, -57, 58, -111, -38, -30, 123, -43, -102, -90, 59, 124, 66, -54, 6, -85, -120, 1, 58, 67, -107, -96, 106, -43, -78, -6, 125, -52, -117, 26, -79, 64, 96, 82, 21, 100, 121, 66, 113, -111, 4, 89, 33, -15, 23, -114, 89, 81, -17, -55, -81, -15, 77, 21, -111, -3, -112, 33, -38, -35, -94, 42, -11, 100, -110, 125, -49, 45, -16, 44, 49, 78, 58, -104, -58, -95, 8, 75, 25, -15, 96, -119, 86, 99, 63, -100, 14, -111, -85, -122, -26, -37, 70, -65, 44, 54, 18, -63, -17, 120, -84, 15, 88, 103, 22, 41, -114, 122, -42, -89, 96, 94, 56, 91, 121, -20, -27, -123, -73, 3, -127, 77, -124, 24, -100, -31, 1, 22, -23, 88, 16, 126, 49, -123, -114, -22, -93, 26, -15, 110, -87, -5, -14, 85, -77, 90, 4, -78, -53, -70, 86, 31, -73, 37, 2, -103, -20, 22, -5, -122, -5, 99, 7, -79, -93, -61, -53, -5, -51, 84, 19, -51, 9, 12, -31, -95, 2, -6, -43, -109, 31, 120, -17, -127, -101, -28, 38, 21, -17, 9, 85, -78, 5, -111, -22, -55, 60, 29, -11, 126, -102, 11, 91, -97, -33, -102, 89, 23, -89, -28, -103, -88, 51, -119, -7, 73, 8, -114, 119, 127, -6, 102, -76, 65, -109, 64, 105, -65, -2, -27, -121, 112, -82, 80, 39, -104, -126, -52, 1, 115, 32, -5, -46, 79, -17, 28, -78, -89, -126, -94, -121, 61, -55, 74, 91, -71, -2, 105, -26, 43, 110, -35, 38, 66, -41, -11, -60, 71, -107, 77, 55, 84, 93, 22, -112, 3, 84, 93, 49, 109, -38, 7, 112, -35, 25, 25, -52, -55, -87, 16, 75, -127, 99, -18, 107, 78, 55, -83, -81, 72, -72, -123, 95, 16, 32, -87, 22, 43, 109, -110, 69, -32, -10, -65, -112, 40, -78, -67, -40, 6, 96, 30, -52, -28, -62, -96, 118, -98, -52, -9, 104, -98, 111, -11, -99, -78, -121, 23, -16, 87, 108, -126, -70, -30, 103, 43, 34, 4, 7, -78, 35, 19, 123, 73, 43, 65, 75, 17, 99, 113, 107, -103, -127, 7, 65, 4, -90, 60, -71, -88, 23, 84, -15, 79, 64, -62, 90, 29, 102, -28, -106, -54, 80, -27, 39, -48, 58, 69, 93, -28, 23, -54, -105, -105, -110, -120, 18, -9, 24, 31, -119, -81, 82, 89, -108, 59, 55, 42, -94, -40, -88, -103, 39, 117, -112, 32, -121, -75, -54, 22, 110, 43, 69, -104, -110, 61, 52, -19, 6, 105, 115, 117, 114, -66, 55, 72, -35, -52, 94, 12, -4, -46, -19, -21, -87, -104, 94, 118, -55, 31, 77, -74, 17, 87, -65, 8, 117, 45, -111, -11, -50, -82, -111, 59, -128, 126, 72, 57, 10, -82, 67, 77, -108, 6, 86, 105, 64, 0, 113, 122, -104, -79, 55, 31, -95, 30, 20, -46, -123, -75, -116, 78, -16, -57, 14, -57, 102, 39, 42, 84, 47, 49, 105, -94, 122, 115, 127, -118, -125, 36, -95, 110, 19, -78, 28, 73, -113, -124, 80, -102, -28, 80, -26, -78, -22, -15, 113, 101, 49, -79, -80, 127, 75, -88, 68, 30, -100, -111, -70, -84, -47, -94, -107, -75, -64, 49, -102, -93, 59, -119, 16, -87, 9, 30, -38, 62, -65, -45, -109, 37, 18, 45, 70, 54, -51, -16, 7, -109, 38, -119, 102, -44, 72, 89, 59, -96, -14, 38, 70, 89, -6, 34, -23, -113, -10, -32, 74, 29, -6, -62, -47, -17, -120, -94, -51, 112, -14, -94, -118, -22, -69, 54, 49, -54, 63, -109, -72, -83, -106, 119, 42, 61, -35, -20, -105, 16, -40, 61, 27, -94, -4, 99, -28, -53, -115, 93, -122, 2, -18, 44, -46, 88, -95, 17, 37, 88, 93, -67, 36, 33, 126, -18, -32, 116, -78, -103, 70, -24, 57, 41, 110, 77, -24, -29, -49, 22, 0, -68, 42, -40, 108, -123, -62, -60, -14, 11, -82, -54, 50, 118, -51, -90, -107, 123, 125, -31, 39, -13, -108, -79, 22, 39, -38, -53, -117, -93, 87, 47, -22, 122, 13, -123, 81, 54, -81, -61, -33, 75, -111, -117, -46, 25, 76, -29, -96, -68, -57, 47, 46, -38, -96, 100, -87, 86, -54, -90, -12, 91, 20, -47, -78, -57, 12, -69, -6, 20, 18, 79, -12, 1, -77, -69, -93, 34, 72, -53, 6, -32, -89, -33, -121, -104, 42, 68, 64, 59, -17, -10, 90, -128, 37, -63, -120, 62, -86, -92, -68, 114, -43, -114, -114, -27, -35, 63, 95, -128, 91, -28, 16, 3, 26, 110, 37, -117, -9, 56, -99, 94, 124, -93, 122, 57, -35, -54, 33, -18, -88, -84, -66, 16, 51, -54, 88, -93, 56, -54, -119, -3, -112, -48, 50, -12, 105, -26, 70, 126, -93, 112, 62, 51, 115, -43, 103, 83, 76, 105, -46, 83, 68, -97, -88, -74, -100, 8, 37, -19, -88, -73, 6, -122, -66, 77, -84, 67, -32, -47, 35, -16, 59, 124, -122, -49, 89, -42, -7, -39, 81, -56, 22, 32, 50, -26, 38, -58, -128, -110, -64, 119, 118, 102, 72, -44, 115, -18, -42, 117, 98, 24, 5, 78, 127, -119, 108, 87, -29, 75, 110, 30, 66, -5, -98, -6, -87, 17, -65, -60, 18, -91, -58, 19, -87, -116, -41, -86, -70, -84, -87, -116, 106, 87, 70, -53, 41, -126, -7, -14, 98, 33, -72, -58, 60, -68, -96, 42, -95, -22, 116, -27, -8, 87, -125, 13, 107, 22, 42, 52, 67, 33, -5, 37, 90, -56, 20, 21, -54, 107, -7, 115, -60, 41, -26, 64, 90, -84, 82, 68, -120, -127]"

    fun packArrayData() {
        try {
            val compressed = compress(data).toHex()
            val result = decompress(compressed.hexStringToByteArray())
            println(data.length)
            println(compressed.length)
            println(result.length)
            println(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}