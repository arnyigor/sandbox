package utils;

import java.io.File;

class ImotoChecker {
    static {
        try {
            final File dll = new File("C:\\Gost89.dll");
            final long length = dll.length();
            final String absolutePath = dll.getAbsolutePath();
            System.out.println(length);
            System.out.println(absolutePath);
            System.load(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ImotoChecker() {

    }

    native public static int emito_create(byte[] value);

    public void check(){
        final byte[] bytes = {1,2,3,4,5,65};
        final int l = emito_create(bytes);
        System.out.println(l);
    }
}
