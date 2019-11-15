package utils;

import org.jetbrains.annotations.NotNull;
import sun.security.util.BitArray;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

class BytesUtils {

    /**
     * Вырезает из масива байтов кусок.
     *
     * @param data       массив байтов
     * @param startIndex индекс, с которого необходимо вырезать масив байтов
     * @param countByte  длина куска, в байтах
     * @return
     */
    public static byte[] subArray(byte[] data, int startIndex, int countByte) {
        byte[] tmpData = new byte[countByte];
        System.arraycopy(data, startIndex, tmpData, 0, countByte);
        return tmpData;
    }

    /**
     * Конвертирует массив байтов в {@code int}.
     *
     * @param bytes     Массив байтов
     * @param byteOrder Порядок сортировки байтов
     * @return 4-х байтовое представление.
     */
    public static int bytesToInt(byte[] bytes, ByteOrder byteOrder) {
        return bytesToInt(bytes, 0, bytes.length * Byte.SIZE, byteOrder);
    }


    @NotNull
    public static boolean[] reverseBooleans(@NotNull boolean[] data) {
        int length = data.length;
        boolean[] result = new boolean[length];
        if (length == 0)
            return result;

        for (int i = 0; i < length; i++) {
            result[i] = data[length - i - 1];
        }
        return result;
    }

    public static int getValue(byte[] data, int index, int count) {
        byte[] tempData = new byte[data.length];
        System.arraycopy(data, 0, tempData, 0, data.length);
        BitArray bitAr = new BitArray(tempData.length * 8, tempData);
        final boolean[] tempBoolData = bitAr.toBooleanArray();
        boolean[] resTemp = new boolean[count];
        System.arraycopy(tempBoolData, index, resTemp, 0, count);
        final boolean[] reverseTemp = reverseBooleans(resTemp);
        int result = 0;
        for (int i = 0; i < reverseTemp.length; i++) {
            if (reverseTemp[i])
                result = result | (1 << i);
        }
        return result;
    }

    public static int bytesToInt(byte[] bytes, int firstBitPos, int length, ByteOrder byteOrder) {
        return (int) bytesToLong(bytes, firstBitPos, length, byteOrder);
    }

    public static long byteToInt(byte[] bytes, int length) {
        int val = 0;
        if (length > 4) throw new RuntimeException("Too big to fit in int");
        for (int i = 0; i < length; i++) {
            val = val << 8;
            val = val | (bytes[i] & 0xFF);
        }
        return val;
    }

    public static int toInt(byte[] bytes, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    public static List<Byte> bytesToList(byte[] bytes) {
        final ArrayList<Byte> bytesList = new ArrayList<>();
        for (byte aByte : bytes) {
            bytesList.add(aByte);
        }
        return bytesList;
    }

    public static byte[] listToBytes(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size() - 1; i++) {
            final Byte aByte = list.get(i);
            bytes[i] = aByte;
        }
        return bytes;
    }

    public static List<Boolean> booleansToList(boolean[] booleans) {
        final ArrayList<Boolean> booleanList = new ArrayList<>();
        for (boolean aBoolean : booleans) {
            booleanList.add(aBoolean);
        }
        return booleanList;
    }

    public static boolean[] listToBooleans(List<Boolean> list) {
        boolean[] booleans = new boolean[list.size()];
        for (int i = 0; i < list.size() - 1; i++) {
            final Boolean aBoolean = list.get(i);
            booleans[i] = aBoolean;
        }
        return booleans;
    }


    /**
     * Конвертирует диапазон битов из массива байтов в {@code long}.
     */
    public static long bytesToLong(byte[] bytes, int firstBitPos, int length, ByteOrder byteOrder) {
        long result = 0;
        int lastBitPos = firstBitPos + length - 1;
        for (int i = 0; i < bytes.length; i++) {
            int startBitPos = Byte.SIZE * i;
            int endBitPos = startBitPos + Byte.SIZE - 1;
            if (firstBitPos > endBitPos) {
                continue;
            }
            int trimHigh = 0;
            int realIndex = byteOrder == ByteOrder.LITTLE_ENDIAN ? bytes.length - i - 1 : i;
            long temp = bytes[realIndex] & 0xff;
            if (firstBitPos <= endBitPos && firstBitPos >= startBitPos) {
                trimHigh = firstBitPos;
                temp = temp >>> firstBitPos;
            }
            if (lastBitPos >= endBitPos) {
                result = result << Byte.SIZE;
                result = result | temp;
            }
            if (lastBitPos < endBitPos) {
                int trimLow = Long.SIZE - Byte.SIZE + endBitPos - lastBitPos + trimHigh;
                temp = (temp << trimLow) >>> trimLow;
                result = (result << (Long.SIZE - trimLow));
                result = result | temp;
            }
        }
        return result;
    }
}
