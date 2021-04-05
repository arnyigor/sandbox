package utils;

public class Tester {
    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.run();
    }

    private void run() {
        Proger proger = new AndroidDev();
        proger.writeCode();
        proger.testCode();
    }

    private class AndroidDev extends Proger {
        public void writeCode() {
            System.out.println("AndroidDev write code");
        }

        private void testCode() {
            System.out.println("AndroidDev test code");
        }
    }

    private class Proger {
        public void writeCode() {
            System.out.println("Proger write code");
        }

        private void testCode() {
            System.out.println("Proger test code");
        }
    }
}
