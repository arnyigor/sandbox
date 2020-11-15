package utils;

import java.text.DecimalFormat;

public class FilesUtils {
    public static String formatFileSize(long size, int digits) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        StringBuilder digs = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            digs.append("#");
        }
        return new DecimalFormat("#,##0." + digs.toString()).format(size
                / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }
}
